package pe.focusit.energigas.data.net.response;

import com.google.gson.annotations.SerializedName;

import pe.focusit.energigas.data.net.dto.UserDto;

public class LoginResponse extends ApiBaseResponse {

    @SerializedName("data")
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
