package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "ROUTE_SEGMENT")
public class RouteSegmentEntity {

    @Id
    private Long id;
    private Long routeId;
    private Integer stopNumber;
    private String stopPlace;
    private Long clientId;
    private Integer requestedAmount;
    @ToOne(joinProperty = "clientId")
    private ClientEntity client;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1566002726)
    private transient RouteSegmentEntityDao myDao;
    @Generated(hash = 1910796434)
    public RouteSegmentEntity(Long id, Long routeId, Integer stopNumber,
            String stopPlace, Long clientId, Integer requestedAmount) {
        this.id = id;
        this.routeId = routeId;
        this.stopNumber = stopNumber;
        this.stopPlace = stopPlace;
        this.clientId = clientId;
        this.requestedAmount = requestedAmount;
    }
    @Generated(hash = 132879407)
    public RouteSegmentEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public Integer getStopNumber() {
        return this.stopNumber;
    }
    public void setStopNumber(Integer stopNumber) {
        this.stopNumber = stopNumber;
    }
    public String getStopPlace() {
        return this.stopPlace;
    }
    public void setStopPlace(String stopPlace) {
        this.stopPlace = stopPlace;
    }
    public Long getClientId() {
        return this.clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public Integer getRequestedAmount() {
        return this.requestedAmount;
    }
    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    @Generated(hash = 1636229693)
    private transient Long client__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1455329522)
    public ClientEntity getClient() {
        Long __key = this.clientId;
        if (client__resolvedKey == null || !client__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClientEntityDao targetDao = daoSession.getClientEntityDao();
            ClientEntity clientNew = targetDao.load(__key);
            synchronized (this) {
                client = clientNew;
                client__resolvedKey = __key;
            }
        }
        return client;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 249365278)
    public void setClient(ClientEntity client) {
        synchronized (this) {
            this.client = client;
            clientId = client == null ? null : client.getId();
            client__resolvedKey = clientId;
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
    @Generated(hash = 1286634710)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRouteSegmentEntityDao() : null;
    }
}
