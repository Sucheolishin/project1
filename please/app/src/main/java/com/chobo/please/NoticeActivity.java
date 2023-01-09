package com.chobo.please;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends Activity {
    LayoutInflater layoutInflater;
    LinearLayout container;
    View view;
    Context context;
    String userName;
    List<Notice> noticeList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        context = this;
        userName = getIntent().getStringExtra("UserName");
        container = findViewById(R.id.notice);

        layoutInflater = LayoutInflater.from(this);

        Connection connection = new Connection();
        NoticeAPI noticeAPI = connection.getRetrofit().create(NoticeAPI.class);
        noticeAPI.getReview(userName).enqueue(new Callback<List<Notice>>(){
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                noticeList.clear();
                noticeList = response.body();
                if (noticeList != null) {
                    for (int i = 0; i < (noticeList.size()); i++) {
                        view = layoutInflater.inflate(R.layout.notice_layout, null, false);
                        TextView nameText = view.findViewById(R.id.ntask1name);
                        nameText.setText(noticeList.get(i).getMarket_name());
                        TextView dateText = view.findViewById(R.id.ntask1date);
                        dateText.setText(noticeList.get(i).getDate());
                        TextView readText = view.findViewById(R.id.ntask1con);
                        readText.setText(noticeList.get(i).getNotice_text());

                        container.addView(view);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "알림이 없습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }
}