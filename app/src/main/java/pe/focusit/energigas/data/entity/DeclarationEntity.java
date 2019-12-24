package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "DECLARATION")
public class DeclarationEntity {

    @Id
    private Long routeId;
    private Long declarationNumber;
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
    @Generated(hash = 629414952)
    public DeclarationEntity(Long routeId, Long declarationNumber,
            Integer scoopCode, Integer scoopVolume, Date dispatchDate,
            Date departureDate, Date arrivalDate, String payer, String bank,
            String transactionNumber, Double amountDelivered,
            Double additionalAmount, String invoiceNumber, Date invoiceDate,
            Double invoiceAmount) {
        this.routeId = routeId;
        this.declarationNumber = declarationNumber;
        this.scoopCode = scoopCode;
        this.scoopVolume = scoopVolume;
        this.dispatchDate = dispatchDate;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.payer = payer;
        this.bank = bank;
        this.transactionNumber = transactionNumber;
        this.amountDelivered = amountDelivered;
        this.additionalAmount = additionalAmount;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
    }
    @Generated(hash = 1298088097)
    public DeclarationEntity() {
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public Long getDeclarationNumber() {
        return this.declarationNumber;
    }
    public void setDeclarationNumber(Long declarationNumber) {
        this.declarationNumber = declarationNumber;
    }
    public Integer getScoopCode() {
        return this.scoopCode;
    }
    public void setScoopCode(Integer scoopCode) {
        this.scoopCode = scoopCode;
    }
    public Integer getScoopVolume() {
        return this.scoopVolume;
    }
    public void setScoopVolume(Integer scoopVolume) {
        this.scoopVolume = scoopVolume;
    }
    public Date getDispatchDate() {
        return this.dispatchDate;
    }
    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }
    public Date getDepartureDate() {
        return this.departureDate;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public Date getArrivalDate() {
        return this.arrivalDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public String getPayer() {
        return this.payer;
    }
    public void setPayer(String payer) {
        this.payer = payer;
    }
    public String getBank() {
        return this.bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getTransactionNumber() {
        return this.transactionNumber;
    }
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    public Double getAmountDelivered() {
        return this.amountDelivered;
    }
    public void setAmountDelivered(Double amountDelivered) {
        this.amountDelivered = amountDelivered;
    }
    public Double getAdditionalAmount() {
        return this.additionalAmount;
    }
    public void setAdditionalAmount(Double additionalAmount) {
        this.additionalAmount = additionalAmount;
    }
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public Date getInvoiceDate() {
        return this.invoiceDate;
    }
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    public Double getInvoiceAmount() {
        return this.invoiceAmount;
    }
    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

}
