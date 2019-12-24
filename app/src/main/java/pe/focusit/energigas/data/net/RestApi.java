package pe.focusit.energigas.data.net;

import pe.focusit.energigas.data.net.dto.DeclarationDto;
import pe.focusit.energigas.data.net.dto.UserDto;
import pe.focusit.energigas.data.net.response.GetDeclarationsResponse;
import pe.focusit.energigas.data.net.response.LoginResponse;
import pe.focusit.energigas.data.net.response.MyRouteResponse;
import pe.focusit.energigas.data.net.response.SendDeclarationResponse;
import pe.focusit.energigas.data.net.response.SyncResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @POST("login/login")
    Call<LoginResponse> login(@Body UserDto user);

    @GET("sync")
    Call<SyncResponse> sync();

    @GET("rutas/mi_ruta")
    Call<MyRouteResponse> getMyRoute();

    @POST("rutas/rendicion_cuenta")
    Call<SendDeclarationResponse> sendDeclaration(@Body DeclarationDto declaration);

    @GET("rendicion_cuenta/rendiciones")
    Call<GetDeclarationsResponse> getDeclarations();

}
