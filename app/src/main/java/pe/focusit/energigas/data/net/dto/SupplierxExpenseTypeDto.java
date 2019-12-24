package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class SupplierxExpenseTypeDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("id_proveedor")
    private Long supplierId;
    @SerializedName("id_tipo_gasto")
    private Long expenseTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
