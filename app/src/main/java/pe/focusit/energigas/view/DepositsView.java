package pe.focusit.energigas.view;

import java.util.List;

import pe.focusit.energigas.model.Deposit;

public interface DepositsView extends BaseView {
    void onGetDepositsSuccess(List<Deposit> deposits);
    void onGetDepositsError(String errorMessage);
}
