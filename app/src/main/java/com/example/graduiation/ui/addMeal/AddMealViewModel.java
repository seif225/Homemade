package com.example.graduiation.ui.addMeal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;
import com.example.graduiation.ui.Data.FoodModel;

 public class AddMealViewModel extends ViewModel {

    private FirebaseQueryHelper firebaseQueryHelper = new FirebaseQueryHelper();

     void uploadFoodData(FoodModel model, Context context, Uri photo, ProgressDialog pd) {
        firebaseQueryHelper.uploadFoodDataToFirebase(model, context, photo, pd);
    }
}
