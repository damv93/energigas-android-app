package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "SUPPLIER_X_EXPENSE_TYPE")
public class SupplierxExpenseTypeEntity {
    @Id
    private Long id;
    private Long supplierId;
    private Long expenseTypeId;
    @Generated(hash = 655985621)
    public SupplierxExpenseTypeEntity(Long id, Long supplierId,
            Long expenseTypeId) {
        this.id = id;
        this.supplierId = supplierId;
        this.expenseTypeId = expenseTypeId;
    }
    @Generated(hash = 1210317701)
    public SupplierxExpenseTypeEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSupplierId() {
        return this.supplierId;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public Long getExpenseTypeId() {
        return this.expenseTypeId;
    }
    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
