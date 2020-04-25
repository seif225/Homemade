package com.example.graduiation.ui.MealCategories;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
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
    private View root;
    private NestedScrollView parentLayout;
    private ArrayList<UserParentModel> listOfKitchens = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_categories, container, false);


        category = getArguments().getString("category");
        Log.e(TAG, "onCreate: current category is " + category);
        recyclerView = root.findViewById(R.id.recyclerView);
        foodRecyclerView = root.findViewById(R.id.food_recyclerView);
        categoryTv = root.findViewById(R.id.tv_category);
        categoryTv.setText(category);
        parentLayout = root.findViewById(R.id.nested_scroll_view_categories_fragment);
        viewModel = ViewModelProviders.of(this).get(CatrgoryViewModel.class);
        linearLayoutManager = new LinearLayoutManager(getActivity()
                , RecyclerView.VERTICAL,
                false);
        linearLayoutManager2 = new LinearLayoutManager(getActivity()
                , RecyclerView.HORIZONTAL,
                false);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.setAdapter(foodAdapter);
        foodRecyclerView.setNestedScrollingEnabled(false);
        foodAdapter = new KitchensRecyclerAdapter(category);
        adapter = new
                RecyclerViewAdapter(getActivity(), category);


        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
        foodRecyclerView.setAdapter(foodAdapter);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getFirstChunkOfKitchens(6, category).observe(this, new Observer<ArrayList<UserParentModel>>() {
            @Override
            public void onChanged(ArrayList<UserParentModel> userParentModels) {
                listSize = userParentModels.size();
                if (userParentModels.size() > 0) {
                    adapter.setData(userParentModels);
                    adapter.notifyDataSetChanged();
                    foodAdapter.setData(userParentModels);
                    foodAdapter.notifyDataSetChanged();
                    listOfKitchens = userParentModels;

                }
            }
        });


        //TODO : trigger pagination by reaching the last item in the  recyclerView
        foodRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e(TAG, "onScrollChange: ");
            }

        });

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e(TAG, "onScrollChange : Story ");
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                int id = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if(id>=listOfKitchens.size()-1)

                {

                    //viewModel.addNewKitchens(listOfKitchens.get(listOfKitchens.size() - 1).getRegistrationTime(), category, 3);

                }

            }



        });

    }
}