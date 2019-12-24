package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "EXPENSE")
public class ExpenseEntity {

    @Id
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
    private String observation;
    private Boolean isExternalProvider;
    private String energigasStation;
    private Double gallons;
    private Integer mileage;
    @ToOne(joinProperty = "expenseTypeId")
    private ExpenseTypeEntity expenseType;
    @ToOne(joinProperty = "expenseSubTypeId")
    private ExpenseTypeEntity expenseSubType;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 422623728)
    private transient ExpenseEntityDao myDao;
    @Generated(hash = 1553037333)
    public ExpenseEntity(Long id, Long routeSegmentId, Long expenseTypeId,
            Long expenseSubTypeId, Date date, Long supplierId, String supplierName,
            String rucNumber, String voucherType, String voucherNumber,
            String photoFilePath, Double amount, String observation,
            Boolean isExternalProvider, String energigasStation, Double gallons,
            Integer mileage) {
        this.id = id;
        this.routeSegmentId = routeSegmentId;
        this.expenseTypeId = expenseTypeId;
        this.expenseSubTypeId = expenseSubTypeId;
        this.date = date;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.rucNumber = rucNumber;
        this.voucherType = voucherType;
        this.voucherNumber = voucherNumber;
        this.photoFilePath = photoFilePath;
        this.amount = amount;
        this.observation = observation;
        this.isExternalProvider = isExternalProvider;
        this.energigasStation = energigasStation;
        this.gallons = gallons;
        this.mileage = mileage;
    }
    @Generated(hash = 1555675139)
    public ExpenseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRouteSegmentId() {
        return this.routeSegmentId;
    }
    public void setRouteSegmentId(Long routeSegmentId) {
        this.routeSegmentId = routeSegmentId;
    }
    public Long getExpenseTypeId() {
        return this.expenseTypeId;
    }
    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
    public Long getExpenseSubTypeId() {
        return this.expenseSubTypeId;
    }
    public void setExpenseSubTypeId(Long expenseSubTypeId) {
        this.expenseSubTypeId = expenseSubTypeId;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Long getSupplierId() {
        return this.supplierId;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public String getSupplierName() {
        return this.supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getRucNumber() {
        return this.rucNumber;
    }
    public void setRucNumber(String rucNumber) {
        this.rucNumber = rucNumber;
    }
    public String getVoucherType() {
        return this.voucherType;
    }
    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }
    public String getVoucherNumber() {
        return this.voucherNumber;
    }
    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }
    public String getPhotoFilePath() {
        return this.photoFilePath;
    }
    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getObservation() {
        return this.observation;
    }
    public void setObservation(String observation) {
        this.observation = observation;
    }
    public Boolean getIsExternalProvider() {
        return this.isExternalProvider;
    }
    public void setIsExternalProvider(Boolean isExternalProvider) {
        this.isExternalProvider = isExternalProvider;
    }
    public String getEnergigasStation() {
        return this.energigasStation;
    }
    public void setEnergigasStation(String energigasStation) {
        this.energigasStation = energigasStation;
    }
    public Double getGallons() {
        return this.gallons;
    }
    public void setGallons(Double gallons) {
        this.gallons = gallons;
    }
    public Integer getMileage() {
        return this.mileage;
    }
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    @Generated(hash = 1510967895)
    private transient Long expenseType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 342604874)
    public ExpenseTypeEntity getExpenseType() {
        Long __key = this.expenseTypeId;
        if (expenseType__resolvedKey == null
                || !expenseType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExpenseTypeEntityDao targetDao = daoSession.getExpenseTypeEntityDao();
            ExpenseTypeEntity expenseTypeNew = targetDao.load(__key);
            synchronized (this) {
                expenseType = expenseTypeNew;
                expenseType__resolvedKey = __key;
            }
        }
        return expenseType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 27586626)
    public void setExpenseType(ExpenseTypeEntity expenseType) {
        synchronized (this) {
            this.expenseType = expenseType;
            expenseTypeId = expenseType == null ? null : expenseType.getId();
            expenseType__resolvedKey = expenseTypeId;
        }
    }
    @Generated(hash = 117623716)
    private transient Long expenseSubType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1907077939)
    public ExpenseTypeEntity getExpenseSubType() {
        Long __key = this.expenseSubTypeId;
        if (expenseSubType__resolvedKey == null
                || !expenseSubType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExpenseTypeEntityDao targetDao = daoSession.getExpenseTypeEntityDao();
            ExpenseTypeEntity expenseSubTypeNew = targetDao.load(__key);
            synchronized (this) {
                expenseSubType = expenseSubTypeNew;
                expenseSubType__resolvedKey = __key;
            }
        }
        return expenseSubType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 877819371)
    public void setExpenseSubType(ExpenseTypeEntity expenseSubType) {
        synchronized (this) {
            this.expenseSubType = expenseSubType;
            expenseSubTypeId = expenseSubType == null ? null
                    : expenseSubType.getId();
            expenseSubType__resolvedKey = expenseSubTypeId;
        }
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
    @Generated(hash = 104243542)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExpenseEntityDao() : null;
    }
}
