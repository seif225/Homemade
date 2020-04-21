package com.example.graduiation.ui.Adapters;

import  android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduiation.R;
import com.example.graduiation.ui.SentOrders.PlaceholderFragment;
import com.example.graduiation.ui.SentOrders.SentOrdersHistoryFragment;


public class SentOrdersSectionsPagerAdapter extends FragmentPagerAdapter {


    private static final String TAG = "SentOrdersSectionsPager";
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.current_orders, R.string.history};
    private final Context mContext;
    private String s ;
    public SentOrdersSectionsPagerAdapter(Context context, FragmentManager fm, String s) {
        super(fm);
        mContext = context;
        this.s = s ;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position==0)
        return PlaceholderFragment.newInstance(position + 1,s);
        else return new SentOrdersHistoryFragment();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        Log.e(TAG, "destroyItem: "  + position);
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