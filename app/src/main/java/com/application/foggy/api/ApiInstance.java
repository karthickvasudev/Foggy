package com.application.foggy.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static ApiRepository getApiRepository() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRepository.class);
    }
}
