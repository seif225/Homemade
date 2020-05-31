package com.example.graduiation.ui.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;


public class ProfileViewModel extends ViewModel {
    private FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<Integer> numOfFollowers = new MutableLiveData<>();
    private MutableLiveData<Integer> numOfOrders = new MutableLiveData<>();
    private MutableLiveData<UserParentModel> userParentModel = new MutableLiveData<>();

    public MutableLiveData<UserParentModel> getUserParentModel(String id) {
        firebaseQueryHelper.addUserToMutableLiveData(id, userParentModel);
        return userParentModel;
    }

    public MutableLiveData<Integer> getNumOfFollowers(String id) {
        firebaseQueryHelper.getNumberOfFollowers(numOfFollowers , id);
        return numOfFollowers;
    }

    public MutableLiveData<Integer> getNumOfOrders(String id ) {
        firebaseQueryHelper.getNumberOfReceivedOrders(numOfOrders,id);
        return numOfOrders;
    }
}
