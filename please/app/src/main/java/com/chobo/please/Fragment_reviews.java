package com.chobo.please;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;

public class Fragment_reviews extends Fragment {
    int now_index;
    LinkedList<String> nameList = new LinkedList<>();        //서버에서 이름 받아와서 넣기
    LinkedList<String> countList = new LinkedList<>();        //서버에서 날짜 받아와서 넣기
    LinkedList<String> readList = new LinkedList<>();        //서버에서 정보 받아와서 넣기

    public Fragment_reviews(int index) {
        now_index = index;

        nameList.add("신수철"); countList.add("리뷰 3개"); readList.add("너무 맛있어요 내스타일!");
        nameList.add("신수철"); countList.add("리뷰 3개"); readList.add("항상 여기서 사먹어요 ");
        nameList.add("신수철"); countList.add("리뷰 3개"); readList.add("항상 여기서 사먹어요 !");
        nameList.add("김예진"); countList.add("리뷰 2개"); readList.add("내 근육 생성소");
        nameList.add("김예진"); countList.add("리뷰 2개"); readList.add("항상 여기서 사먹어요 !");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.reviews_layout, container, false);
        TextView nameText = rootView.findViewById(R.id.profilename2);
        nameText.setText(nameList.get(now_index));
        TextView countText = rootView.findViewById(R.id.reviews2);
        countText.setText(countList.get(now_index));
        TextView readText = rootView.findViewById(R.id.explain2);
        readText.setText(readList.get(now_index));
        return rootView;
    }

}
