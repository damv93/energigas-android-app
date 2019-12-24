package pe.focusit.energigas.view;

public interface MainView extends BaseView {
    void onLogoutSuccess();
    void onLogoutError(String errorMessage);
}
