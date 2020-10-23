package com.example.graduiation.ui.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Emitter;
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
    private DataRepo(){}
    public static DataRepo getInstance() {
        if (repo == null) repo = new DataRepo();
        return repo;
    }

    public String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        return sharedPref.getString("userToken", "");
    }

    public String getUserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        return sharedPref.getString("userId", "empty user Id");
    }

    @SuppressLint("CheckResult")
    public void addMeal(String token, MealModel meal) {
        HashMap<String, String> tokenHash = new HashMap();
        tokenHash.put("Authorization", "Bearer " + token);
        Observable<MealModel> observable = client.addMeal(tokenHash, meal).subscribeOn(Schedulers.io());
        observable.subscribe((o)->Log.e(TAG, "onNext: " + o.getId()),
                             (e)->Log.e(TAG, "onError: " + e.getMessage()));
    }

    public HashMap<String, String> getAuth(Context c) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("Authorization", "Bearer " + getToken(c));
        return hm;
    }

    @SuppressLint("CheckResult")
    public void getUsersInCategory(String category, int page, Context c, MutableLiveData<ArrayList<UserModel>> userLiveData){
        Observable<ArrayList<UserModel>> observable = client.getUsersInCategory(category,page,getAuth(c)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe((o)-> userLiveData.setValue(o)
                            ,(e) -> Log.e(TAG, "getUsersInCategory: "+e ) );
    }

    @SuppressLint("CheckResult")
    public void getAllMealsById(Context c , String id , MutableLiveData<ArrayList<MealModel>> list){
        Observable<ArrayList<MealModel>> observable = client.getMealsByUserId(id,getAuth(c)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe((o)-> list.setValue(o),
        (e)-> Log.e(TAG, "getAllMealsById: "+e ));

    }

    @SuppressLint("CheckResult")
    public void getUserById(Context c, String uid, MutableLiveData<UserModel> userModelMutableLiveData) {
        Observable<UserModel> observable = client.getUserById(uid,getAuth(c)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe((o)->userModelMutableLiveData.setValue(o) , (e)->Log.e(TAG, "onError: " + e));
    }

    @SuppressLint("CheckResult")
    public void getUserWithMealsById(Context c, String uid, MutableLiveData<UserModel> userModelMutableLiveData){
        Observable<UserModel> observable = client.getUserWithMealsById(uid,getAuth(c)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe((o)->userModelMutableLiveData.setValue(o) , (e)->Log.e(TAG, "onError: " + e));
    }

}
