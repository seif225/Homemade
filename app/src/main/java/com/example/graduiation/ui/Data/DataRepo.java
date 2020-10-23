package com.example.graduiation.ui.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DataRepo {

    private static DataRepo repo;
    private static IMatba5Api client = Matba5ApiClient.getApi();
    private static final String TAG = "DataRepo";

    public static DataRepo getInstance() {
        if (repo == null) repo = new DataRepo();
        return repo;
    }

    public String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "userFile", Context.MODE_PRIVATE);
        return sharedPref.getString("userToken", "");
    }

    ;

    public String getUserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "userFile", Context.MODE_PRIVATE);
        return sharedPref.getString("userId", "empty user Id");
    }

    ;

    public void addMeal(String token, MealModel meal) {
        HashMap<String, String> tokenHash = new HashMap();
        tokenHash.put("Authorization", "Bearer " + token);
        Observable<MealModel> observable = client.addMeal(tokenHash, meal).subscribeOn(Schedulers.io());
        Observer<MealModel> observer = new Observer<MealModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MealModel mealModel) {
                Log.e(TAG, "onNext: " + mealModel.getId());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    public HashMap<String, String> getAuth(Context c) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("Authorization", "Bearer " + getToken(c));
        return hm;
    }

    public void getUsersInCategory(String category, int page, Context c, MutableLiveData<ArrayList<UserModel>> userLiveData){
        Observable<ArrayList<UserModel>> observable = client.getUsersInCategory(category,page,getAuth(c)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ArrayList<UserModel>> observer = new Observer<ArrayList<UserModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<UserModel> userModels) {
                for (UserModel user:userModels) {
                    Log.e(TAG, "onNext: " + user.getName() );
                }
                userLiveData.setValue(userModels);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage() );
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);


    };

}
