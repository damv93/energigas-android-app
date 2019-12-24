package pe.focusit.energigas.controller;

import android.content.Context;

import java.io.File;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.productDispatch.ProductDispatchModelEntityMapper;
import pe.focusit.energigas.data.entity.DeclarationEntity;
import pe.focusit.energigas.data.entity.DeclarationEntityDao;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.ProductDispatchEntity;
import pe.focusit.energigas.data.entity.ProductDispatchEntityDao;
import pe.focusit.energigas.model.ProductDispatch;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.view.ProductDispatchView;

public class ProductDispatchController extends Controller {

    private static String TAG = ProductDispatchController.class.getSimpleName();

    private ProductDispatchView mView;
    private RouteSegment mRouteSegment;
    private ProductDispatchEntityDao mDispatchEntityDao;
    private DeclarationEntityDao mDeclarationEntityDao;
    private ExpenseEntityDao mExpenseEntityDao;

    public ProductDispatchController(Context context,
                                     @NonNull ProductDispatchView view, @NonNull RouteSegment segment) {
        super(context);
        mView = view;
        mRouteSegment = segment;
        mDispatchEntityDao = mDaoSession.getProductDispatchEntityDao();
        mDeclarationEntityDao = mDaoSession.getDeclarationEntityDao();
        mExpenseEntityDao = mDaoSession.getExpenseEntityDao();
    }

    public void getProductDispatch() {
        ProductDispatchEntity dispatchEntity = mDispatchEntityDao.queryBuilder()
                .where(ProductDispatchEntityDao.Properties.RouteSegmentId.eq(mRouteSegment.getId()))
                .unique();
        ProductDispatch dispatch = ProductDispatchModelEntityMapper.transform(dispatchEntity);
        if (dispatch != null) {
            mView.onGetDispatchSuccess(dispatch);
        } else {
            mView.onGetDispatchError();
        }
    }

    public void saveProductDispatch(@NonNull ProductDispatch dispatch) {
        /* Set the route-segment id of the Dispatch */
        dispatch.setRouteSegmentId(mRouteSegment.getId());
        /* Build a Dispatch entity from its model */
        ProductDispatchEntity dispatchEntity = ProductDispatchModelEntityMapper.transform(dispatch);
        /* Save Dispatch entity */
        mDispatchEntityDao.insertOrReplaceInTx(dispatchEntity);
        mView.onSaveDispatchSuccess();
    }

    public void deleteProductDispatch(@NonNull ProductDispatch productDispatch) {
        /* Delete Product Dispatch with id */
        mDispatchEntityDao.deleteByKeyInTx(productDispatch.getId());
        /* Check if it was deleted */
        ProductDispatchEntity productDispatchEntity = mDispatchEntityDao.queryBuilder()
                .where(ProductDispatchEntityDao.Properties.Id.eq(productDispatch.getId()))
                .unique();
        if (productDispatchEntity == null) {
            /* Delete photo file */
            if (productDispatch.getPhotoFilePath() != null) {
                File photoFile = new File(productDispatch.getPhotoFilePath());
                photoFile.delete();
            }
            mView.onDeleteDispatchSuccess();
        } else {
            mView.onDeleteDispatchError();
        }
    }

    public void checkExpenses() {
        /* Check if the route segment has expenses registered */
        List<ExpenseEntity> expenseEntityList = mExpenseEntityDao.queryBuilder()
                .where(ExpenseEntityDao.Properties.RouteSegmentId.eq(mRouteSegment.getId()))
                .list();
        mView.onExpensesChecked(expenseEntityList != null && !expenseEntityList.isEmpty());
    }

    public void closeSegmentDeclaration() {
        /* Close product dispatch */
        ProductDispatchEntity dispatchEntity = mDispatchEntityDao.queryBuilder()
                .where(ProductDispatchEntityDao.Properties.RouteSegmentId.eq(mRouteSegment.getId()))
                .unique();
        if (dispatchEntity == null)
            return;
        dispatchEntity.setDispatchClosed(true);
        mDispatchEntityDao.updateInTx(dispatchEntity);

        mView.onCloseSegmentDeclarationSuccess();
    }

    public Date getDepartureDate() {
        /* Get Declaration entity */
        DeclarationEntity declarationEntity = mDeclarationEntityDao.queryBuilder()
                .where(DeclarationEntityDao.Properties.RouteId.eq(mRouteSegment.getRouteId()))
                .unique();
        return declarationEntity == null ? null : declarationEntity.getDepartureDate();
    }

}
