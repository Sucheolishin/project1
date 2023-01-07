package com.chobo.please;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        TextView id_text = findViewById(R.id.id_text);
        id_text.setText(getIntent().getStringExtra("userID"));
        TextView name_text = findViewById(R.id.name_text);
        name_text.setText(getIntent().getStringExtra("userName"));
    }
}
