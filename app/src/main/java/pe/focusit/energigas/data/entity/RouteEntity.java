package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(nameInDb = "ROUTE")
public class RouteEntity {

    @Id
    private Long id;
    private Long unitTypeId;
    private Long driverId;
    private Long vehicleId;
    private String name;
    private String departurePlace;
    private String fuelToTransport;
    private String distance;
    private Integer viaticum;
    private Integer transportation;
    private Integer hotel;
    private Integer parking;
    private Double dayTotal;
    private Integer numDays;
    private Double amountGiven;
    @ToOne(joinProperty = "vehicleId")
    private VehicleEntity vehicle;
    @ToMany(referencedJoinProperty = "routeId")
    private List<RouteSegmentEntity> routeSegments;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2117834311)
    private transient RouteEntityDao myDao;
    @Generated(hash = 1545584340)
    public RouteEntity(Long id, Long unitTypeId, Long driverId, Long vehicleId,
            String name, String departurePlace, String fuelToTransport,
            String distance, Integer viaticum, Integer transportation,
            Integer hotel, Integer parking, Double dayTotal, Integer numDays,
            Double amountGiven) {
        this.id = id;
        this.unitTypeId = unitTypeId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.name = name;
        this.departurePlace = departurePlace;
        this.fuelToTransport = fuelToTransport;
        this.distance = distance;
        this.viaticum = viaticum;
        this.transportation = transportation;
        this.hotel = hotel;
        this.parking = parking;
        this.dayTotal = dayTotal;
        this.numDays = numDays;
        this.amountGiven = amountGiven;
    }
    @Generated(hash = 2061632751)
    public RouteEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUnitTypeId() {
        return this.unitTypeId;
    }
    public void setUnitTypeId(Long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }
    public Long getDriverId() {
        return this.driverId;
    }
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    public Long getVehicleId() {
        return this.vehicleId;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDeparturePlace() {
        return this.departurePlace;
    }
    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }
    public String getFuelToTransport() {
        return this.fuelToTransport;
    }
    public void setFuelToTransport(String fuelToTransport) {
        this.fuelToTransport = fuelToTransport;
    }
    public String getDistance() {
        return this.distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public Integer getViaticum() {
        return this.viaticum;
    }
    public void setViaticum(Integer viaticum) {
        this.viaticum = viaticum;
    }
    public Integer getTransportation() {
        return this.transportation;
    }
    public void setTransportation(Integer transportation) {
        this.transportation = transportation;
    }
    public Integer getHotel() {
        return this.hotel;
    }
    public void setHotel(Integer hotel) {
        this.hotel = hotel;
    }
    public Integer getParking() {
        return this.parking;
    }
    public void setParking(Integer parking) {
        this.parking = parking;
    }
    public Double getDayTotal() {
        return this.dayTotal;
    }
    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }
    public Integer getNumDays() {
        return this.numDays;
    }
    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }
    public Double getAmountGiven() {
        return this.amountGiven;
    }
    public void setAmountGiven(Double amountGiven) {
        this.amountGiven = amountGiven;
    }
    @Generated(hash = 913495357)
    private transient Long vehicle__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 633105719)
    public VehicleEntity getVehicle() {
        Long __key = this.vehicleId;
        if (vehicle__resolvedKey == null || !vehicle__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VehicleEntityDao targetDao = daoSession.getVehicleEntityDao();
            VehicleEntity vehicleNew = targetDao.load(__key);
            synchronized (this) {
                vehicle = vehicleNew;
                vehicle__resolvedKey = __key;
            }
        }
        return vehicle;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 656817856)
    public void setVehicle(VehicleEntity vehicle) {
        synchronized (this) {
            this.vehicle = vehicle;
            vehicleId = vehicle == null ? null : vehicle.getId();
            vehicle__resolvedKey = vehicleId;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1322970716)
    public List<RouteSegmentEntity> getRouteSegments() {
        if (routeSegments == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RouteSegmentEntityDao targetDao = daoSession.getRouteSegmentEntityDao();
            List<RouteSegmentEntity> routeSegmentsNew = targetDao
                    ._queryRouteEntity_RouteSegments(id);
            synchronized (this) {
                if (routeSegments == null) {
                    routeSegments = routeSegmentsNew;
                }
            }
        }
        return routeSegments;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1221734057)
    public synchronized void resetRouteSegments() {
        routeSegments = null;
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
    @Generated(hash = 859181491)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRouteEntityDao() : null;
    }

}
