package com.example.graduiation.ui.UserCart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.CartItemModel;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class UserCartViewModel extends ViewModel {


    private MutableLiveData<ArrayList<FoodModel>> listMutableLiveData = new MutableLiveData<>();

    private FirebaseQueryHelperRepository firebaseQueryHelperRepository = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();

    public MutableLiveData <ArrayList<FoodModel>>getCartItems(String uid) {
        firebaseQueryHelperRepository.getCartItems(uid , listMutableLiveData);
        return listMutableLiveData;
    }

    public MutableLiveData<Integer> getTotalPrice() {
        return totalPrice;
    }
}
