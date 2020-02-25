package com.example.graduiation.ui.MealCategories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CatrgoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserParentModel>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FoodModel>> foodModelMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();
    private HashSet<String> cookIds = new HashSet<String>();
    String category;
    private static final String TAG = "CatrgoryViewModel";

    public MutableLiveData<ArrayList<FoodModel>> getFoodModelMutableLiveData(String category) {
        if (this.category == null) this.category = category;
        firebaseQueryHelper.getListOfFoodAndUsers(foodModelMutableLiveData, cookIds, this.category);
        return foodModelMutableLiveData;
    }


    public MutableLiveData<ArrayList<UserParentModel>> getUsersLiveData() {
        Log.e(TAG, "getUsersLiveData: " + cookIds.size());
        // if (cookIds.size() > 0) Log.e(TAG, "getUsersLiveData: " + cookIds.get(0));
        firebaseQueryHelper.getUsersData(usersLiveData, cookIds);
        return usersLiveData;
    }
}
