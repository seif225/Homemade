package com.example.graduiation.ui.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduiation.ui.LegacyData.FoodModel;
import com.example.graduiation.ui.LegacyData.UserParentModel;
import com.example.graduiation.ui.SellerProfileViewPager.PlaceholderFragment;

import java.util.ArrayList;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private String[] tabTitles;
    private final Context mContext;
    UserParentModel userParentModel;
    ArrayList<FoodModel> foodModels;


    public SectionsPagerAdapter(Context context, FragmentManager fm, String[] tabs, ArrayList<FoodModel> foodModels, UserParentModel userParentModel) {
        super(fm);
        tabTitles = tabs;
        mContext = context;
        this.foodModels = foodModels;
        this.userParentModel = userParentModel;
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

        return PlaceholderFragment.newInstance(position + 1, tabTitles[position], categoryModels , userParentModel);
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

