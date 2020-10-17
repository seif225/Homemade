package com.example.graduiation.ui.login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static Retrofit retrofitInstance;
    private static Retrofit getRetrofitInstance(){
        if(retrofitInstance==null){
            retrofitInstance=new Retrofit.Builder()
                    .baseUrl("https://mn-elmatba5-api.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static  ApiInterface getAPIS(){
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
