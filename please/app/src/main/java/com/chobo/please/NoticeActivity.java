package com.chobo.please;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class NoticeActivity extends Activity {
    LinkedList<String>nameList = new LinkedList<>();        //서버에서 이름 받아와서 넣기
    LinkedList<String>dateList = new LinkedList<>();        //서버에서 날짜 받아와서 넣기
    LinkedList<String>readList = new LinkedList<>();        //서버에서 정보 받아와서 넣기

    LayoutInflater layoutInflater;
    LinearLayout container;
    View view;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        context = this;

        /*예시*/
        nameList.add("씨홍스"); dateList.add("2023-01-08"); readList.add("내 관심업체에 새로운 리뷰가 작성되었어요!");
        nameList.add("미식반점"); dateList.add("2023-01-08"); readList.add("내 관심업체가 오픈했어요!");
        nameList.add("땅땅치킨"); dateList.add("2023-01-08"); readList.add("내 관심업체가 잘하고있어요!");
        nameList.add("헬스짐"); dateList.add("2023-01-08"); readList.add("내 관심업체에 새로운 리뷰가 작성되었어요!");

        container = findViewById(R.id.notice);

        layoutInflater = LayoutInflater.from(this);

        for(int i = 0; i<(nameList.size()); i++){
            view = layoutInflater.inflate(R.layout.notice_layout,null, false);
            TextView nameText = view.findViewById(R.id.ntask1name);
            nameText.setText(nameList.get(i));
            TextView dateText = view.findViewById(R.id.ntask1date);
            dateText.setText(dateList.get(i));
            TextView readText = view.findViewById(R.id.ntask1con);
            readText.setText(readList.get(i));

            container.addView(view);
        }
    }
}
