package com.example.graduiation.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.FoodSearchModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<FoodSearchModel>> allMealsMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public MutableLiveData<ArrayList<FoodSearchModel>> getListOfFoodModels(){
        repo.getListOfFoodSearchModels(allMealsMutableLiveData);
        return allMealsMutableLiveData;
    }


    public LiveData<String> getText() {
        return mText;
    }
}