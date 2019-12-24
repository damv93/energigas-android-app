package pe.focusit.energigas.view;

import pe.focusit.energigas.model.Route;

public interface RouteDetailView extends BaseView {
    void onGetMyRouteSuccess(Route route);
    void onGetMyRouteError(String errorMessage);
    void onSendDeclarationSuccess();
    void onSendDeclarationError(String errorMessage);
}
