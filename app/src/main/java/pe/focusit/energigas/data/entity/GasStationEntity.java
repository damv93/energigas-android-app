package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "GAS_STATION")
public class GasStationEntity {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    @Generated(hash = 576655846)
    public GasStationEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 793905771)
    public GasStationEntity() {
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
}
