package com.chobo.please;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends Activity {
    EditText mID, mPW;
    Button mBtnLogin, mBtnRegister;
    int isLogin = 0;    //아이디가 있으면 1반환
    private class RegisterTask extends AsyncTask<Call,Void,Integer> {
        protected Integer doInBackground(Call... calls) {
            try {
                Call<Integer> call = calls[0];
                Response<Integer> response=call.execute();
                return response.body();

            } catch (IOException e) {

            }
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //로그인 전 변수 불러오기
        mID = (EditText) findViewById(R.id.Id_Ex);
        mPW = (EditText) findViewById(R.id.pass_Ex);
        mBtnLogin = (Button) findViewById(R.id.login_Btn);
        mBtnRegister = (Button) findViewById(R.id.Reg);

        //로그인 버튼을 누르면
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = mID.getText().toString();
                String userPW = mPW.getText().toString();

                User mUser = new User();
                mUser.setId(userID);
                mUser.setPassword(userPW);
                Connection connection = new Connection();
                UserAPI userAPI = connection.getRetrofit().create(UserAPI.class);
                Call<Integer> call = userAPI.register(mUser);        //내가 집어넣을 정보
               try{
                    isLogin = new RegisterTask().execute(call).get();
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(isLogin != 0){
                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    String userid = mUser.getId();
                    String userPassword = mUser.getPassword();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    // 로그인 하면서 사용자 정보 넘기기
                    intent.putExtra("userID", userid);
                    intent.putExtra("userPassword", userPassword);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
