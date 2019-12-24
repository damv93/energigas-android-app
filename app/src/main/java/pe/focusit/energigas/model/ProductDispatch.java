package pe.focusit.energigas.model;

import java.util.Date;

public class ProductDispatch {

    private Long id;
    private Integer gok;
    private Integer number;
    private Long routeSegmentId;
    private Integer requestedAmount;
    private Double dispatchedAmount;
    private Integer tankPercentage;
    private Date arrivalDate;
    private Date departureDate;
    private String hit;
    private String guideNumber;
    private String invoiceNumber;
    private Double fuelPrice;
    private Double amount;
    private String receivedBy;
    private String photoFilePath;
    private Boolean dispatchClosed;
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGok() {
        return gok;
    }

    public void setGok(Integer gok) {
        this.gok = gok;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getRouteSegmentId() {
        return routeSegmentId;
    }

    public void setRouteSegmentId(Long routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getDispatchedAmount() {
        return dispatchedAmount;
    }

    public void setDispatchedAmount(Double dispatchedAmount) {
        this.dispatchedAmount = dispatchedAmount;
    }

    public Integer getTankPercentage() {
        return tankPercentage;
    }

    public void setTankPercentage(Integer tankPercentage) {
        this.tankPercentage = tankPercentage;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getGuideNumber() {
        return guideNumber;
    }

    public void setGuideNumber(String guideNumber) {
        this.guideNumber = guideNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public Boolean getDispatchClosed() {
        return dispatchClosed;
    }

    public void setDispatchClosed(Boolean dispatchClosed) {
        this.dispatchClosed = dispatchClosed;
    }

    public Double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(Double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }
}
