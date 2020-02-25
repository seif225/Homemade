package com.example.graduiation.ui.StoryDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.graduiation.R;

public class StoryDetailsActivity extends AppCompatActivity {


    private StoryDetailsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        String uid = getIntent().getStringExtra("uid");
        String category = getIntent().getStringExtra("category");

        if(uid!=null && category!=null){
            viewModel = ViewModelProviders.of(this).get(StoryDetailsViewModel.class);
            viewModel.setCategoryAndId(uid,category);

        }

    }
}
