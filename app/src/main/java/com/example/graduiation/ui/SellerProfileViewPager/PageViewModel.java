package com.example.graduiation.ui.SellerProfileViewPager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.LegacyData.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    private MutableLiveData<String> mString = new MutableLiveData<>();
    private MutableLiveData<List<FoodModel>> mList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Integer>> mIndex = new MutableLiveData<>();

    public void setmIndex(MutableLiveData<ArrayList<Integer>> mIndex) {
        this.mIndex = mIndex;
    }

    public void setmList(List<FoodModel> mList) {
        this.mList.setValue(mList);
    }

    public void setmString(MutableLiveData<String> mString) {
        this.mString = mString;
    }

    public MutableLiveData<List<FoodModel>> getmList() {
        return mList;
    }

    public MutableLiveData<ArrayList<Integer>> getmIndex() {
        return mIndex;
    }

    public MutableLiveData<String> getmString() {
        return mString;
    }
}
