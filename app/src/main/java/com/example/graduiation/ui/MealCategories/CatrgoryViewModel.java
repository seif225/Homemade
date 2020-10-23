package com.example.graduiation.ui.MealCategories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.DataRepo;
import com.example.graduiation.ui.Data.UserModel;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.example.graduiation.ui.LegacyData.UserParentModel;

import java.util.ArrayList;
import java.util.HashSet;

public class CatrgoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserParentModel>> usersLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FoodModel>> foodModelMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private HashSet<String> cookIds = new HashSet<String>();
    private String category;
    private MutableLiveData<ArrayList<UserParentModel>> kitchenLiveData = new MutableLiveData<>();
    private static final DataRepo repo =DataRepo.getInstance();
    private MutableLiveData<ArrayList<UserModel>> userLiveData = new MutableLiveData<>();

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

    @Deprecated
    public MutableLiveData<ArrayList<UserParentModel>> getFirstChunkOfKitchens(int n, String category) {
        firebaseQueryHelper.getFirstChucnkOfData(n, kitchenLiveData, category);
        return kitchenLiveData;
    }

    @Deprecated
    public void addNewKitchens(long lastDate, String category, int n) {
        firebaseQueryHelper.getMoreKitchens(lastDate, kitchenLiveData, category, n);
    }

    public MutableLiveData<ArrayList<UserModel>> getUsersInCategory(String  category , int page , Context c) {
        repo.getUsersInCategory(category,page,c,userLiveData);
        return userLiveData;
    }

    ;

}
