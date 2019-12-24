package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DeclarationDto {

    public static int STATE_INCOMPLETE = 0;
    public static int STATE_PENDING = 1; // Declaration completed but pending of approval
    public static int STATE_OBSERVED = 2;
    public static int STATE_APPROVED = 3;

    @SerializedName("nro_rendicion")
    private Long declarationNumber;
    @SerializedName("id_ruta")
    private Long routeId;
    @SerializedName("cod_scoop")
    private Integer scoopCode;
    @SerializedName("vol_scoop")
    private Integer scoopVolume;
    @SerializedName("fecha_despacho")
    private Date dispatchDate;
    @SerializedName("fecha_salida")
    private Date departureDate;
    @SerializedName("fecha_llegada")
    private Date arrivalDate;
    @SerializedName("depositador")
    private String payer;
    @SerializedName("banco")
    private String bank;
    @SerializedName("n_deposito")
    private String transactionNumber;
    @SerializedName("monto_entregado")
    private Double amountDelivered;
    @SerializedName("monto_adicional")
    private Double additionalAmount;
    @SerializedName("n_factura")
    private String invoiceNumber;
    @SerializedName("fecha_factura")
    private Date invoiceDate;
    @SerializedName("monto_factura")
    private Double invoiceAmount;
    @SerializedName("detalle_gastos")
    private List<ExpenseDto> expenses;
    @SerializedName("informacion_ruta_porcentaje")
    private List<ProductDispatchDto> productDispatches;
    @SerializedName("estado")
    private Integer state;
    @SerializedName("date_created")
    private String dateCreated;
    @SerializedName("nombre")
    private String name;
    @SerializedName("observacion")
    private String observation;

    public Long getDeclarationNumber() {
        return declarationNumber;
    }

    public void setDeclarationNumber(Long declarationNumber) {
        this.declarationNumber = declarationNumber;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Integer getScoopCode() {
        return scoopCode;
    }

    public void setScoopCode(Integer scoopCode) {
        this.scoopCode = scoopCode;
    }

    public Integer getScoopVolume() {
        return scoopVolume;
    }

    public void setScoopVolume(Integer scoopVolume) {
        this.scoopVolume = scoopVolume;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Double getAmountDelivered() {
        return amountDelivered;
    }

    public void setAmountDelivered(Double amountDelivered) {
        this.amountDelivered = amountDelivered;
    }

    public Double getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(Double additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public List<ProductDispatchDto> getProductDispatches() {
        return productDispatches;
    }

    public void setProductDispatches(List<ProductDispatchDto> productDispatches) {
        this.productDispatches = productDispatches;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
