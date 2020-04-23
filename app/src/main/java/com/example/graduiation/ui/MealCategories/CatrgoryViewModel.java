package com.example.graduiation.ui.MealCategories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;
import java.util.HashSet;

public class CatrgoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserParentModel>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FoodModel>> foodModelMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private HashSet<String> cookIds = new HashSet<String>();
    private String category;
    private MutableLiveData<ArrayList<UserParentModel>> kitchenLiveData = new MutableLiveData<>();


    private static final String TAG = "CatrgoryViewModel";

    @Deprecated
    public MutableLiveData<ArrayList<FoodModel>> getFoodModelMutableLiveData(String category) {
        if (this.category == null) this.category = category;
        firebaseQueryHelper.getListOfFoodAndUsers(foodModelMutableLiveData, cookIds, this.category);
        return foodModelMutableLiveData;
    }

    @Deprecated
    public MutableLiveData<ArrayList<UserParentModel>> getUsersLiveData() {
        Log.e(TAG, "getUsersLiveData: " + cookIds.size());
        // if (cookIds.size() > 0) Log.e(TAG, "getUsersLiveData: " + cookIds.get(0));
        firebaseQueryHelper.getUsersData(usersLiveData, cookIds);
        return usersLiveData;
    }

    public MutableLiveData<ArrayList<UserParentModel>> getFirstChunkOfKitchens(int n, String category) {
        firebaseQueryHelper.getFirstChucnkOfData(n, kitchenLiveData, category);
        return kitchenLiveData;
    }


    public void addNewKitchens(long lastDate ,String category ,int n) {
        firebaseQueryHelper.getMoreKitchens( lastDate,kitchenLiveData, category , n);
    }
}
