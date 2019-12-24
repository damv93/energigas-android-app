package pe.focusit.energigas.view;

import pe.focusit.energigas.model.ProductDispatch;

public interface ProductDispatchView extends BaseView {
    void onGetDispatchSuccess(ProductDispatch productDispatch);
    void onGetDispatchError();
    void onSaveDispatchSuccess();
    void onSaveDispatchError();
    void onDeleteDispatchSuccess();
    void onDeleteDispatchError();
    void onExpensesChecked(boolean hasExpenses);
    void onCloseSegmentDeclarationSuccess();
    void onCloseSegmentDeclarationError(String errorMessage);
}
