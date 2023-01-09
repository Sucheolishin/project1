package com.chobo.please;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends Activity {
    String postName;
    String userName;
    String date;
    String reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        postName = getIntent().getStringExtra("PostName");
        userName = getIntent().getStringExtra("UserName");
        TextView shopName = findViewById(R.id.post_text);
        EditText shopReview = findViewById(R.id.review_text);
        Button writeBtn = findViewById(R.id.review_write);


        shopName.setText(postName);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long mNow = System.currentTimeMillis();
                Date mDate = new Date(mNow);
                SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
                reviewText = shopReview.getText().toString();
                date = mFormat.format(mDate);
                Connection connection = new Connection();
                ReviewAPI reviewAPI = connection.getRetrofit().create(ReviewAPI.class);
                reviewAPI.putReview(userName, date, postName, reviewText)
                        .enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                System.out.println(userName);
                                System.out.println(postName);
                                System.out.println(date);
                                System.out.println(reviewText);
                                if(response.body()){
                                    Toast.makeText(getApplicationContext(), "리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(WriteActivity.this, MainActivity.class );
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "바디없음 실패", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "리뷰 작성 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
