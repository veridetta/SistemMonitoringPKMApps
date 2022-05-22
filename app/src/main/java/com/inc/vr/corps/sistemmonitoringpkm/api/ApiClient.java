package com.inc.vr.corps.sistemmonitoringpkm.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiService apiService;

    public ApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost/pkm/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getServies(){
        return apiService;
    }

}
