package com.example.graduiation.ui.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduiation.R;
import com.example.graduiation.ui.AcceptedOrders.AcceptedOrdersFragment;
import com.example.graduiation.ui.OrdersHanging.OrdersHangingFragment;
import com.example.graduiation.ui.OrdersReceived.OrdersRecievedPlaceHolder;
import com.example.graduiation.ui.SellerProfileViewPager.PlaceholderFragment;

public class OrdersSectionPagerAdapter  extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.hanging_orders, R.string.accepted_orders};
    private final Context mContext;

    public OrdersSectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
       if(position==0){
           return new OrdersHangingFragment();


       }
       else {

           return new AcceptedOrdersFragment();
       }

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