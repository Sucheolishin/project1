package com.chobo.please;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button btn;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);
        radioGroup.check(R.id.task1rect);
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

    public void clickBtn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}