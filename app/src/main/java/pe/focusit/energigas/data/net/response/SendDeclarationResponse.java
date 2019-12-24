package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

public class SendDeclarationResponse extends ApiBaseResponse {
    @SerializedName("nro_rendicion")
    private Long declarationNumber;

    public Long getDeclarationNumber() {
        return declarationNumber;
    }

    public void setDeclarationNumber(Long declarationNumber) {
        this.declarationNumber = declarationNumber;
    }
}
