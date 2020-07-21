package com.example.graduiation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.AutoCompleteMealAdapter;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.FoodSearchModel;
import com.example.graduiation.ui.MealCategories.CategoriesFragment;

import net.colindodd.gradientlayout.GradientLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    private static final String TAG = "HomeFragment";
    private CardView cooked;
    private CardView semiCooked;
    private CardView pastry;
    private CardView dessert;
    private NavController navController ;
    private String category;
    private AutoCompleteTextView autoCompleteTextView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        cooked = root.findViewById(R.id.well_cooked_layout);
        navController = Navigation.findNavController(container);
        cooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "well-cooked";
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                navController.navigate(R.id.action_nav_home_to_categoriesFragment,bundle);

            }
        });

        autoCompleteTextView = root.findViewById(R.id.meal_auto_complete_search);
            autoCompleteTextView.setBackgroundResource(R.drawable.searchview_rounded);
               homeViewModel.getListOfFoodModels().observe(getActivity(), new Observer<ArrayList<FoodSearchModel>>() {
                   @Override
                   public void onChanged(ArrayList<FoodSearchModel> foodSearchModels) {
                       for (FoodSearchModel model: foodSearchModels) {
                           Log.e(TAG, "getListOfFoodModels " + model.getMealName() );
                           autoCompleteTextView = root.findViewById(R.id.meal_auto_complete_search);
                           AutoCompleteMealAdapter adapter = new AutoCompleteMealAdapter(getContext(), foodSearchModels);
                           autoCompleteTextView.setAdapter(adapter);

                       }
                   }
               });


       /* autoCompleteTextView = root.findViewById(R.id.meal_auto_complete_search);
        AutoCompleteMealAdapter adapter = new AutoCompleteMealAdapter(this, countryList);
        autoCompleteTextView.setAdapter(adapter);*/


        semiCooked = root.findViewById(R.id.semi_card);

        semiCooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = "semi-cooked";
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                navController.navigate(R.id.action_nav_home_to_categoriesFragment,bundle);
            }
        });


        pastry = root.findViewById(R.id.pastry_card);

        pastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = "Pastry";
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                navController.navigate(R.id.action_nav_home_to_categoriesFragment,bundle);
            }
        });


        dessert = root.findViewById(R.id.dessert_card);

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = "dessert";
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                navController.navigate(R.id.action_nav_home_to_categoriesFragment,bundle);
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }




}