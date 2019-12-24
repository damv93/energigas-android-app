package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class ExpenseTypeDto {

    @SerializedName("id")
    private Long id;
    @SerializedName("nombre")
    private String name;
    @SerializedName("descripcion")
    private String description;
    @SerializedName("tipo")
    private Integer type;
    @SerializedName("id_padre")
    private Long parentId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
