package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class SupplierDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("proveedor")
    private String name;
    @SerializedName("ruc")
    private String ruc;
    @SerializedName("concepto")
    private String concept;

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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }
}
