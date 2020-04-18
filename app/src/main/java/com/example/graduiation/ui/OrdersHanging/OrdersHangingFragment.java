package com.example.graduiation.ui.OrdersHanging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.AcceptedOrders.AcceptedOrdersViewModel;
import com.example.graduiation.ui.Adapters.OrdersRecieivedRecyclerAdapter;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class OrdersHangingFragment extends Fragment {

    private OrdersHangingViewModel viewModel;
    private RecyclerView receiverOrdersRecyclerView;
    private static final String TAG = "OrdersReceivedFragment";
    private OrdersRecieivedRecyclerAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.orders_hanging_fragment, container, false);
        receiverOrdersRecyclerView = root.findViewById(R.id.received_orders_recycler_view);
        SharedPreferences  sp= getActivity().getSharedPreferences("userData",Context.MODE_PRIVATE);
        String userId = sp.getString("id","default_value");
        Log.e(TAG, "onCreateView: userID from shared preference " + userId );
        viewModel =
                ViewModelProviders.of(this).get(OrdersHangingViewModel.class);
        viewModel.getListOfReceivedOrders(userId).observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModel) {
                adapter = new OrdersRecieivedRecyclerAdapter(orderModel);
                receiverOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                receiverOrdersRecyclerView.setAdapter(adapter);
            }
        });

        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}