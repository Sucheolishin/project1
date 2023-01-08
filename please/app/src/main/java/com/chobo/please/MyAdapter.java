package com.chobo.please;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {
    public int mCount;

    public MyAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        return new Fragment_reviews(index);
    }

    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }
}
