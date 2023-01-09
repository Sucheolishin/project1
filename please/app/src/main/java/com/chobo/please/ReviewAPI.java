package com.chobo.please;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewAPI {
    @GET("/review/getReview/{market_name}")
    Call<List<Review>>getReview(@Path ("market_name") String market_name);
    @PUT("/review/addReview/{name}/{market_name}/{date}/{review_text}")
    Call<Boolean> putReview(@Path("name")String name,@Path("market_name")String market_name, @Path("date")String date, @Path("review_text")String review_text);
    @GET("/review/getreview/{name}")
    Call<List<Review>>getMyReview(@Path("name")String name);
}
