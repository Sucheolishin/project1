package com.chobo.please;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    RadioGroup radio_group;
    MapView mapView;
    RelativeLayout bottom_bar;
    Button up_arr;
    Button down_arr;
    String userName;
    Boolean isLog = false;
    Button my_page;
    Button notice;
    Button review;
    Button pick;

    //리뷰 넘기기 변수
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 5;       //개수 통신을 통해 변경

    User user = new User();

    private class RegisterTask extends AsyncTask<Call,Void, String> {
        protected String doInBackground(Call... calls) {
            try {
                Call<String> call = calls[0];
                Response<String> response=call.execute();
                return response.body();
            } catch (IOException e) {

            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼들 동기화
        login_btn = findViewById(R.id.loginnotice);
        radio_group = findViewById(R.id.radio_group);
        radio_group.check(R.id.task1rect);
        down_arr = findViewById(R.id.darrow);
        up_arr = findViewById(R.id.uarrow);
        bottom_bar = findViewById(R.id.bottom);
        my_page = findViewById(R.id.list1);
        notice = findViewById(R.id.list2);
        review = findViewById(R.id.list3);
        pick = findViewById(R.id.list4);

        //뷰페이저 연결
        mPager = findViewById(R.id.viewpager);
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager.setCurrentItem(1000);        //천장정도의 이미지를 생성하여 사실상 무한으로 만들기
        mPager.setOffscreenPageLimit(num_page);        //최대 리뷰 개수(해당도 통신을 통해 변경)

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0){
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });


        getIntent().putExtra("userID","tncjf789");      //임시
        getIntent().putExtra("userPassword","123456");  //임시
        //통신해서 로그인 되었는지 확인
         if(getIntent().hasExtra("userID") && getIntent().hasExtra("userPassword") ){
            user.setId(getIntent().getStringExtra("userID"));
            user.setPassword(getIntent().getStringExtra("userPassword"));
            isLog = true;
           if(isLog){
               //통신해서 이름 불러오는 곳
                /*try{
                    Connection connection = new Connection();
                    UserAPI userAPI = connection.getRetrofit().create(UserAPI.class);
                    Call<String> call = userAPI.names(user);
                    userName = new MainActivity.RegisterTask().execute(call).get();
                }catch(Exception e){

                }*/
               userName = "tncjf";  //임시
               login_btn.setTextSize(25);
               login_btn.setText(userName);
            }
            else{
                login_btn.setText("로그인 필요");
            }
        }

        /*메인코드에서 지도 연동하는 부분*/
        mapView= new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final View drawerView = (View) findViewById(R.id.drawer);

        Button open_btn = findViewById(R.id.profileimag);
        Button close_btn = findViewById(R.id.list5);

        //로그인 이벤트
        login_btn.setOnClickListener(view -> {
            Intent intent;
            if(!isLog){
                intent = new Intent(MainActivity.this, LoginActivity.class);
            }
            else{
                intent = new Intent(MainActivity.this, PageActivity.class);
                intent.putExtra("userID",user.getId());
                intent.putExtra("userName", userName);
            }
            startActivity(intent);
        });

        //라디오 버튼 선택
        radio_group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId){
                case R.id.task1rect:
                    break;
                case R.id.task2rect:
                    break;
                case R.id.task3rect:
                    break;
                case R.id.task4rect:
                    break;
                case R.id.task5rect:
                    break;
                case R.id.task6rect:
                    break;
            }
        });

        //하단 바 감추기 or 보이기
        down_arr.setOnClickListener(view -> {
            bottom_bar.setVisibility(View.GONE);
            down_arr.setVisibility(View.GONE);
            up_arr.setVisibility(View.VISIBLE);
        });
        up_arr.setOnClickListener(view -> {
            up_arr.setVisibility(View.GONE);
            down_arr.setVisibility(View.VISIBLE);
            bottom_bar.setVisibility(View.VISIBLE);
        });
        //옆 드로어 열기
        open_btn.setOnClickListener(view -> drawerLayout.openDrawer(drawerView));
        close_btn.setOnClickListener(view -> drawerLayout.closeDrawer(drawerView));

       my_page.setOnClickListener(view -> {
           if(isLog){
               Intent intent = new Intent(MainActivity.this, PageActivity.class);
               intent.putExtra("userID",user.getId());
               intent.putExtra("userName", userName);
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
               return;
           }
          
        });

       notice.setOnClickListener(view ->{
           if(isLog){
               Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
               return;
           }
       });

       review.setOnClickListener(view ->{
            if(isLog){
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
       });

       pick.setOnClickListener(view ->{
            if(isLog){
                Intent intent = new Intent(MainActivity.this, PickActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
       });
    }
}
