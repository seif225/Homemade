package com.example.graduiation.ui.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;


public class ProfileViewModel extends ViewModel {
    FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();

    private MutableLiveData<UserParentModel> userParentModel = new MutableLiveData<>();

    public MutableLiveData<UserParentModel> getUserParentModel(String id) {
        firebaseQueryHelper.addUserToMutableLiveData(id, userParentModel);
        return userParentModel;
    }
}
