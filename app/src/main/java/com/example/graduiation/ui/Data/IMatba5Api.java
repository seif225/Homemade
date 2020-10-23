package com.example.graduiation.ui.Data;

import com.example.graduiation.ui.LegacyData.PaymentModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMatba5Api {

    //endpoint for adding meals
    @POST("addMeal")
    Observable<MealModel> addMeal(@HeaderMap HashMap<String,String> token, @Body MealModel mealModel);
   //endpoint for getting all users in a particualr category that you provide as parameter
    @GET("getUsersInCategory")
    Observable<ArrayList<UserModel>> getUsersInCategory(@Query("category") String category , @Query("page") int page, @HeaderMap HashMap<String, String> token);
    //get all meals for a particular user providing his id
    @GET("getMealsByUserId")
    Observable<ArrayList<MealModel>> getMealsByUserId(@Query("userId") String userId , @HeaderMap HashMap<String, String> token);
   //get user by id
    @GET("getUserById")
    Observable<UserModel> getUserById(@Query("userId") String userId, @HeaderMap HashMap<String, String> token );
    //get user with array list of his meals
    @GET("getUserWithMeals")
    Observable<UserModel> getUserWithMealsById(@Query("userId") String userId ,@HeaderMap HashMap<String, String> token) ;
    //get only one meal with its id
    @GET("getOneMealById")
    Observable<MealModel> getOneMealWithId(@Query("mealId") String mealId ,@HeaderMap HashMap<String, String> token);
}
