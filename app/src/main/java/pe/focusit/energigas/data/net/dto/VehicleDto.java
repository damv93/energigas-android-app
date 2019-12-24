package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class VehicleDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("id_tipo_movilidad")
    private Long vehicleTypeId;
    @SerializedName("tracto_unidad")
    private String unitTract;
    @SerializedName("botella")
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
