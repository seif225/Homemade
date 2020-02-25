package com.example.graduiation.ui.MealCategories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.ArrayList;

import butterknife.BindView;


public class CategoriesFragment extends Fragment {
    private static final String TAG = "CategoriesFragment";
    RecyclerView recyclerView;
    private CatrgoryViewModel viewModel;
    RecyclerView foodRecyclerView;
    private RecyclerViewAdapter adapter;
    private FoodItemRecyclerViewAdapter foodAdapter;
    private String category;

    public CategoriesFragment(String s) {
        category=s;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        foodRecyclerView = view.findViewById(R.id.food_recyclerView);

        viewModel = ViewModelProviders.of(this).get(CatrgoryViewModel.class);
        viewModel.getFoodModelMutableLiveData(category).observe(this, new Observer<ArrayList<FoodModel>>() {
            @Override
            public void onChanged(ArrayList<FoodModel> foodModels) {



                if (foodModels != null) {

                    Log.e(TAG, "onChanged:fooooood "+ foodModels.get(0).getTitle() );
                    foodAdapter = new FoodItemRecyclerViewAdapter(foodModels, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                            , RecyclerView.VERTICAL,
                            false);
                    foodRecyclerView.setLayoutManager(linearLayoutManager);
                    foodRecyclerView.setAdapter(foodAdapter);



                    viewModel.getUsersLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<UserParentModel>>() {
                        @Override
                        public void onChanged(ArrayList<UserParentModel> userParentModels) {
                           // Log.e(TAG, "onChanged: "+userParentModels.get(0).getName()+"" );
                            adapter = new RecyclerViewAdapter(userParentModels, getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                                    , RecyclerView.HORIZONTAL,
                                    false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }

            }
        });


        return view;
    }


}
