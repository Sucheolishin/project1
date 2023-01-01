package com.chobo.please;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Connection {
    private Retrofit retrofit;

    public Connection(){
        initializeRefrofit();
    }
    public void initializeRefrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://211.219.61.130:8080/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}