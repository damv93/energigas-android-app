package pe.focusit.energigas.controller;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import pe.focusit.energigas.BuildConfig;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.declaration.DeclarationDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.expense.ExpenseDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.productDispatch.ProductDispatchDtoEntityMapper;
import pe.focusit.energigas.controller.mapper.productDispatch.ProductDispatchModelEntityMapper;
import pe.focusit.energigas.data.entity.DeclarationEntity;
import pe.focusit.energigas.data.entity.DeclarationEntityDao;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.ProductDispatchEntity;
import pe.focusit.energigas.data.entity.ProductDispatchEntityDao;
import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.data.entity.RouteEntityDao;
import pe.focusit.energigas.data.entity.RouteSegmentEntityDao;
import pe.focusit.energigas.data.net.RestCallback;
import pe.focusit.energigas.data.net.RestReceptor;
import pe.focusit.energigas.data.net.dto.DeclarationDto;
import pe.focusit.energigas.data.net.dto.ExpenseDto;
import pe.focusit.energigas.data.net.dto.ProductDispatchDto;
import pe.focusit.energigas.data.net.response.SendDeclarationResponse;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.controller.mapper.route.RouteModelEntityMapper;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.FileUtil;
import pe.focusit.energigas.util.LogUtil;
import pe.focusit.energigas.view.RouteDetailView;
import retrofit2.Call;

public class RouteDetailController extends Controller {

    private static String TAG = RouteDetailController.class.getSimpleName();

    private RouteDetailView mView;
    private Route mMyRoute;
    private RouteEntityDao mRouteEntityDao;
    private ExpenseEntityDao mExpenseEntityDao;
    private ProductDispatchEntityDao mProductDispatchEntityDao;
    private DeclarationEntityDao mDeclarationEntityDao;
    private RouteSegmentEntityDao mRouteSegmentEntityDao;

    public RouteDetailController(Context context, RouteDetailView view) {
        super(context);
        setView(view);
        mRouteEntityDao = mDaoSession.getRouteEntityDao();
        mExpenseEntityDao = mDaoSession.getExpenseEntityDao();
        mProductDispatchEntityDao = mDaoSession.getProductDispatchEntityDao();
        mDeclarationEntityDao = mDaoSession.getDeclarationEntityDao();
        mRouteSegmentEntityDao = mDaoSession.getRouteSegmentEntityDao();
    }

    public void setView(RouteDetailView view) {
        mView = view;
    }

    public void getMyRoute() {
        Long userId = mUser.getId();
        RouteEntity routeEntity = mRouteEntityDao.queryBuilder()
                .where(RouteEntityDao.Properties.DriverId.eq(userId))
                .unique();
        /* Build route model from its entity */
        Route route = RouteModelEntityMapper.transform(routeEntity, mExpenseEntityDao);
        if (route != null) {

            /* Check if the dispatches of all the segments are closed */
            boolean areAllDispatchesClosed = true;

            /* Set route segments progress percentages and total expense amount */
            for (RouteSegment segment : route.getRouteSegments()) {

                /* Check if the route segment has its product dispatch registered */
                ProductDispatchEntity dispatchEntity = mProductDispatchEntityDao.queryBuilder()
                        .where(ProductDispatchEntityDao.Properties.RouteSegmentId.eq(segment.getId()))
                        .unique();
                boolean isDispatchClosed = false;
                if (dispatchEntity != null) {
                    /* The route segment has its product dispatch registered */
                    segment.setProductDispatch(ProductDispatchModelEntityMapper.transform(dispatchEntity));
                    /* Now check if the dispatch is closed */
                    isDispatchClosed = dispatchEntity.getDispatchClosed() == null ?
                            false : dispatchEntity.getDispatchClosed();
                    if (isDispatchClosed) {
                        /* If the product dispatch is registered and it is closed, the progress is 100% */
                        segment.setProgressPercentage(100);
                    } else {
                        /* If the product dispatch is registered but is not closed, the progress is 45 percent more */
                        segment.setProgressPercentage(segment.getProgressPercentage() + 45);
                        areAllDispatchesClosed = false;
                    }
                } else {
                    areAllDispatchesClosed = false;
                }

                /* Check if the route segment has expenses registered */
                List<ExpenseEntity> expenseEntityList = mExpenseEntityDao.queryBuilder()
                        .where(ExpenseEntityDao.Properties.RouteSegmentId.eq(segment.getId()))
                        .list();
                if (expenseEntityList != null && !expenseEntityList.isEmpty()) {
                    if (!isDispatchClosed) {
                        /* If segment has at least one expense, the progress is 45 percent more */
                        segment.setProgressPercentage(segment.getProgressPercentage() + 45);
                    }
                    /* Set segment total expense amount */
                    double totalAmount = 0;
                    for (ExpenseEntity expenseEntity : expenseEntityList) {
                        totalAmount += expenseEntity.getAmount();
                    }
                    segment.setExpenseAmount(totalAmount);
                }

            }

            route.setAreAllDispatchesClosed(areAllDispatchesClosed);

            mMyRoute = route;
            /* Send route model to the view */
            mView.onGetMyRouteSuccess(route);
        } else {
            mView.onGetMyRouteError(mContext.getString(R.string.error_msg_local_data));
        }
    }

    public void sendDeclaration() {

        if (mMyRoute.getRouteSegments() == null)
            return;

        mView.showLoading();

        new BuildDeclarationDtoTask(this).execute();

    }

    private static class BuildDeclarationDtoTask extends AsyncTask<Void, Void, DeclarationDto> {

        RouteDetailController mController;
        List<ExpenseEntity> mExpenseEntityList;
        List<ProductDispatchEntity> mDispatchEntityList;
        DeclarationEntity mDeclarationEntity;

        private BuildDeclarationDtoTask(@NonNull RouteDetailController controller) {
            mController = controller;
        }

        @Override
        protected DeclarationDto doInBackground(Void... voids) {
            /* Get route segments ids */
            List<Long> routeSegmentIds = new ArrayList<>();
            for (RouteSegment segment : mController.mMyRoute.getRouteSegments()) {
                routeSegmentIds.add(segment.getId());
            }
            /* Get expenses of the entire route */
            mExpenseEntityList = mController.mExpenseEntityDao.queryBuilder()
                    .where(ExpenseEntityDao.Properties.RouteSegmentId.in(routeSegmentIds))
                    .list();
            /* Build Expenses dto from the entities */
            List<ExpenseDto> expenseDtoList = ExpenseDtoEntityMapper.transform(mExpenseEntityList);

            /* Get Product dispatches of the entire route */
            mDispatchEntityList = mController.mProductDispatchEntityDao.queryBuilder()
                    .where(ProductDispatchEntityDao.Properties.RouteSegmentId.in(routeSegmentIds))
                    .list();
            /* Build Product dispatch dto from the entity */
            List<ProductDispatchDto> dispatchDtoList = ProductDispatchDtoEntityMapper.transform(mDispatchEntityList);

            /* Get Declaration entity */
            mDeclarationEntity = mController.mDeclarationEntityDao.queryBuilder()
                    .where(DeclarationEntityDao.Properties.RouteId.eq(mController.mMyRoute.getId()))
                    .unique();
            if (mDeclarationEntity == null) {
                /* If there is no declaration yet, create one */
                mDeclarationEntity = new DeclarationEntity();
                mDeclarationEntity.setRouteId(mController.mMyRoute.getId());
                mController.mDeclarationEntityDao.insertOrReplaceInTx(mDeclarationEntity);
            }

            /* Build Declaration dto from the entity */
            DeclarationDto declarationDto = DeclarationDtoEntityMapper.transform(mDeclarationEntity);
            declarationDto.setState(DeclarationDto.STATE_PENDING); // pending of approval

            /* Set expenses and product dispatches declaration */
            declarationDto.setExpenses(expenseDtoList);
            declarationDto.setProductDispatches(dispatchDtoList);

            if (BuildConfig.DEBUG) {
                /* Save request body */
                //FileUtil.saveJSON(declarationDto, "enviarRendicion");
            }

            return declarationDto;
        }

        @Override
        protected void onPostExecute(DeclarationDto declarationDto) {
            /* Call send declaration service */
            Call<SendDeclarationResponse> sendDeclarationCall =
                    mController.mRestBase.getRestApi().sendDeclaration(declarationDto);
            RestReceptor<SendDeclarationResponse> receptor = new RestReceptor<>(mController.mContext);

            receptor.invoke(sendDeclarationCall, new RestCallback<SendDeclarationResponse>() {
                @Override
                public void onSuccess(SendDeclarationResponse data) {
                    LogUtil.i(TAG, "SendDeclaration - onSuccess");
                    /* After successfully sending the declaration, delete all the local route info */
                    mController.deleteRoute(mExpenseEntityList, mDispatchEntityList, mDeclarationEntity);
                    mController.mView.hideLoading();
                    mController.mView.onSendDeclarationSuccess();
                }

                @Override
                public void onError(Exception exception) {
                    LogUtil.i(TAG, "SendDeclaration - onError: " + exception.getMessage());
                    mController.mView.hideLoading();
                    mController.mView.onSendDeclarationError(exception.getMessage());
                }
            });
        }
    }

    private void deleteRoute(List<ExpenseEntity> expenses, List<ProductDispatchEntity> dispatches,
                             DeclarationEntity declaration) {

        if (expenses != null) {
            /* Delete expenses photo files */
            for (ExpenseEntity expense : expenses) {
                if (expense.getPhotoFilePath() != null) {
                    File photoFile = new File(expense.getPhotoFilePath());
                    photoFile.delete();
                }
            }
            /* Delete expenses */
            mExpenseEntityDao.deleteInTx(expenses);
        }
        if (dispatches != null) {
            /* Delete dispatches photo files */
            for (ProductDispatchEntity dispatch : dispatches) {
                if (dispatch.getPhotoFilePath() != null) {
                    File photoFile = new File(dispatch.getPhotoFilePath());
                    photoFile.delete();
                }
            }
            /* Delete dispatches */
            mProductDispatchEntityDao.deleteInTx(dispatches);
        }
        if (declaration != null)
            mDeclarationEntityDao.delete(declaration);
        if (mMyRoute != null) {
            if (mMyRoute.getRouteSegments() != null) {
                List<Long> segmentIds = new ArrayList<>();
                for (RouteSegment segment : mMyRoute.getRouteSegments()) {
                    segmentIds.add(segment.getId());
                }
                mRouteSegmentEntityDao.deleteByKeyInTx(segmentIds);
            }
            mRouteEntityDao.deleteByKey(mMyRoute.getId());
        }

    }

}
