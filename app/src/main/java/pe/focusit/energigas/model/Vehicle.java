package pe.focusit.energigas.model;

public class Vehicle {

    private Long id;
    private Long vehicleTypeId;
    private String unitTract;
    private String bottle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getUnitTract() {
        return unitTract;
    }

    public void setUnitTract(String unitTract) {
        this.unitTract = unitTract;
    }

    public String getBottle() {
        return bottle;
    }

    public void setBottle(String bottle) {
        this.bottle = bottle;
    }
}
