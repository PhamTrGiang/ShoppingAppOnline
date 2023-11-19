package com.example.onlineshopingapp.API;

import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.Model.CartModel;
import com.example.onlineshopingapp.Model.OrderModel;
import com.example.onlineshopingapp.Model.ProductModel;
import com.example.onlineshopingapp.Model.ProfileModel;
import com.example.onlineshopingapp.Model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //account
    @POST("login")
    Call<String> setLogin(@Body UserModel user);

    @POST("register")
    Call<String> setRegister(@Body UserModel user);

    @PUT("changeStatus")
    Call<String> setStatus(@Body UserModel model);

    @GET("user")
    Call<ArrayList<UserModel>> getUser();

    @GET("status/{id}")
    Call<Integer> getStatus(@Path("id") String id);

    @GET("profile/{id}")
    Call<ProfileModel> getProfile(@Path("id") String id);

    @POST("profile/add")
    Call<ProfileModel> setProfile(@Body ProfileModel model);

    //product
    @GET("product")
    Call<ArrayList<ProductModel>> getProduct();

    @POST("product/add")
    Call<ArrayList<ProductModel>> addProduct(@Body ProductModel product);

    @PUT("product/edit/{id}")
    Call<ArrayList<ProductModel>> editProduct(@Path("id") String id, @Body ProductModel product);

    @DELETE("product/delete/{id}")
    Call<ArrayList<ProductModel>> deleteProduct(@Path("id") String id);

    @GET("product/{id}")
    Call<ProductModel> getProductDetail(@Path("id") String id);


    //brand
    @GET("brand")
    Call<ArrayList<BrandModel>> getBrand();

    @POST("brand/add")
    Call<ArrayList<BrandModel>> addBrand(@Body BrandModel brand);

    @PUT("brand/edit/{id}")
    Call<ArrayList<BrandModel>> editBrand(@Path("id") String id, @Body BrandModel brand);

    @DELETE("brand/delete/{id}")
    Call<ArrayList<BrandModel>> deleteBrand(@Path("id") String id);


    //cart
    @GET("cart/{id}")
    Call<ArrayList<CartModel>> getCart(@Path("id") String id);

    @POST("cart/add")
    Call<String> addCart(@Body CartModel cart);

    @DELETE("cart/delete/{userId}/{id}")
    Call<ArrayList<CartModel>> deleteCart(@Path("id") String id,@Path("userId") String userId);

    //order
    @GET("order/{id}")
    Call<ArrayList<OrderModel>> getOrder(@Path("id") String id);

    @POST("order/add")
    Call<String> addOrder(@Body OrderModel order);

    @PUT("changeOrder")
    Call<String> setOrder(@Body OrderModel model);
}
