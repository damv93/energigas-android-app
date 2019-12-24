package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

public abstract class ApiBaseResponse {

    @SerializedName("ok")
    private Boolean ok;
    @SerializedName("msg")
    private String msg;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
