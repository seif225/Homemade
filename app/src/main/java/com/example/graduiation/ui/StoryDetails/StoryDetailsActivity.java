package com.example.graduiation.ui.StoryDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;

public class StoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "StoryDetailsActivity";
    private StoryDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        getSupportActionBar().hide();
        String uid = getIntent().getStringExtra("uid");
        String category = getIntent().getStringExtra("category");
        Log.e(TAG, "onCreate: " + uid + " " + category);

        if (uid != null && category != null) {
            viewModel = ViewModelProviders.of(this).get(StoryDetailsViewModel.class);
            viewModel.setCategoryAndId(uid, category);
            viewModel.getUserParentModelMutableLiveData().observe(this, new Observer<UserParentModel>() {
                @Override
                public void onChanged(UserParentModel userParentModel) {
                    Log.e(TAG, "onChanged: " + userParentModel.getName());
                }
            });

            viewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<FoodModel>>() {
                @Override
                public void onChanged(ArrayList<FoodModel> foodModels) {

                    Log.e(TAG, "onChanged: " + foodModels.get(0).getTitle());

                }
            });

        }

    }
}
