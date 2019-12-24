package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

public class GasStationDto {
    @SerializedName("nombre")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
