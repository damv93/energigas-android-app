package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

import pe.focusit.energigas.data.net.dto.RouteDto;

public class MyRouteResponse extends ApiBaseResponse {

    @SerializedName("data")
    private RouteDto route;

    public RouteDto getRoute() {
        return route;
    }

    public void setRoute(RouteDto route) {
        this.route = route;
    }
}
