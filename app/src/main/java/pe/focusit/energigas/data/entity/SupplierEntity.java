package pe.focusit.energigas.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "SUPPLIER")
public class SupplierEntity {
    @Id
    private Long id;
    private String name;
    private String ruc;
    private String concept;
    @Generated(hash = 386320891)
    public SupplierEntity(Long id, String name, String ruc, String concept) {
        this.id = id;
        this.name = name;
        this.ruc = ruc;
        this.concept = concept;
    }
    @Generated(hash = 57222699)
    public SupplierEntity() {
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
    public String getRuc() {
        return this.ruc;
    }
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    public String getConcept() {
        return this.concept;
    }
    public void setConcept(String concept) {
        this.concept = concept;
    }
}
