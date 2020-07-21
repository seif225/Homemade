package com.example.graduiation.ui.SentOrders;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class SentOrdersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<OrderModel>> mutableLiveDataForOrdeRModelList = new MutableLiveData<>();
    private FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();

    public MutableLiveData<ArrayList<OrderModel>> getMutableLiveDataForOrdeRModelList(String id) {
        repo.getListOfCurrentOrders(id , mutableLiveDataForOrdeRModelList);
        return mutableLiveDataForOrdeRModelList;
    }
}