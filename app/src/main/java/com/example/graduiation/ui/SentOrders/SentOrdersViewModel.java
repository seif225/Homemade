package com.example.graduiation.ui.SentOrders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.OrderModel;

import java.util.ArrayList;

public class SentOrdersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<OrderModel>> mutableLiveDataForOrdeRModelList = new MutableLiveData<>();
    private FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();

    public MutableLiveData<ArrayList<OrderModel>> getMutableLiveDataForOrdeRModelList(String id) {
        repo.getListOfCurrentOrders(id , mutableLiveDataForOrdeRModelList);
        return mutableLiveDataForOrdeRModelList;
    }
}