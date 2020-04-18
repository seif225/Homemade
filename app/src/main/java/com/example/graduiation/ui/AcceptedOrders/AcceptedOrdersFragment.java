package com.example.graduiation.ui.AcceptedOrders;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.AcceptedOrdersRecyclerAdapter;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class AcceptedOrdersFragment extends Fragment {

    private AcceptedOrdersViewModel mViewModel;
    private RecyclerView acceptedOrdersRecyclerView;
    private AcceptedOrdersRecyclerAdapter adapter;
    public static AcceptedOrdersFragment newInstance() {
        return new AcceptedOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.accepted_orders_fragment, container, false);
        String id = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE).getString("id","");
        acceptedOrdersRecyclerView = view.findViewById(R.id.accepted_orders_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(AcceptedOrdersViewModel.class);

        mViewModel.getListOfAcceptedOrders(id).observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {

                acceptedOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new AcceptedOrdersRecyclerAdapter(orderModels);
                acceptedOrdersRecyclerView.setAdapter(adapter);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
