package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "EXPENSE_TYPE")
public class ExpenseTypeEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer type;
    private Long parentId;
    @ToMany(referencedJoinProperty = "parentId")
    private List<ExpenseTypeEntity> children;
    @ToMany
    @JoinEntity(
            entity = SupplierxExpenseTypeEntity.class,
            sourceProperty = "expenseTypeId",
            targetProperty = "supplierId"
    )
    private List<SupplierEntity> suppliers;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469330330)
    private transient ExpenseTypeEntityDao myDao;
    @Generated(hash = 1105467566)
    public ExpenseTypeEntity(Long id, String name, String description, Integer type,
            Long parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.parentId = parentId;
    }
    @Generated(hash = 614862388)
    public ExpenseTypeEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getType() {
        return this.type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 403397434)
    public List<ExpenseTypeEntity> getChildren() {
        if (children == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExpenseTypeEntityDao targetDao = daoSession.getExpenseTypeEntityDao();
            List<ExpenseTypeEntity> childrenNew = targetDao
                    ._queryExpenseTypeEntity_Children(id);
            synchronized (this) {
                if (children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1590975152)
    public synchronized void resetChildren() {
        children = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1061336265)
    public List<SupplierEntity> getSuppliers() {
        if (suppliers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SupplierEntityDao targetDao = daoSession.getSupplierEntityDao();
            List<SupplierEntity> suppliersNew = targetDao
                    ._queryExpenseTypeEntity_Suppliers(id);
            synchronized (this) {
                if (suppliers == null) {
                    suppliers = suppliersNew;
                }
            }
        }
        return suppliers;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 957327039)
    public synchronized void resetSuppliers() {
        suppliers = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1408640991)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExpenseTypeEntityDao() : null;
    }

}
