package com.example.graduiation.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelper;

public class LoginViewModel extends ViewModel {

    FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();
    public void authenticate(String mail , String password , Context context , ProgressDialog progressDialog){
        firebaseQueryHelper.SignIn(mail,password,context,progressDialog);
    }

}
