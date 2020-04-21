package com.example.graduiation.ui.SentOrders;

import android.os.Bundle;

import com.example.graduiation.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.ui.Adapters.SentOrdersSectionsPagerAdapter;

public class CurrentSentOrdersFragment extends Fragment {
    private static final String TAG = "SentOrdersFragment";
    SentOrdersSectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.activity_sent_orders_fragment, container, false);


        Log.e(TAG, "onCreateView: " + getActivity());
        Log.e(TAG, "onCreateView: " + getActivity().getSupportFragmentManager());


         sectionsPagerAdapter = new SentOrdersSectionsPagerAdapter(getActivity()
                , getActivity().getSupportFragmentManager() ,"test");
         viewPager = root.findViewById(R.id.view_pager);
         viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        PageViewModel viewModel = ViewModelProviders.of(this).get(PageViewModel.class);




        return root;

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Object object = sectionsPagerAdapter.getItem(0);
        sectionsPagerAdapter.destroyItem(viewPager,0,object);
        Object object2 = sectionsPagerAdapter.getItem(1);
        sectionsPagerAdapter.destroyItem(viewPager,1,object2 );
        viewPager.setAdapter(null);


        Log.e(TAG, "onDestroy: " );
    }
}