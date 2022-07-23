package com.application.foggy.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    private static final String BASE_URL = "http://10.202.242.121:8080";

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new ApiInterceptor())
            .build();

    public static ApiRepository getApiRepository() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRepository.class);
    }

    public static ApiRepository getApiRepositoryWithToken() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRepository.class);
    }
}
