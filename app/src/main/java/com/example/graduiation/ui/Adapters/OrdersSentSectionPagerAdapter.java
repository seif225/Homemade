package com.example.graduiation.ui.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

public class OrdersSentSectionPagerAdapter  extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.current_orders, R.string.history};
    private final Context mContext;
    private ArrayList<OrderModel>orderModels;

    public OrdersSentSectionPagerAdapter(ArrayList<OrderModel> orderModels, Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.orderModels=orderModels;
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        /*if(position==0){
            return new CurrentSentOrdersFragment(orderModels);


        }
        else {

            return new HistorySentOrders();
        }*/
        return null;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}