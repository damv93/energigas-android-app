package pe.focusit.energigas.controller;

import android.content.Context;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import pe.focusit.energigas.R;
import pe.focusit.energigas.controller.mapper.expense.ExpenseModelEntityMapper;
import pe.focusit.energigas.controller.mapper.expenseType.ExpenseTypeModelEntityMapper;
import pe.focusit.energigas.controller.mapper.gasStation.GasStationModelEntityMapper;
import pe.focusit.energigas.controller.mapper.route.RouteModelEntityMapper;
import pe.focusit.energigas.data.entity.ExpenseEntity;
import pe.focusit.energigas.data.entity.ExpenseEntityDao;
import pe.focusit.energigas.data.entity.ExpenseTypeEntity;
import pe.focusit.energigas.data.entity.ExpenseTypeEntityDao;
import pe.focusit.energigas.data.entity.GasStationEntityDao;
import pe.focusit.energigas.data.entity.RouteEntity;
import pe.focusit.energigas.data.entity.RouteEntityDao;
import pe.focusit.energigas.model.Expense;
import pe.focusit.energigas.model.ExpenseType;
import pe.focusit.energigas.model.GasStation;
import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.RouteSegment;
import pe.focusit.energigas.util.Constants;
import pe.focusit.energigas.view.ExpenseDetailView;

public class ExpenseDetailController extends Controller {

    private ExpenseDetailView mView;
    private RouteEntityDao mRouteEntityDao;
    private ExpenseTypeEntityDao mExpenseTypeEntityDao;
    private ExpenseEntityDao mExpenseEntityDao;
    private GasStationEntityDao mGasStationEntityDao;
    private Route mRoute;
    private RouteSegment mRouteSegment;

    public ExpenseDetailController(Context context, @NonNull ExpenseDetailView view,
                                   @NonNull Route route, @NonNull RouteSegment segment) {
        super(context);
        mView = view;
        mRouteEntityDao = mDaoSession.getRouteEntityDao();
        mExpenseTypeEntityDao = mDaoSession.getExpenseTypeEntityDao();
        mExpenseEntityDao = mDaoSession.getExpenseEntityDao();
        mGasStationEntityDao = mDaoSession.getGasStationEntityDao();
        mRoute = route;
        mRouteSegment = segment;
    }

    public void getExpenseTypes() {
        List<ExpenseTypeEntity> expenseTypeEntityList = mExpenseTypeEntityDao.queryBuilder()
                .whereOr(ExpenseTypeEntityDao.Properties.ParentId.isNull(), ExpenseTypeEntityDao.Properties.ParentId.eq(0))
                .list();
        /* Build expense type models list from the entity list */
        List<ExpenseType> expenseTypes = ExpenseTypeModelEntityMapper.transform(expenseTypeEntityList);
        if (expenseTypes != null) {
            mView.onGetExpenseTypesSuccess(expenseTypes);
        } else {
            mView.onGetExpenseTypesError();
        }
    }

    public void getExpenses() {
        List<ExpenseEntity> expenseEntityList = mExpenseEntityDao.queryBuilder()
                .where(ExpenseEntityDao.Properties.RouteSegmentId.eq(mRouteSegment.getId()))
                .list();
        List<Expense> expenses = ExpenseModelEntityMapper.transform(expenseEntityList);
        if (expenses != null) {
            mView.onGetExpensesSuccess(expenses);
        } else {
            mView.onGetExpensesError();
        }
    }

    public void getGasStations() {
        List<GasStation> gasStations = GasStationModelEntityMapper.transform(mGasStationEntityDao.loadAll());
        if (gasStations != null) {
            mView.onGetGasStationsSuccess(gasStations);
        } else {
            mView.onGetGasStationsError();
        }
    }

    public void saveExpense(@NonNull Expense expense) {
        /* Set the route-segment id of the Expense */
        expense.setRouteSegmentId(mRouteSegment.getId());
        /* Check if it is fuel consumption expense type */
        if (Constants.FUEL_CONSUMPTION_ITEM_ID.equalsIgnoreCase(expense.getExpenseType().getName().trim())) {
            if (expense.getExternalProvider()) {
                /* If is external provider, the voucher type is always invoice */
                expense.setVoucherType(mContext.getString(R.string.lbl_invoice));
            } else {
                /* If is not external provider, the voucher type is always ticket */
                expense.setVoucherType(mContext.getString(R.string.lbl_ticket));
            }
        }
        /* Build an Expense entity from its model */
        ExpenseEntity expenseEntity = ExpenseModelEntityMapper.transform(expense);
        /* Save Expense entity */
        mExpenseEntityDao.insertOrReplaceInTx(expenseEntity);
        /* Update route with the new budget balance */
        RouteEntity routeEntity = mRouteEntityDao.queryBuilder()
                .where(RouteEntityDao.Properties.Id.eq(mRoute.getId()))
                .unique();
        mRoute = RouteModelEntityMapper.transform(routeEntity, mExpenseEntityDao);
        mView.onSaveExpenseSuccess(mRoute);
    }

    public void deleteExpense(@NonNull Expense expense) {
        /* Delete Expense with id */
        mExpenseEntityDao.deleteByKeyInTx(expense.getId());
        /* Check if it was deleted */
        ExpenseEntity expenseEntity = mExpenseEntityDao.queryBuilder()
                .where(ExpenseEntityDao.Properties.Id.eq(expense.getId()))
                .unique();
        if (expenseEntity == null) {
            /* Delete photo file */
            if (expense.getPhotoFilePath() != null) {
                File photoFile = new File(expense.getPhotoFilePath());
                photoFile.delete();
            }
            /* Update route budget balance */
            mRoute.setBudgetBalance(mRoute.getBudgetBalance() + expense.getAmount());
            mView.onDeleteExpenseSuccess();
        } else {
            mView.onDeleteExpenseError();
        }
    }

}
