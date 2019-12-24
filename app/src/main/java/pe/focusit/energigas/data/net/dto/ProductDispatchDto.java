package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ProductDispatchDto {

    @SerializedName("gok")
    private Integer gok;
    @SerializedName("numero")
    private Integer number;
    @SerializedName("id_ruta_cliente")
    private Long routeSegmentId;
    @SerializedName("cant_solicitada")
    private Integer requestedAmount;
    @SerializedName("cant_despachada")
    private Double dispatchedAmount;
    @SerializedName("n_tanque")
    private Integer tankPercentage;
    @SerializedName("fecha_llegada")
    private Date arrivalDate;
    @SerializedName("fecha_salida")
    private Date departureDate;
    @SerializedName("golpe")
    private String hit;
    @SerializedName("n_guia")
    private String guideNumber;
    @SerializedName("n_factura")
    private String invoiceNumber;
    @SerializedName("precio_combustible")
    private Double fuelPrice;
    @SerializedName("monto")
    private Double amount;
    @SerializedName("recepcion_por")
    private String receivedBy;
    @SerializedName("observacion")
    private String observation;
    @SerializedName("nom_arch_foto")
    private String photoFileName;
    @SerializedName("foto")
    private String photoFile;

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

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
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
