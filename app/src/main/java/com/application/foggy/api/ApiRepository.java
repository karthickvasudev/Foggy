package com.application.foggy.api;

import com.application.foggy.modals.CustomerModal;
import com.application.foggy.modals.LoginSignupModal;
import com.application.foggy.modals.ProductModals;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRepository {

    @POST("/api/v1/userdetailsmanagement/loginsignup")
    Call<LoginSignupModal> getLoginSignupDetails(@Body LoginSignupModal loginSignupModal);

    @GET("/api/v1/products/getall")
    Call<List<ProductModals>> getAllProducts();

    @POST("/api/v1/products/create")
    Call<ProductModals> createProduct(@Body ProductModals product);

    @DELETE("/api/v1/products/{productId}")
    Call<Void> deleteProduct(@Path("productId") String productId);

    @PUT("/api/v1/products/{productId}")
    Call<ProductModals> updateProduct(@Path("productId") String updateProductId, @Body ProductModals product);

    @GET("/api/v1/products/{productId}")
    Call<ProductModals> getProductDetails(@Path("productId")String productId);

    @GET("/api/v1/customers/getall")
    Call<List<CustomerModal>> getAllCustomers();
}
