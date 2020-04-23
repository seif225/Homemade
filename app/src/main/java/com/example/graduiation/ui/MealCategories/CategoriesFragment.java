package com.example.graduiation.ui.MealCategories;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.FoodItemRecyclerViewAdapter;
import com.example.graduiation.ui.Adapters.KitchensRecyclerAdapter;
import com.example.graduiation.ui.Adapters.RecyclerViewAdapter;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private static final String TAG = "CategoriesFragment";
    private RecyclerView recyclerView;
    private CatrgoryViewModel viewModel;
    private RecyclerView foodRecyclerView;
    private RecyclerViewAdapter adapter;
    private KitchensRecyclerAdapter foodAdapter;
    private String category;
    private TextView categoryTv;
    private int listSize = 0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);


        category =getArguments().getString("category");
        Log.e(TAG, "onCreate: current category is " + category);
        recyclerView = root.findViewById(R.id.recyclerView);
        foodRecyclerView = root.findViewById(R.id.food_recyclerView);
        categoryTv = root.findViewById(R.id.tv_category);
        categoryTv.setText(category);
        viewModel = ViewModelProviders.of(this).get(CatrgoryViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                , RecyclerView.VERTICAL,
                false);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.setAdapter(foodAdapter);
        foodRecyclerView.setNestedScrollingEnabled(false);
        foodAdapter = new KitchensRecyclerAdapter(category);
        adapter = new
                RecyclerViewAdapter( getActivity(), category);
         linearLayoutManager = new LinearLayoutManager(getActivity()
                , RecyclerView.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
        foodRecyclerView.setAdapter(foodAdapter);


        viewModel.getFirstChunkOfKitchens(3,category).observe(this, new Observer<ArrayList<UserParentModel>>() {
            @Override
            public void onChanged(ArrayList<UserParentModel> userParentModels) {

                listSize = userParentModels.size();
                if (userParentModels.size() > 0) {
                    adapter.setData(userParentModels);
                    adapter.notifyDataSetChanged();
                    foodAdapter.setData(userParentModels);
                    foodAdapter.notifyDataSetChanged();
                }

            }
        });


        return root;
    }

}
