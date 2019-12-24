package pe.focusit.energigas.model;

import java.util.Date;
import java.util.List;

import pe.focusit.energigas.data.net.dto.ExpenseDto;
import pe.focusit.energigas.data.net.dto.ProductDispatchDto;

public class Declaration {

    private Long declarationNumber;
    private Long routeId;
    private Integer scoopCode;
    private Integer scoopVolume;
    private Date dispatchDate;
    private Date departureDate;
    private Date arrivalDate;
    private String payer;
    private String bank;
    private String transactionNumber;
    private Double amountDelivered;
    private Double additionalAmount;
    private String invoiceNumber;
    private Date invoiceDate;
    private Double invoiceAmount;
    private List<ExpenseDto> expenses;
    private List<ProductDispatchDto> productDispatches;
    private Integer state;
    private Date dateCreated;
    private String name;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
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
