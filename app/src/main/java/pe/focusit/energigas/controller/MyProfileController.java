package pe.focusit.energigas.controller;

import android.content.Context;

import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.route.RouteModelEntityMapper;
import pe.focusit.energigas.controller.mapper.vehicle.VehicleModelEntityMapper;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.data.entity.RouteEntityDao;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.Vehicle;
import pe.focusit.energigas.view.MyProfileView;

public class MyProfileController extends Controller {

    private MyProfileView mView;
    private RouteEntityDao mRouteEntityDao;
    private ExpenseEntityDao mExpenseEntityDao;

    public MyProfileController(Context context, MyProfileView view) {
        super(context);
        mView = view;
        mRouteEntityDao = mDaoSession.getRouteEntityDao();
        mExpenseEntityDao = mDaoSession.getExpenseEntityDao();
    }

    public void getVehicleAssigned() {
        Long userId = mUser.getId();
        RouteEntity routeEntity = mRouteEntityDao.queryBuilder()
                .where(RouteEntityDao.Properties.DriverId.eq(userId))
                .unique();
        if (routeEntity == null) {
            mView.onGetVehicleError(mContext.getString(R.string.msg_driver_not_have_vehicle));
            return;
        }
        Vehicle vehicle = VehicleModelEntityMapper.transform(routeEntity.getVehicle());
        if (vehicle != null) {
            mView.onGetVehicleSuccess(vehicle);
        } else {
            mView.onGetVehicleError(mContext.getString(R.string.msg_driver_not_have_vehicle));
        }
    }

    public void getBudgetBalance() {
        Long userId = mUser.getId();
        RouteEntity routeEntity = mRouteEntityDao.queryBuilder()
                .where(RouteEntityDao.Properties.DriverId.eq(userId))
                .unique();
        if (routeEntity == null) {
            mView.onGetBudgetBalanceSuccess(0);
            return;
        }
        /* Build route model from its entity */
        Route route = RouteModelEntityMapper.transform(routeEntity, mExpenseEntityDao);
        if (route.getBudgetBalance() != null)
            mView.onGetBudgetBalanceSuccess(route.getBudgetBalance());
        else
            mView.onGetBudgetBalanceError("");
    }
}
