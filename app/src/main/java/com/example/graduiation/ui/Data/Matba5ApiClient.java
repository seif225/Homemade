package com.example.graduiation.ui.Data;

import com.example.graduiation.ui.login.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Matba5ApiClient {
    private static Retrofit retrofitInstance;
    private static Retrofit getRetrofitInstance(){
        if(retrofitInstance==null){
            retrofitInstance=new Retrofit.Builder()
                    .baseUrl("https://mn-elmatba5-api.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static IMatba5Api getApi(){
        return getRetrofitInstance().create(IMatba5Api.class);
    }

}
