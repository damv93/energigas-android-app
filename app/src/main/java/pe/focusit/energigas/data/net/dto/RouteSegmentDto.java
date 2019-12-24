package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class RouteSegmentDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("id_ruta")
    private Long routeId;
    @SerializedName("numero_parada")
    private Integer stopNumber;
    @SerializedName("lugar_parada")
    private String stopPlace;
    @SerializedName("id_cliente")
    private Long clientId;
    @SerializedName("cant_solicitada")
    private Integer requestedAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Integer getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(Integer stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getStopPlace() {
        return stopPlace;
    }

    public void setStopPlace(String stopPlace) {
        this.stopPlace = stopPlace;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
}
