package com.example.graduiation.ui.Meal;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;

import java.util.HashMap;

public class MealViewModel extends ViewModel {

    private FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<HashMap<String,Object>> rateMapMutableLifeData = new MutableLiveData<>();
    public void addNewRating(String userId, String cookId, String mealId, float rating) {
        repo.addNewRating(userId,cookId,mealId,rating);
    }

    public MutableLiveData<HashMap<String,Object>> getMapOfRatings(String cookId, String id) {
        repo.getMapOfRatings(cookId,id,rateMapMutableLifeData);
        return rateMapMutableLifeData;
    }

    public void addToCart(Context onClickListener, String uid, FoodModel model) {
        repo.addItemToCart(onClickListener , uid , model);
    }
}
