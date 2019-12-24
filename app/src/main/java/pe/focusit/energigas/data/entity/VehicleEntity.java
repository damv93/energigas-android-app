package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "VEHICLE")
public class VehicleEntity {

    @Id
    private Long id;
    private Long vehicleTypeId;
    private String unitTract;
    private String bottle;
    @Generated(hash = 533044796)
    public VehicleEntity(Long id, Long vehicleTypeId, String unitTract,
            String bottle) {
        this.id = id;
        this.vehicleTypeId = vehicleTypeId;
        this.unitTract = unitTract;
        this.bottle = bottle;
    }
    @Generated(hash = 1055610634)
    public VehicleEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getVehicleTypeId() {
        return this.vehicleTypeId;
    }
    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
    public String getUnitTract() {
        return this.unitTract;
    }
    public void setUnitTract(String unitTract) {
        this.unitTract = unitTract;
    }
    public String getBottle() {
        return this.bottle;
    }
    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

}
