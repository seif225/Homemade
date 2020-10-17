package com.example.graduiation.ui.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("createUser")
    public Call<UserModel>postUser(@Body UserModel user);
}
