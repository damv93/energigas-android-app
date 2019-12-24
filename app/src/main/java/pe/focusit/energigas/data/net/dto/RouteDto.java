package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("id_tipo_unidad")
    private Long unitTypeId;
    @SerializedName("id_chofer")
    private Long driverId;
    @SerializedName("nombre")
    private String name;
    @SerializedName("lugar_inicio")
    private String departurePlace;
    @SerializedName("combustible_trasladar")
    private String fuelToTransport;
    @SerializedName("distancia")
    private String distance;
    @SerializedName("monto_entregado")
    private Double amountGiven;
    @SerializedName("unidad")
    private VehicleDto vehicle;
    @SerializedName("ruta_clientes")
    private List<RouteSegmentDto> routeSegments;
    @SerializedName("depositos")
    private List<DepositDto> deposits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFuelToTransport() {
        return fuelToTransport;
    }

    public void setFuelToTransport(String fuelToTransport) {
        this.fuelToTransport = fuelToTransport;
    }

    public List<RouteSegmentDto> getRouteSegments() {
        return routeSegments;
    }

    public void setRouteSegments(List<RouteSegmentDto> routeSegments) {
        this.routeSegments = routeSegments;
    }

    public Double getAmountGiven() {
        return amountGiven;
    }

    public void setAmountGiven(Double amountGiven) {
        this.amountGiven = amountGiven;
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
    }

    public List<DepositDto> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<DepositDto> deposits) {
        this.deposits = deposits;
    }
}
