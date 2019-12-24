package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class ClientDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("nombre")
    private String name;
    @SerializedName("descripcion")
    private String description;
    @SerializedName("ruc")
    private String ruc;
    @SerializedName("ubicacion")
    private String location;
    @SerializedName("latitud")
    private String latitude;
    @SerializedName("longitud")
    private String longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
