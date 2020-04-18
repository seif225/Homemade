package com.example.graduiation.ui.SentOrders;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.CurrentOrdersRecyclerAddapter;
import com.example.graduiation.ui.Data.OrderModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CurrentSentOrdersFragment extends Fragment {

    private CurrentSentOrdersViewModel mViewModel;
    private RecyclerView currentOrdersRecyclerView;
    private CurrentOrdersRecyclerAddapter adapter;
    private static final String TAG = "CurrentSentOrdersFragme";

    public static CurrentSentOrdersFragment newInstance() {
        return new CurrentSentOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.current_sent_orders_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(CurrentSentOrdersViewModel.class);
        currentOrdersRecyclerView = view.findViewById(R.id.current_orders_recycler_view);
        mViewModel.getMutableLiveDataForOrdeRModelList(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {
                Log.e(TAG, "onChanged: " + orderModels.size());

                if (FirebaseAuth.getInstance().getUid() != null) {
                    currentOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter = new CurrentOrdersRecyclerAddapter(orderModels);
                    currentOrdersRecyclerView.setAdapter(adapter);
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
