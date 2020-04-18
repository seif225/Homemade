package com.example.graduiation.ui.SentOrders;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.OrdersSentSectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SendOrdersPlaceHolder extends Fragment {

    private SendOrdersPlaceHolderViewModel mViewModel;

    public static SendOrdersPlaceHolder newInstance() {
        return new SendOrdersPlaceHolder();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.send_orders_place_holder_fragment, container, false);

        mViewModel = ViewModelProviders.of(this).get(SendOrdersPlaceHolderViewModel.class);
        OrdersSentSectionPagerAdapter sectionsPagerAdapter = new OrdersSentSectionPagerAdapter(getContext(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.current_orders_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.current_orders_tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
