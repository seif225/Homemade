package com.example.graduiation.ui.SellerProfileViewPager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduiation.ui.Data.FoodModel;

import java.util.ArrayList;
import java.util.HashSet;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private String[] tabTitles;
    private final Context mContext;

    ArrayList<FoodModel> foodModels;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String[] tabs, ArrayList<FoodModel> foodModels) {
        super(fm);
        tabTitles = tabs;
        mContext = context;

        this.foodModels = foodModels;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        ArrayList<FoodModel> categoryModels = new ArrayList<>();
        for (FoodModel model : foodModels) {
            if (model.getCategory().equals(tabTitles[position])) {
                categoryModels.add(model);
            }
        }

        return PlaceholderFragment.newInstance(position + 1, tabTitles[position], categoryModels);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return tabTitles.length;
    }
}

