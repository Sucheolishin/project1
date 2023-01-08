package com.chobo.please;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostAPI {
    @GET("/post/{category}")
    Call<List<Post>> getPostInfo(@Path ("category") int category);
}
