package com.application.foggy.api;

import androidx.annotation.NonNull;

import com.application.foggy.constants.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Constants.getToken())
                .build();
        return chain.proceed(request);
    }
}
