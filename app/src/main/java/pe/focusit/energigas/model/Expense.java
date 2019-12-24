package pe.focusit.energigas.model;

import java.util.Date;

public class Expense {

    public static final String INVOICE_VOUCHER_TYPE = "Factura";
    public static final String BILL_VOUCHER_TYPE = "Boleta";

    private Long id;
    private Long routeSegmentId;
    private Long expenseTypeId;
    private Long expenseSubTypeId;
    private Date date;
    private Long supplierId;
    private String supplierName;
    private String rucNumber;
    private String voucherType;
    private String voucherNumber;
    private String photoFilePath;
    private Double amount;
    private ExpenseType expenseType;
    private ExpenseType expenseSubType;
    private String observation;
    private Boolean isExternalProvider;
    private String energigasStation;
    private Double gallons;
    private Integer mileage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteSegmentId() {
        return routeSegmentId;
    }

    public void setRouteSegmentId(Long routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public Long getExpenseSubTypeId() {
        return expenseSubTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
        this.expenseTypeId = (expenseType == null) ? null : expenseType.getId();
    }

    public ExpenseType getExpenseSubType() {
        return expenseSubType;
    }

    public void setExpenseSubType(ExpenseType expenseType) {
        this.expenseSubType = expenseType;
        this.expenseSubTypeId = (expenseType == null) ? null : expenseType.getId();
    }

    public String getRucNumber() {
        return rucNumber;
    }

    public void setRucNumber(String rucNumber) {
        this.rucNumber = rucNumber;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Boolean getExternalProvider() {
        return isExternalProvider;
    }

    public void setExternalProvider(Boolean externalProvider) {
        isExternalProvider = externalProvider;
    }

    public String getEnergigasStation() {
        return energigasStation;
    }

    public void setEnergigasStation(String energigasStation) {
        this.energigasStation = energigasStation;
    }

    public Double getGallons() {
        return gallons;
    }

    public void setGallons(Double gallons) {
        this.gallons = gallons;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
}
