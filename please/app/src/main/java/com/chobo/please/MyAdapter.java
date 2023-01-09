package com.chobo.please;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyAdapter extends FragmentStateAdapter {
    public int mCount;
    List<Review> reviewList;

    public MyAdapter(FragmentActivity fa, int count, List<Review> rList){
        super(fa);
        mCount = count;
        reviewList = rList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        return new Fragment_reviews(index, reviewList);
    }

    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }
}