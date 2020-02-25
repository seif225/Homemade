package com.example.graduiation.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;
import com.example.graduiation.ui.Data.UserParentModel;

public class MainViewModel  extends ViewModel {

    private FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();
    private MutableLiveData<UserParentModel> userParentModel=new MutableLiveData<>();

    public MutableLiveData<UserParentModel> getUserParentModel(String id) {
        firebaseQueryHelper.addUserToMutableLiveData(id,userParentModel);
        return userParentModel;
    }
}
