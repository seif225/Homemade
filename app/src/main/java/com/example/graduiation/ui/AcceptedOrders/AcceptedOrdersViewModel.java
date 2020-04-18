package com.example.graduiation.ui.AcceptedOrders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class AcceptedOrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<ArrayList<OrderModel>> listOfAcceptedOrders = new MutableLiveData<>();
    private FirebaseQueryHelperRepository rep = FirebaseQueryHelperRepository.getInstance();

    public MutableLiveData<ArrayList<OrderModel>> getListOfAcceptedOrders(String id) {
        rep.getAcceptedOrders(id,listOfAcceptedOrders);
        return listOfAcceptedOrders;
    }
}
