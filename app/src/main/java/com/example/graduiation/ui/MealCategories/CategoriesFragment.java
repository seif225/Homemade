package com.example.graduiation.ui.MealCategories;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.FoodItemRecyclerViewAdapter;
import com.example.graduiation.ui.Adapters.RecyclerViewAdapter;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;

public class CategoriesFragment extends AppCompatActivity {

    private static final String TAG = "CategoriesFragment";
    private RecyclerView recyclerView;
    private CatrgoryViewModel viewModel;
    private RecyclerView foodRecyclerView;
    private RecyclerViewAdapter adapter;
    private FoodItemRecyclerViewAdapter foodAdapter;
    private String category;
    private TextView categoryTv;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_categories);
        Intent i = getIntent();
        category = i.getStringExtra("category");
        Log.e(TAG, "onCreate: current category is " + category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      
        recyclerView = findViewById(R.id.recyclerView);
        foodRecyclerView = findViewById(R.id.food_recyclerView);
        categoryTv = findViewById(R.id.tv_category);
        categoryTv.setText(category);
        viewModel = ViewModelProviders.of(this).get(CatrgoryViewModel.class);
        viewModel.getFoodModelMutableLiveData(category).observe(this, new Observer<ArrayList<FoodModel>>() {
            @Override
            public void onChanged(ArrayList<FoodModel> foodModels) {
                if (foodModels != null) {
                    //Log.e(TAG, "onChanged:fooooood "+ foodModels.get(0).getTitle() );
                    foodAdapter = new FoodItemRecyclerViewAdapter(foodModels, CategoriesFragment.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CategoriesFragment.this
                            , RecyclerView.VERTICAL,
                            false);
                    foodRecyclerView.setLayoutManager(linearLayoutManager);
                    foodRecyclerView.setAdapter(foodAdapter);
                    //foodRecyclerView.setVerticalScrollBarEnabled(true);
                    recyclerView.setNestedScrollingEnabled(false);
                    viewModel.getUsersLiveData().observe(CategoriesFragment.this, new Observer<ArrayList<UserParentModel>>() {
                        @Override
                        public void onChanged(ArrayList<UserParentModel> userParentModels) {
                            // Log.e(TAG, "onChanged: "+userParentModels.get(0).getName()+"" );
                            if (userParentModels.size() > 0) {
                                adapter = new
                                        RecyclerViewAdapter(userParentModels, CategoriesFragment.this, category);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()
                                        , RecyclerView.HORIZONTAL,
                                        false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setHorizontalScrollBarEnabled(true);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }
}
