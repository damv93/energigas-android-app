package pe.focusit.energigas.view;

import pe.focusit.energigas.model.Route;
import pe.focusit.energigas.model.Vehicle;

public interface MyRouteView extends BaseView {

    void onGetMyRouteSuccess(Route route);
    void onGetMyRouteError(String errorMessage);
    void onNoRouteAssigned();

}
