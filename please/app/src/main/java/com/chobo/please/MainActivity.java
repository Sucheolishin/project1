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
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

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
    String userId;
    Button write_review;
    Button my_page;
    Button notice;
    Button review;
    Button pick;
    Button logout;
    List<Post> postList;
    List<MapPOIItem> markers;
    int pickPost;

    //리뷰 넘기기 변수
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 5;       //개수 통신을 통해 변경

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

    private class PostTask extends AsyncTask<Call,Void, List<Post>> {
        protected List<Post> doInBackground(Call... calls) {
            try {
                Call<List<Post>> call = calls[0];
                Response<List<Post>> response=call.execute();
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
        logout = findViewById(R.id.list6);
        write_review = findViewById(R.id.shopimage);

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

        //통신해서 로그인 되었는지 확인
         if(getIntent().hasExtra("userID")){
            userId = getIntent().getStringExtra("userID");
            isLog = true;
           if(isLog){
               //통신해서 이름 불러오는 곳
                try{
                    Connection connection = new Connection();
                    UserAPI userAPI = connection.getRetrofit().create(UserAPI.class);
                    Call<String> call = userAPI.getUserName(userId);
                    userName = new MainActivity.RegisterTask().execute(call).get();
                }catch(Exception e){

                }
               login_btn.setTextSize(25);
               login_btn.setText(userName);
            }
            else{
                login_btn.setText("로그인 필요");
            }
        }

         //로그아웃 버튼
         if(isLog){
             logout.setVisibility(View.VISIBLE);
             findViewById(R.id.line6).setVisibility(View.VISIBLE);
         }
         else{
             logout.setVisibility(View.GONE);
             findViewById(R.id.line6).setVisibility(View.GONE);
         }

        /*메인코드에서 지도 연동하는 부분*/
        mapView= new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord( 37.55028006471174,127.07462588183662), true);        // 중심점 변경
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
                intent.putExtra("userID", userId);
                intent.putExtra("userName", userName);
            }
            finish();
            startActivity(intent);
        });


        //라디오 버튼 선택(카테고리)
        radio_group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            try{
                Connection connection = new Connection();
                PostAPI postAPI = connection.getRetrofit().create(PostAPI.class);
                int category;
                switch (checkedId){
                    case R.id.task1rect: category = 0; break;
                    case R.id.task2rect: category = 1; break;
                    case R.id.task3rect: category = 2; break;
                    case R.id.task4rect: category = 3; break;
                    case R.id.task5rect: category = 4; break;
                    default: category = 5; break;
                }
                markers.clear();
                Call<List<Post>> call = postAPI.getPostInfo(category);
                postList = new MainActivity.PostTask().execute(call).get();
            }catch(Exception e){

            }
        });

        if(postList != null){
            //위 정보를 가지고 핀찍기
            for(int i = 0; i < postList.size(); i++){
                markers.add(new MapPOIItem());
                markers.get(i).setItemName(postList.get(i).getMarket_name());
                markers.get(i).setTag(i);
                markers.get(i).setMapPoint(MapPoint.mapPointWithGeoCoord
                         (postList.get(i).getLatitude(), postList.get(i).getLongitude()));
                markers.get(i).setMarkerType(MapPOIItem.MarkerType.BluePin);
                markers.get(i).setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            }
            mapView.addPOIItems(markers.toArray(new MapPOIItem[markers.size()]));;
        }

        mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
            @Override
            public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
                pickPost = mapPOIItem.getTag();
                Post post = postList.get(pickPost);
                TextView shopName = findViewById(R.id.shopname);
                shopName.setText(post.getMarket_name());
                TextView openTime = findViewById(R.id.opentime);
                openTime.setText("영업 시간: " + post.getOpen_time());
                TextView shopExplain = findViewById(R.id.shopexplain);
                shopExplain.setText(post.getMarket_Info());
            }
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {}
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {}
            public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {}
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
               intent.putExtra("userID",userId);
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

       logout.setOnClickListener(view->{
           userName = null;
           userId = null;
           getIntent().removeExtra("userID");
           getIntent().removeExtra("userPassword");
           login_btn.setTextSize(40);
           login_btn.setText("로그인 필요");
           isLog = false;
           logout.setVisibility(View.GONE);
           findViewById(R.id.line6).setVisibility(View.GONE);
       });

       write_review.setOnClickListener(view -> {
           if(isLog){
               Intent intent = new Intent(MainActivity.this, WriteActivity.class);
               /*intent.putExtra("PostId", postList.get(pickPost).getMarket_id());*/
               intent.putExtra("PostName", postList.get(pickPost).getMarket_name());
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
               return;
           }
       });
    }
}

