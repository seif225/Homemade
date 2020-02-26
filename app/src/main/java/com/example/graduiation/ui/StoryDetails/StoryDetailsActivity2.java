package com.example.graduiation.ui.StoryDetails;

import android.os.Bundle;

import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.MealCategories.FoodItemRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graduiation.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryDetailsActivity2 extends AppCompatActivity {

    private static final String TAG = "StoryDetailsActivity2";
    private StoryDetailsViewModel viewModel;
    private RecyclerView recyclerView;
    private FoodItemRecyclerViewAdapter foodAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.story_details_recycler);
        textView = findViewById(R.id.tv_category_story_detailed);
        String uid = getIntent().getStringExtra("uid");
        String category = getIntent().getStringExtra("category");


        Log.e(TAG, "onCreate: " + uid + " " + category);

        if (uid != null && category != null) {
            textView.setText(category);
            viewModel = ViewModelProviders.of(this).get(StoryDetailsViewModel.class);
            viewModel.setCategoryAndId(uid, category);
            viewModel.getUserParentModelMutableLiveData().observe(this, new Observer<UserParentModel>() {
                @Override
                public void onChanged(UserParentModel userParentModel) {
                    Log.e(TAG, "onChanged: " + userParentModel.getName());
                    if (userParentModel.getImage() != null) {
                        Picasso.get().load(userParentModel.getImage()).networkPolicy(NetworkPolicy.OFFLINE)
                                .into((ImageView) findViewById(R.id.backdrop), new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(userParentModel.getImage()).into((ImageView) findViewById(R.id.backdrop));
                            }
                        });
                        toolbar.setTitle(userParentModel.getName());
                        toolbar.setSubtitle(userParentModel.getName()+"'s " + category +" menu");
                        toolbar.setSubtitleTextColor(getTitleColor());

                    }

                }
            });

            viewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<FoodModel>>() {
                @Override
                public void onChanged(ArrayList<FoodModel> foodModels) {

                    Log.e(TAG, "onChanged: " + foodModels.get(0).getTitle());
                    foodAdapter = new FoodItemRecyclerViewAdapter(foodModels, getBaseContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()
                            , RecyclerView.VERTICAL,
                            false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(foodAdapter);
                }
            });

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
