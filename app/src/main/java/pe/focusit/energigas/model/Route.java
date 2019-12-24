package pe.focusit.energigas.model;

import java.util.Collections;
import java.util.List;

public class Route {

    private Long id;
    private Long unitTypeId;
    private Long driverId;
    private String name;
    private String departurePlace;
    private String fuelToTransport;
    private String distance;
    private Integer viaticum;
    private Integer transportation;
    private Integer hotel;
    private Integer parking;
    private Double dayTotal;
    private Integer numDays;
    private Double amountGiven;
    private Double budgetBalance;
    private Vehicle vehicle;
    private List<RouteSegment> routeSegments;
    private String routeSegmentsName;
    private Boolean areAllDispatchesClosed = false;

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

    public Integer getViaticum() {
        return viaticum;
    }

    public void setViaticum(Integer viaticum) {
        this.viaticum = viaticum;
    }

    public Integer getTransportation() {
        return transportation;
    }

    public void setTransportation(Integer transportation) {
        this.transportation = transportation;
    }

    public Integer getHotel() {
        return hotel;
    }

    public void setHotel(Integer hotel) {
        this.hotel = hotel;
    }

    public Integer getParking() {
        return parking;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public Double getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }

    public String getFuelToTransport() {
        return fuelToTransport;
    }

    public void setFuelToTransport(String fuelToTransport) {
        this.fuelToTransport = fuelToTransport;
    }

    public List<RouteSegment> getRouteSegments() {
        return routeSegments;
    }

    public void setRouteSegments(List<RouteSegment> routeSegments) {
        this.routeSegments = routeSegments;
        if (routeSegments == null) {
            routeSegmentsName = null;
            return;
        }
        /* Sort route segments by stop number */
        Collections.sort(routeSegments, (s1, s2) -> s1.getStopNumber() - s2.getStopNumber());
        /* Initialize route segments name */
        routeSegmentsName = departurePlace;
        /* Set the start places of the route segments */
        for (int i = 0; i < routeSegments.size(); i++) {
            RouteSegment segment = routeSegments.get(i);
            segment.setStartPlace(i == 0 ? departurePlace : routeSegments.get(i - 1).getStopPlace());
            /* Build route segments name */
            routeSegmentsName += " - " + segment.getStopPlace();
        }
    }

    public String getRouteSegmentsName() {
        return routeSegmentsName;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Double getBudgetBalance() {
        return budgetBalance;
    }

    public void setBudgetBalance(Double budgetBalance) {
        this.budgetBalance = budgetBalance;
    }

    public Boolean getAreAllDispatchesClosed() {
        return areAllDispatchesClosed;
    }

    public void setAreAllDispatchesClosed(Boolean areAllDispatchesClosed) {
        this.areAllDispatchesClosed = areAllDispatchesClosed;
    }

    public Integer getNumDays() {
        return numDays;
    }

    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }

    public Double getAmountGiven() {
        return amountGiven;
    }

    public void setAmountGiven(Double amountGiven) {
        this.amountGiven = amountGiven;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
