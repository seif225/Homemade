package com.example.graduiation.ui.UserCart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.CartItemModel;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;

import java.util.List;

public class UserCartViewModel extends ViewModel {


    MutableLiveData<List<FoodModel>> listMutableLiveData = new MutableLiveData<>();
    FirebaseQueryHelperRepository firebaseQueryHelperRepository = FirebaseQueryHelperRepository.getInstance();
    public MutableLiveData <List<FoodModel>>getCartItems(String uid) {
        firebaseQueryHelperRepository.getCartItems(uid , listMutableLiveData);
        return listMutableLiveData;
    }
}
