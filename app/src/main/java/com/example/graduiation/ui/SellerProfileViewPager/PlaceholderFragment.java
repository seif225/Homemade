package com.example.graduiation.ui.SellerProfileViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Adapters.FoodItemRecyclerViewAdapter;
import com.example.graduiation.ui.Data.UserParentModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel viewModel;

    public static PlaceholderFragment newInstance(int index, String category, ArrayList<FoodModel> foodModels, UserParentModel userParentModel) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putSerializable("ARRAYLIST", foodModels);
        bundle.putInt(ARG_SECTION_NUMBER, index);

        bundle.putString("userName",userParentModel.getName());
        bundle.putString("userPicture",userParentModel.getImage());
        bundle.putString("userId",userParentModel.getId());

        fragment.setArguments(bundle);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        viewModel.setmList((ArrayList<FoodModel>) getArguments().getSerializable("ARRAYLIST"));

    }


    RecyclerView recycler;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        recycler = root.findViewById(R.id.fragment_main_recyclerView);


                FoodItemRecyclerViewAdapter foodAdapter = new FoodItemRecyclerViewAdapter((ArrayList<FoodModel>) getArguments().getSerializable("ARRAYLIST")
                        , getContext() , getArguments().getString("userName"),getArguments().getString("userPicture") , getArguments().getString("userId"));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                        , RecyclerView.VERTICAL,
                        false);

                recycler.setLayoutManager(linearLayoutManager);

                recycler.setAdapter(foodAdapter);




        return root;
    }
}