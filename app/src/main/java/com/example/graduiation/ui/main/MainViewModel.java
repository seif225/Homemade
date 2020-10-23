package com.example.graduiation.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.UserParentModel;

public class MainViewModel extends ViewModel {

    private FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<UserParentModel> userParentModel = new MutableLiveData<>();

    public MutableLiveData<UserParentModel> getUserParentModel(String id) {
        firebaseQueryHelper.addUserToMutableLiveData(id, userParentModel);
        return userParentModel;
    }
}
