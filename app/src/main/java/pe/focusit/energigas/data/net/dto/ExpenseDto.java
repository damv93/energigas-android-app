package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExpenseDto {

    @SerializedName("id_ruta_cliente")
    private Long routeSegmentId;
    @SerializedName("id_tipo_gasto")
    private Long expenseTypeId;
    @SerializedName("id_sub_tipo_gasto")
    private Long expenseSubTypeId;
    @SerializedName("tipo")
    private Integer type;
    @SerializedName("fecha")
    private Date date;
    @SerializedName("id_proveedor")
    private Long supplierId;
    @SerializedName("ruc")
    private String rucNumber;
    @SerializedName("tipo_comprobante")
    private String voucherType;
    @SerializedName("n_comprobante")
    private String voucherNumber;
    @SerializedName("nom_arch_foto")
    private String photoFileName;
    @SerializedName("foto")
    private String photoFile;
    @SerializedName("monto")
    private Double amount;
    @SerializedName("comentario_chofer")
    private String observation;
    /* Fields related to the fuel consumption expense type */
    @SerializedName("es_proveedor_externo")
    private Boolean isExternalProvider;
    @SerializedName("estacion")
    private String energigasStation;
    @SerializedName("galones")
    private Double gallons;
    @SerializedName("kilometraje")
    private Integer mileage;

    public Long getRouteSegmentId() {
        return routeSegmentId;
    }

    public void setRouteSegmentId(Long routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRucNumber() {
        return rucNumber;
    }

    public void setRucNumber(String rucNumber) {
        this.rucNumber = rucNumber;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getExpenseSubTypeId() {
        return expenseSubTypeId;
    }

    public void setExpenseSubTypeId(Long expenseSubTypeId) {
        this.expenseSubTypeId = expenseSubTypeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
