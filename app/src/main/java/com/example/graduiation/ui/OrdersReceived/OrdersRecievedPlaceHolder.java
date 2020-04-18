package com.example.graduiation.ui.OrdersReceived;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.OrdersSectionPagerAdapter;
import com.example.graduiation.ui.Adapters.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersRecievedPlaceHolder extends Fragment {

    public OrdersRecievedPlaceHolder() {
        // Required empty public constructor
    }

    private static final String TAG = "OrdersRecievedPlaceHold";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_recieved_place_holder, container, false);
        Log.e(TAG, "onCreateView: "+getActivity().getSupportFragmentManager() );
        OrdersSectionPagerAdapter sectionsPagerAdapter = new OrdersSectionPagerAdapter(getContext(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }
}
