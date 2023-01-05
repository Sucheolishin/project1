package com.chobo.please;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    private Button mSubmitButton;

    private EditText mID, mPW,mPW_C, mName;

    private static String userID, userPW,userPW_C, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 값 가져오기
       mID = (EditText)findViewById(R.id.Id_Ex);
       mPW = (EditText)findViewById(R.id.pass_Ex);
       mPW_C = (EditText)findViewById(R.id.pass_Ex_check);
       mName = (EditText)findViewById(R.id.name_Ex);

       mSubmitButton = (Button)findViewById(R.id.register_Btn);

        // 회원 가입 버튼이 눌렸을때
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 입력된 정보를 string으로 가져오기
                userID = mID.getText().toString();
                userPW = mPW.getText().toString();
                userPW_C = mPW_C.getText().toString();
                userName = mName.getText().toString();

                if(!userPW.equals(userPW_C)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                User mUser = new User();
                mUser.setId(userID);
                mUser.setPassword(userPW);
                
                Connection connection = new Connection();
                UserAPI userAPI = connection.getRetrofit().create(UserAPI.class);

            userAPI.insertUser(mUser.getId(), mUser.getPassword(), userName)
                            .enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if(response.body()){
                                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class );
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });

            }
        });
    }
}
