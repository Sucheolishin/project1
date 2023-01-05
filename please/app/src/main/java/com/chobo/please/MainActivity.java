package com.chobo.please;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button loginbtn;
    RadioGroup radioGroup;
    MapView mapView;
    RelativeLayout bottombar;
    Button uparr;
    Button downarr;
    String userName;
    Boolean isLog = false;

    User user;

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

        /*user.setId(savedInstanceState.getString("userID","0"));
        user.setPassword(savedInstanceState.getString("userPassword","0"));
*/
        /*if(!(user.getId().equals("0") && user.getPassword().equals("0"))){
           isLog = true;
        }
        else{
            isLog = false;
        }
        if(isLog){
            try{
                Connection connection = new Connection();
                UserAPI userAPI = connection.getRetrofit().create(UserAPI.class);
                Call<String> call = userAPI.names(user);
                userName = new MainActivity.RegisterTask().execute(call).get();
            }catch(Exception e){

            }
            loginbtn.setText(userName);
        }
        else{
            loginbtn.setText("로그인 필요");
        }*/
        mapView= new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        final View drawerView = (View) findViewById(R.id.drawer);

        Button openbtn = findViewById(R.id.profileimag);
        Button closebtn = findViewById(R.id.list5);

        loginbtn = findViewById(R.id.loginnotice);
        radioGroup = findViewById(R.id.radio_group);
        downarr = findViewById(R.id.darrow);
        uparr = findViewById(R.id.uarrow);
        bottombar = findViewById(R.id.bottom);
        radioGroup.check(R.id.task1rect);

        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLog){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
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
            }
        });
        downarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottombar.setVisibility(View.GONE);
                downarr.setVisibility(View.GONE);
                uparr.setVisibility(View.VISIBLE);
            }
        });
        uparr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uparr.setVisibility(View.GONE);
                downarr.setVisibility(View.VISIBLE);
                bottombar.setVisibility(View.VISIBLE);
            }
        });
    }
 /*   private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }*/
}
