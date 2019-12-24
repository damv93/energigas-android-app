package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "PRODUCT_DISPATCH")
public class ProductDispatchEntity {

    @Id
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
    private Boolean dispatchClosed = false;
    private String observation;
    @Generated(hash = 501306223)
    public ProductDispatchEntity(Long id, Integer gok, Integer number,
            Long routeSegmentId, Integer requestedAmount, Double dispatchedAmount,
            Integer tankPercentage, Date arrivalDate, Date departureDate,
            String hit, String guideNumber, String invoiceNumber, Double fuelPrice,
            Double amount, String receivedBy, String photoFilePath,
            Boolean dispatchClosed, String observation) {
        this.id = id;
        this.gok = gok;
        this.number = number;
        this.routeSegmentId = routeSegmentId;
        this.requestedAmount = requestedAmount;
        this.dispatchedAmount = dispatchedAmount;
        this.tankPercentage = tankPercentage;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.hit = hit;
        this.guideNumber = guideNumber;
        this.invoiceNumber = invoiceNumber;
        this.fuelPrice = fuelPrice;
        this.amount = amount;
        this.receivedBy = receivedBy;
        this.photoFilePath = photoFilePath;
        this.dispatchClosed = dispatchClosed;
        this.observation = observation;
    }
    @Generated(hash = 2098964991)
    public ProductDispatchEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getGok() {
        return this.gok;
    }
    public void setGok(Integer gok) {
        this.gok = gok;
    }
    public Integer getNumber() {
        return this.number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public Long getRouteSegmentId() {
        return this.routeSegmentId;
    }
    public void setRouteSegmentId(Long routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }
    public Integer getRequestedAmount() {
        return this.requestedAmount;
    }
    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    public Double getDispatchedAmount() {
        return this.dispatchedAmount;
    }
    public void setDispatchedAmount(Double dispatchedAmount) {
        this.dispatchedAmount = dispatchedAmount;
    }
    public Integer getTankPercentage() {
        return this.tankPercentage;
    }
    public void setTankPercentage(Integer tankPercentage) {
        this.tankPercentage = tankPercentage;
    }
    public Date getArrivalDate() {
        return this.arrivalDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public Date getDepartureDate() {
        return this.departureDate;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public String getHit() {
        return this.hit;
    }
    public void setHit(String hit) {
        this.hit = hit;
    }
    public String getGuideNumber() {
        return this.guideNumber;
    }
    public void setGuideNumber(String guideNumber) {
        this.guideNumber = guideNumber;
    }
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public Double getFuelPrice() {
        return this.fuelPrice;
    }
    public void setFuelPrice(Double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getReceivedBy() {
        return this.receivedBy;
    }
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
    public String getPhotoFilePath() {
        return this.photoFilePath;
    }
    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }
    public Boolean getDispatchClosed() {
        return this.dispatchClosed;
    }
    public void setDispatchClosed(Boolean dispatchClosed) {
        this.dispatchClosed = dispatchClosed;
    }
    public String getObservation() {
        return this.observation;
    }
    public void setObservation(String observation) {
        this.observation = observation;
    }
}
