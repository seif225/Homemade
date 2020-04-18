package com.example.graduiation.ui.register;

import android.app.ProgressDialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;

public class RegisterViewModel extends ViewModel {

    private FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<String> mutableLiveDataOfUserState=new MutableLiveData<>();
    @Deprecated
    public void Authenticate(String mail, String password, String password2, ProgressDialog pd, String phone, String name) throws IllegalArgumentException {
        firebaseQueryHelper.SignUp(mail, password, password2, pd, phone, name);
    }

    public MutableLiveData<String> getUserRegisterationState(String id , UserParentModel user ) {
        FirebaseQueryHelperRepository.getInstance().addUserToDataBase(id , user , mutableLiveDataOfUserState);
        return mutableLiveDataOfUserState;
    }
}
