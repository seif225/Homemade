package com.example.graduiation.ui.OrdersReceived;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.OrdersSectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersRecievedPlaceHolder extends Fragment {


    OrdersSectionPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
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
         sectionsPagerAdapter = new OrdersSectionPagerAdapter(getContext(), getActivity().getSupportFragmentManager());
         viewPager = view.findViewById(R.id.current_orders_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.current_orders_tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "onDestroy: \n \n \n" );
        Object object = sectionsPagerAdapter.getItem(0);
        sectionsPagerAdapter.destroyItem(viewPager,0,object);
        Object object2 = sectionsPagerAdapter.getItem(1);
        sectionsPagerAdapter.destroyItem(viewPager,1,object2 );
        viewPager.setAdapter(null);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }
}
