package com.example.graduiation.ui.MealCategories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CatrgoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserParentModel>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FoodModel>> foodModelMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();
    private ArrayList<String> cookIds = new ArrayList<>();
    private static final String TAG = "CatrgoryViewModel";

    public MutableLiveData<ArrayList<FoodModel>> getFoodModelMutableLiveData() {
        firebaseQueryHelper.getListOfFoodAndUsers(foodModelMutableLiveData, cookIds);
        return foodModelMutableLiveData;
    }



    public MutableLiveData<ArrayList<UserParentModel>> getUsersLiveData() {
        Log.e(TAG, "getUsersLiveData: " + cookIds.size());
        if (cookIds.size() > 0) Log.e(TAG, "getUsersLiveData: " + cookIds.get(0));
        firebaseQueryHelper.getUsersData(usersLiveData, cookIds);
        return usersLiveData;
    }
}
