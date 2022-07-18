package com.application.foggy.api;

import com.application.foggy.modals.LoginSignupModal;
import com.application.foggy.modals.ProductModals;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRepository {

    @POST("/api/v1/userdetailsmanagement/loginsignup")
    Call<LoginSignupModal> getLoginSignupDetails(@Body LoginSignupModal loginSignupModal);

    @GET("/api/v1/products/getall")
    Call<List<ProductModals>>  getAllProducts();

    @POST("/api/v1/products/create")
    Call<ProductModals> createProduct(@Body ProductModals product);
}
