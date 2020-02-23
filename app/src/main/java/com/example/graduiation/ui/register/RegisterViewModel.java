package com.example.graduiation.ui.register;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;

public class RegisterViewModel extends ViewModel {

    private FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();

    public void Authenticate(String mail, String password, String password2, ProgressDialog pd, String phone, String name) throws IllegalArgumentException {

        firebaseQueryHelper.SignUp(mail, password, password2, pd, phone, name);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        firebaseQueryHelper.getUploadUserDataDisposable().dispose();
    }
}
