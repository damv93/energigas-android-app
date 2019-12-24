package pe.focusit.energigas.view;

import java.util.List;

import androidx.annotation.NonNull;
import pe.focusit.energigas.model.Expense;
import pe.focusit.energigas.model.ExpenseType;
import pe.focusit.energigas.model.GasStation;
import pe.focusit.energigas.model.Route;

public interface ExpenseDetailView extends BaseView {
    void onGetExpensesSuccess(@NonNull List<Expense> expenses);
    void onGetExpensesError();
    void onGetExpenseTypesSuccess(@NonNull List<ExpenseType> expenseTypes);
    void onGetExpenseTypesError();
    void onGetGasStationsSuccess(@NonNull List<GasStation> gasStations);
    void onGetGasStationsError();
    void onSaveExpenseSuccess(Route route);
    void onSaveExpenseError();
    void onDeleteExpenseSuccess();
    void onDeleteExpenseError();
}
