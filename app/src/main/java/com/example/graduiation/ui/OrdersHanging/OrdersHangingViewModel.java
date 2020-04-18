package com.example.graduiation.ui.OrdersHanging;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class OrdersHangingViewModel extends ViewModel {


    private MutableLiveData<ArrayList<OrderModel>> listOfReceivedOrders;
    private final FirebaseQueryHelperRepository repository= FirebaseQueryHelperRepository.getInstance();
    public OrdersHangingViewModel() {
        listOfReceivedOrders = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<OrderModel>> getListOfReceivedOrders(String id) {
        repository.getReceivedOrder(listOfReceivedOrders,id);
        return listOfReceivedOrders;
    }
}