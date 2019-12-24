package pe.focusit.energigas.controller;

import android.content.Context;

import java.util.Date;
import java.util.List;

import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.declaration.DeclarationDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.deposit.DepositEntityDtoMapper;
import pe.focusit.energigas.controller.mapper.route.RouteModelEntityMapper;
import pe.focusit.energigas.controller.mapper.vehicle.VehicleDtoEntityMapper;
import pe.focusit.energigas.data.entity.DeclarationEntity;
import pe.focusit.energigas.data.entity.DeclarationEntityDao;
import pe.focusit.energigas.data.entity.DepositEntity;
import pe.focusit.energigas.data.entity.DepositEntityDao;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.data.entity.RouteEntityDao;
import pe.focusit.energigas.data.entity.RouteSegmentEntity;
import pe.focusit.energigas.data.entity.RouteSegmentEntityDao;
import pe.focusit.energigas.controller.mapper.route.RouteDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.routeSegment.RouteSegmentDtoEntityMapper;
import pe.focusit.energigas.data.entity.VehicleEntity;
import pe.focusit.energigas.data.entity.VehicleEntityDao;
import pe.focusit.energigas.data.net.RestCallback;
import pe.focusit.energigas.data.net.RestReceptor;
import pe.focusit.energigas.data.net.dto.DeclarationDto;
import pe.focusit.energigas.data.net.dto.DepositDto;
import pe.focusit.energigas.data.net.dto.RouteDto;
import pe.focusit.energigas.data.net.dto.RouteSegmentDto;
import pe.focusit.energigas.data.net.dto.VehicleDto;
import pe.focusit.energigas.data.net.response.MyRouteResponse;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.controller.mapper.route.RouteModelDtoMapper;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.view.MyRouteView;
import retrofit2.Call;

public class MyRouteController extends Controller {

    private static String TAG = MyRouteController.class.getSimpleName();

    private MyRouteView mView;
    private Route mMyRoute;
    private RouteEntityDao mRouteEntityDao;
    private RouteSegmentEntityDao mRouteSegmentEntityDao;
    private DeclarationEntityDao mDeclarationEntityDao;
    private ExpenseEntityDao mExpenseEntityDao;
    private VehicleEntityDao mVehicleEntityDao;
    private DepositEntityDao mDepositEntityDao;

    public MyRouteController(Context context, MyRouteView view) {
        super(context);
        setView(view);
        mRouteEntityDao = mDaoSession.getRouteEntityDao();
        mRouteSegmentEntityDao = mDaoSession.getRouteSegmentEntityDao();
        mDeclarationEntityDao = mDaoSession.getDeclarationEntityDao();
        mExpenseEntityDao = mDaoSession.getExpenseEntityDao();
        mVehicleEntityDao = mDaoSession.getVehicleEntityDao();
        mDepositEntityDao = mDaoSession.getDepositEntityDao();
    }

    public void setView(MyRouteView view) {
        mView = view;
    }

    public void getMyRoute() {

        /* Call get-my-route service */
        Call<MyRouteResponse> getMyRouteCall = mRestBase.getRestApi().getMyRoute();
        RestReceptor<MyRouteResponse> receptor = new RestReceptor<>(mContext);

        mView.showLoading();

        receptor.invoke(getMyRouteCall, new RestCallback<MyRouteResponse>() {
            @Override
            public void onSuccess(MyRouteResponse data) {
                LogUtil.i(TAG, "GetMyRoute - onSuccess");

                mView.hideLoading();

                RouteDto routeDto = data.getRoute();
                if (routeDto != null) {
                    /* Store the route locally */
                    saveMyRoute(routeDto);
                    /* Build a route model from its dto */
                    Route route = RouteModelDtoMapper.transform(routeDto);
                    /* Set route budget balance */
                    setRouteBudgetBalance(route);
                    /* Send the route model to the view */
                    mMyRoute = route;
                    mView.onGetMyRouteSuccess(route);
                } else {
                    /* Route not found. Delete stored route if exists */
                    deleteUserRoute();
                    mView.onNoRouteAssigned();
                }

            }

            @Override
            public void onError(Exception exception) {
                LogUtil.i(TAG, "GetMyRoute - onError: " + exception.getMessage());

                /* Try getting the route locally if exists */
                Long driverId = mUser.getId();
                RouteEntity routeEntity = mRouteEntityDao.queryBuilder()
                        .where(RouteEntityDao.Properties.DriverId.eq(driverId))
                        .unique();
                if (routeEntity != null) {
                    /* Build a route model from its entity */
                    Route route = RouteModelEntityMapper.transform(routeEntity, mExpenseEntityDao);
                    /* Send the route and the user vehicle models to the view */
                    mMyRoute = route;
                    mView.hideLoading();
                    mView.onGetMyRouteSuccess(route);
                } else {
                    /* Route not found, send service error message */
                    mView.hideLoading();
                    mView.onGetMyRouteError(exception.getMessage());
                }
            }
        });

    }

    private void deleteUserRoute() {
        RouteEntity previousRouteStored = mRouteEntityDao.queryBuilder()
                .where(RouteEntityDao.Properties.DriverId.eq(mUser.getId()))
                .unique();
        if (previousRouteStored != null) {

            /* Delete route segments */
            previousRouteStored.resetRouteSegments();
            List<RouteSegmentEntity> segmentEntityList = previousRouteStored.getRouteSegments();
            if (segmentEntityList != null) {
                mRouteSegmentEntityDao.deleteInTx(segmentEntityList);
            }

            /* Delete route bank deposits */
            List<DepositEntity> depositEntityList = mDepositEntityDao.queryBuilder()
                    .where(DepositEntityDao.Properties.RouteId.eq(previousRouteStored.getId()))
                    .list();
            mDepositEntityDao.deleteInTx(depositEntityList);

            /* Delete route */
            mRouteEntityDao.deleteInTx(previousRouteStored);
        }
    }

    private void saveMyRoute(RouteDto routeDto) {

        /* Delete previous route stored if exists */
        deleteUserRoute();

        /* Check if route has a vehicle */
        VehicleDto vehicleDto = routeDto.getVehicle();
        if (vehicleDto != null) {
            /* First save the vehicle entity */
            VehicleEntity vehicleEntity = VehicleDtoEntityMapper.transform(vehicleDto);
            mVehicleEntityDao.insertOrReplace(vehicleEntity);
        }

        /* Build a route entity from its dto */
        RouteEntity routeEntity = RouteDtoEntityMapper.transform(routeDto);
        /* Save route entity */
        mRouteEntityDao.insertOrReplaceInTx(routeEntity);

        List<RouteSegmentDto> routeSegmentDtoList = routeDto.getRouteSegments();
        if (routeSegmentDtoList != null) {
            /* Build route segment entity list from its dto */
            List<RouteSegmentEntity> routeSegmentEntityList = RouteSegmentDtoEntityMapper.transform(routeSegmentDtoList);
            /* Save route segment entities */
            mRouteSegmentEntityDao.insertOrReplaceInTx(routeSegmentEntityList);
        }

        List<DepositDto> depositDtoList = routeDto.getDeposits();
        if (depositDtoList != null) {
            /* Build deposit entity list from its dto */
            List<DepositEntity> depositEntityList = DepositEntityDtoMapper.transform(depositDtoList);
            /* Save deposit entities */
            mDepositEntityDao.insertOrReplaceInTx(depositEntityList);
        }

    }

    private void setRouteBudgetBalance(Route route) {
        if (route.getRouteSegments() == null)
            return;
        double expensesTotalAmount = 0;
        for (RouteSegment segment : route.getRouteSegments()) {
            List<ExpenseEntity> expenseEntityList = mExpenseEntityDao.queryBuilder()
                    .where(ExpenseEntityDao.Properties.RouteSegmentId.eq(segment.getId()))
                    .list();
            if (expenseEntityList == null)
                continue;
            for (ExpenseEntity expenseEntity : expenseEntityList) {
                if (expenseEntity.getAmount() != null && expenseEntity.getExpenseType() != null &&
                        expenseEntity.getExpenseType().getType() == Constants.FUND_TYPE)
                    expensesTotalAmount += expenseEntity.getAmount();
            }
        }
        route.setBudgetBalance(route.getAmountGiven() - expensesTotalAmount);
    }

    public void startRoute() {
        /* Get Declaration entity */
        DeclarationEntity declarationEntity = mDeclarationEntityDao.queryBuilder()
                .where(DeclarationEntityDao.Properties.RouteId.eq(mMyRoute.getId()))
                .unique();
        if (declarationEntity == null) {
            /* If there is no declaration yet, create one */
            declarationEntity = new DeclarationEntity();
            declarationEntity.setRouteId(mMyRoute.getId());
            declarationEntity.setDepartureDate(new Date()); // set departure date to now
            mDeclarationEntityDao.insertInTx(declarationEntity);
        }
    }

}
