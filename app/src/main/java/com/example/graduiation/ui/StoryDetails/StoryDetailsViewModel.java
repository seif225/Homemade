package com.example.graduiation.ui.StoryDetails;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.DataRepo;
import com.example.graduiation.ui.Data.MealModel;
import com.example.graduiation.ui.Data.UserModel;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.example.graduiation.ui.LegacyData.UserParentModel;

import java.util.ArrayList;

public class StoryDetailsViewModel extends ViewModel {

    //Legacy Variables
    private String uid, category;
    FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private MutableLiveData<UserParentModel> userParentModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<FoodModel>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableFlag = new MutableLiveData<>();
    private MutableLiveData<Integer> numberOfFollowers = new MutableLiveData<>();
    //updated variables
    private MutableLiveData<ArrayList<MealModel>> listOfMeals = new MutableLiveData<>();
    private static final DataRepo repo = DataRepo.getInstance();

    @Deprecated
    public void setCategoryAndId(String uid, String category) {
        this.uid = uid;
        this.category = category;


    }

    @Deprecated
    public MutableLiveData<ArrayList<FoodModel>> getListMutableLiveData() {
        FirebaseQueryHelperRepository.getListOfFoodForParticularCateegory(listMutableLiveData, uid, category);
        return listMutableLiveData;
    }

    @Deprecated
    public MutableLiveData<UserParentModel> getUserParentModelMutableLiveData() {
        firebaseQueryHelper.getSingleUserData(userParentModelMutableLiveData, uid);
        return userParentModelMutableLiveData;
    }

    @Deprecated
    public MutableLiveData<Boolean> isFollowed(String myId, String userId) {
        firebaseQueryHelper.isFollowed(myId, userId, mutableFlag);
        return mutableFlag;
    }

    @Deprecated
    public void follow(String myId, String userId, String name, String token) {
        firebaseQueryHelper.follow(myId, userId, name, token);
    }

    @Deprecated
    public void unFollow(String myId, String userId) {
        firebaseQueryHelper.unFollow(myId, userId);
    }


    @Deprecated
    public MutableLiveData<Integer> getFollowersCount(String id) {
        firebaseQueryHelper.getNumberOfFollowers(numberOfFollowers, id);

        return numberOfFollowers;
    }

    @Deprecated
    public void pushNotification(String finalUserName, String token) {
        firebaseQueryHelper.sendFollowNotification(finalUserName, token);
    }

    public MutableLiveData<ArrayList<MealModel>> getListOfMealsByUserId(Context c , String id ){
        repo.getAllMealsById(c,id , listOfMeals);
        return listOfMeals;
    };

    public MutableLiveData<UserModel> getUserById(Context c, String uid) {
         MutableLiveData<UserModel>  userModelMutableLiveData = new MutableLiveData<>();
        repo.getUserById(c,uid,userModelMutableLiveData);
        return userModelMutableLiveData;
    }

    public MutableLiveData<UserModel> getUserWithMealsById(Context c, String uid) {
        MutableLiveData<UserModel>  userModelMutableLiveData = new MutableLiveData<>();
        repo.getUserWithMealsById(c,uid,userModelMutableLiveData);
        return userModelMutableLiveData;
    }
}
