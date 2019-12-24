package pe.focusit.energigas.data.net.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DepositDto {

    @SerializedName("id_ruta_chofer_unidad")
    private Long routeId;
    @SerializedName("monto")
    private Double amount;
    @SerializedName("n_operacion")
    private String operationNumber;
    @SerializedName("fecha")
    private Date date;
    @SerializedName("concepto")
    private String concept;

}
