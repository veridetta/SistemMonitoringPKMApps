package com.inc.vr.corps.sistemmonitoringpkm.api;

import com.inc.vr.corps.sistemmonitoringpkm.model.LoginModel;
import com.inc.vr.corps.sistemmonitoringpkm.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;

public interface ApiService {
    public static final String BASE_URL = "http://192.168.43.235/pkm/api/";
    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String REGISTER = BASE_URL + "register.php";

    //Form Login
    @FormUrlEncoded
    @retrofit2.http.POST(LOGIN_URL)
    Call<LoginModel> login(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(REGISTER)
    Call<RegisterResponse> register(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("email") String email,
            @retrofit2.http.Field("name") String name,
            @retrofit2.http.Field("role") String role,
            @retrofit2.http.Field("apps") String apps
    );
}
