package com.example.graduiation.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import java.util.List;

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
    private Button sortBtn;
    private static int  sortMethod = 0 ;
    AutoCompleteMealAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        autoCompleteTextView = root.findViewById(R.id.meal_auto_complete_search);
        sortBtn = root.findViewById(R.id.sort_btn);
        sortBtn.setBackgroundResource(R.drawable.sort_button_style);
        autoCompleteTextView.setBackgroundResource(R.drawable.sort_button_style);


        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

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
                            adapter = new AutoCompleteMealAdapter(getContext(), foodSearchModels , sortMethod);
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
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("AlertDialog");
        String[] items = {"sort by price Descending" , "Sort by price Ascending","sort by name A-Z","sort by name Z-A"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        sortMethod = 1;
                        adapter.setSortMethod(sortMethod);
                        dialog.cancel();;
                        break;
                    case 1:
                        sortMethod = 2;
                        adapter.setSortMethod(sortMethod);
                        dialog.cancel();
                        break;
                    case 2:
                        sortMethod = 3;
                        adapter.setSortMethod(sortMethod);
                        dialog.cancel();
                        break;
                    case 3:
                        sortMethod = 4;
                        adapter.setSortMethod(sortMethod);
                        dialog.cancel();
                        break;


                }

            }
        });


        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }



}