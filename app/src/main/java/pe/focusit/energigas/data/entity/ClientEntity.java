package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "CLIENT")
public class ClientEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private String ruc;
    private String location;
    private String latitude;
    private String longitude;
    @Generated(hash = 2088429684)
    public ClientEntity(Long id, String name, String description, String ruc,
            String location, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ruc = ruc;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1526468954)
    public ClientEntity() {
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
    public String getRuc() {
        return this.ruc;
    }
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
