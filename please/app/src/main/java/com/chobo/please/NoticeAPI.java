package com.chobo.please;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NoticeAPI {
    @GET("/notice/getNotice/{id}")
    Call<List<Notice>> getReview(@Path("id") String market_name);
}
