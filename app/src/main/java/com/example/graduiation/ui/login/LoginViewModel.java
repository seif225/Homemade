package com.example.graduiation.ui.login;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;

public class LoginViewModel extends ViewModel {

    FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    public void authenticate(String mail , String password , Context context , ProgressDialog progressDialog){
        firebaseQueryHelper.SignIn(mail,password,context,progressDialog);
    }

    public void loginWithGoogle(String string){
        firebaseQueryHelper.loginWithGoogle(string);
    }

}
