package com.example.zhulinaproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("Users/{pass}")
    Call<UsersMask> createPostUsers(@Query("pass") String pass, @Body UsersMask mask);

    @POST("Selecteds/")
    Call<SelectedsMask> createPostSelecteds(@Body SelectedsMask mask);

    @PUT("Selecteds/{id}")
    Call<SelectedsMask> createPutSelecteds(@Query("id") int id, @Body SelectedsMask mask);

    @DELETE("Selecteds/{id}")
    Call<SelectedsMask> deleteData(@Query("id") int id);
}
