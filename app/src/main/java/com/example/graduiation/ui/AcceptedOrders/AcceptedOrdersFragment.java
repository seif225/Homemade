package com.example.graduiation.ui.AcceptedOrders;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;

public class AcceptedOrdersFragment extends Fragment {

    private AcceptedOrdersViewModel mViewModel;

    public static AcceptedOrdersFragment newInstance() {
        return new AcceptedOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.accepted_orders_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AcceptedOrdersViewModel.class);
        // TODO: Use the ViewModel
    }

}
