package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pe.focusit.energigas.data.net.dto.DeclarationDto;

public class GetDeclarationsResponse extends ApiBaseResponse {

    @SerializedName("pendientes")
    private List<DeclarationDto> pending;
    @SerializedName("observadas")
    private List<DeclarationDto> observed;
    @SerializedName("aprobadas")
    private List<DeclarationDto> approved;

    public List<DeclarationDto> getPending() {
        return pending;
    }

    public void setPending(List<DeclarationDto> pending) {
        this.pending = pending;
    }

    public List<DeclarationDto> getObserved() {
        return observed;
    }

    public void setObserved(List<DeclarationDto> observed) {
        this.observed = observed;
    }

    public List<DeclarationDto> getApproved() {
        return approved;
    }

    public void setApproved(List<DeclarationDto> approved) {
        this.approved = approved;
    }
}
