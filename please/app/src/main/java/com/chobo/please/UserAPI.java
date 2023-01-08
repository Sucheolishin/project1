package com.chobo.please;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("/user/login")
    Call<Boolean> register(@Body User user);      //해당 API는 바디가 필요함. 바디를 넣어주어야 한다고 인지시켜줌
    @PUT("/user/register/{id}/{password}/{name}")
    Call<Boolean> insertUser(@Path("id")String id,@Path("password")String password, @Path("name")String name);
    @GET("/user/getUserName/{id}")
    Call<String> getUserName(@Path("id")String id);
}
