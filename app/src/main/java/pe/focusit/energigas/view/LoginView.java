package pe.focusit.energigas.view;

public interface LoginView extends BaseView {

    void onLoginSuccess();
    void onLoginError(String errorMessage);

}
