package pe.focusit.energigas.view;

import pe.focusit.energigas.model.Vehicle;

public interface MyProfileView extends BaseView {
    void onGetVehicleSuccess(Vehicle vehicle);
    void onGetVehicleError(String errorMessage);
    void onGetBudgetBalanceSuccess(double budgetBalance);
    void onGetBudgetBalanceError(String errorMessage);
}
