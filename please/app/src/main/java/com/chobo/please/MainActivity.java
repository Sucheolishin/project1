package com.chobo.please;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button loginbtn;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        final View drawerView = (View) findViewById(R.id.drawer);

        Button openbtn = findViewById(R.id.profileimag);
        Button closebtn = findViewById(R.id.list5);
        loginbtn = findViewById(R.id.loginnotice);
        radioGroup = findViewById(R.id.radio_group);
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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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
    }
}