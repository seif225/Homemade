package com.example.graduiation.ui.SentOrders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentOrdersHistoryFragment extends Fragment {

    public SentOrdersHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sent_orders_history, container, false);
    }
}
