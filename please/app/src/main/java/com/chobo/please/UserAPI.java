package com.chobo.please;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserAPI {
    @POST("/user/login")
    Call<Integer> register(@Body User user);      //해당 API는 바디가 필요함. 바디를 넣어주어야 한다고 인지시켜줌
    @PUT("/user/register")
    Call<Integer> insertUser(@Body User user);
}
