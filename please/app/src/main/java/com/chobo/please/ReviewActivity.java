package com.chobo.please;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends Activity {
    LayoutInflater layoutInflater;
    LinearLayout container;
    View view;
    Context context;
    String userName;
    List<Review> reviewList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);
        context = this;
        userName = getIntent().getStringExtra("userName");

        container = findViewById(R.id.my_review);

        layoutInflater = LayoutInflater.from(this);

        Connection connection = new Connection();
        ReviewAPI reviewAPI = connection.getRetrofit().create(ReviewAPI.class);
        reviewAPI.getMyReview(userName).enqueue(new Callback<List<Review>>(){
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                reviewList.clear();
                reviewList = response.body();
                if(reviewList != null){
                    for(int i = 0; i<(reviewList.size()); i++){
                        view = layoutInflater.inflate(R.layout.notice_layout,null, false);
                        TextView nameText = view.findViewById(R.id.ntask1name);
                        nameText.setText(reviewList.get(i).getMarket_name());
                        TextView dateText = view.findViewById(R.id.ntask1date);
                        dateText.setText(reviewList.get(i).getDate());
                        TextView readText = view.findViewById(R.id.ntask1con);
                        readText.setText(reviewList.get(i).getReview_text());

                        container.addView(view);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "리뷰리스트가 없음", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });


    }
}
