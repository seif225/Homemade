package com.example.graduiation.ui.addMeal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.graduiation.ui.Data.DataRepo;
import com.example.graduiation.ui.Data.MealModel;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.example.graduiation.ui.WorkManagers.UploadMealWorkManager;
import com.google.gson.Gson;

public class AddMealViewModel extends ViewModel {

    FirebaseQueryHelperRepository firebaseQueryHelper = FirebaseQueryHelperRepository.getInstance();
    private DataRepo repo = DataRepo.getInstance();
    private static final String TAG = "AddMealViewModel";

    @Deprecated
    void uploadFoodData(FoodModel model, Context context, Uri photo, ProgressDialog pd) {
        firebaseQueryHelper.uploadFoodDataToFirebase(model, context, photo, pd);
    }

    void uploadFoodDataWork(FoodModel model, Context context, Uri photo) {
      //  firebaseQueryHelper.uploadFoodDataToFirebase(model, context, photo);
        model.setThumbnail(photo.toString());
        Gson gson = new Gson();
        String json = gson.toJson(model);

        Data data = new Data.Builder()
                .putString("json" , json)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadMealWorkManager.class)
                .setInputData(data)
                .build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);

    }

    public void uploadMeal(Context context,MealModel model) {
        model.setUserId(repo.getUserId(context));
        Log.e(TAG, "uploadMeal: " + model.getUserId() + "\n mohm" );
        repo.addMeal(repo.getToken(context),model);
    }


}
