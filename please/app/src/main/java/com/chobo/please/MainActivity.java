package com.chobo.please;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.ToString;
import retrofit2.Call;
import retrofit2.Callback;
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
    List<Post> postList = new LinkedList<>();
    List<MapPOIItem> markers = new LinkedList<>();
    List<Review> reviewList= new LinkedList<>();
    int pickPost = 0;
    String mapPick;

    //리뷰 넘기기 변수
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 0;       //개수 통신을 통해 변경
    
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
     private class ReviewTask extends AsyncTask<Call,Void, List<Review>> {
        protected List<Review> doInBackground(Call... calls) {
            try {
                Call<List<Review>> call = calls[0];
                Response<List<Review>> response=call.execute();
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



        Connection connection2 = new Connection();
        PostAPI postAPI2 = connection2.getRetrofit().create(PostAPI.class);
        postAPI2.getPostInfo(0)
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        postList.clear();
                        markers.clear();
                        postList = response.body();
                        if(postList != null){
                            //위 정보를 가지고 핀찍기
                            for(int i = 0; i < postList.size(); i++){
                                MapPOIItem mapPOIItem = new MapPOIItem();
                                mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord
                                        (postList.get(i).getLongitude(), postList.get(i).getLatitude()));
                                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                                mapPOIItem.setItemName(postList.get(i).getMarket_name());

                                markers.add(mapPOIItem);
                            }
                            mapView.addPOIItems(markers.toArray(new MapPOIItem[markers.size()]));
                            if(markers.size()>0) {
                                mapView.moveCamera(CameraUpdateFactory.newMapPointAndDiameter(markers.get(markers.size()-1).getMapPoint(), 500));
                            }
                            else{
                                Toast.makeText(MainActivity.this,"검색한 장소가 없습니다!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {

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
                    userAPI.getUserName(userId)
                            .enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.body() != null){
                                        User tmp = new User();
                                        tmp = response.body();
                                        userName = tmp.getName();
                                        login_btn.setTextSize(25);
                                        login_btn.setText(userName);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "바디없음 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "닉네임 불러오기 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                }catch(Exception e){

                }
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
                postList.clear();
                markers.clear();
                Call<List<Post>> call = postAPI.getPostInfo(category);
                postList = new PostTask().execute(call).get();
            }catch(Exception e){

            }
            if(postList != null){
                //위 정보를 가지고 핀찍기
                for(int i = 0; i < postList.size(); i++){
                    MapPOIItem mapPOIItem = new MapPOIItem();
                    mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord
                            (postList.get(i).getLongitude(), postList.get(i).getLatitude()));
                    mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    mapPOIItem.setItemName(postList.get(i).getMarket_name());

                    markers.add(mapPOIItem);
                }
                mapView.addPOIItems(markers.toArray(new MapPOIItem[markers.size()]));
                if(markers.size()>0) {
                    mapView.moveCamera(CameraUpdateFactory.newMapPointAndDiameter(markers.get(markers.size()-1).getMapPoint(), 500));
                }
                else{
                    Toast.makeText(MainActivity.this,"검색한 장소가 없습니다!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
                    @Override
                    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
                        //리뷰 불러오기
                        Connection connection = new Connection();
                        ReviewAPI reviewAPI = connection.getRetrofit().create(ReviewAPI.class);
                        Call<List<Review>> call = reviewAPI.getReview(mapPOIItem.getItemName());
                        try {
                            reviewList.clear();
                            reviewList = new ReviewTask().execute(call).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mapPick = mapPOIItem.getItemName();
                        String tmp = mapPOIItem.getItemName();
                        for(Post p : postList){
                            if(p.getMarket_name().matches(tmp)){
                                TextView shopName = findViewById(R.id.shopname);
                                shopName.setText(p.getMarket_name());
                                TextView openTime = findViewById(R.id.opentime);
                                openTime.setText(p.getOpen_time());
                                TextView shopExplain = findViewById(R.id.shopexplain);
                                shopExplain.setText(p.getDetails());
                            }
                        }

                        //리뷰
                        if(reviewList.size() != 0){
                            num_page = reviewList.size();
                            //뷰페이저 연결
                            mPager = findViewById(R.id.viewpager);
                            pagerAdapter = new MyAdapter(MainActivity.this, num_page, reviewList);
                            mPager.setAdapter(pagerAdapter);
                            mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                            mPager.setCurrentItem(num_page);        //천장정도의 이미지를 생성하여 사실상 무한으로 만들기
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
                        }
                    }
                    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {}
                    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {}
                    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {}
                });
                return false;
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
               intent.putExtra("userID",userId);
               intent.putExtra("userName", userName);
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
           }
          
        });

       notice.setOnClickListener(view ->{
           if(isLog){
               Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
               intent.putExtra("UserName", userName);
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
           }
       });

       review.setOnClickListener(view ->{
            if(isLog){
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
       });

       pick.setOnClickListener(view ->{
            if(isLog){
                Intent intent = new Intent(MainActivity.this, PickActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
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
               intent.putExtra("PostName", mapPick);
               intent.putExtra("UserName", userName);
               startActivity(intent);
           }
           else{
               Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
           }
       });
    }
}

