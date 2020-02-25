package com.example.graduiation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.MealCategories.CategoriesFragment;

import net.colindodd.gradientlayout.GradientLinearLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;

    private static final String TAG = "HomeFragment";
    private CardView cooked;
    private CardView semiCooked;
    private CardView pastry;
    private CardView dessert;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        cooked = root.findViewById(R.id.well_cooked_layout);

        cooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("cooked");
            }
        });


        semiCooked = root.findViewById(R.id.semi_card);

        semiCooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("semi-cooked");
            }
        });


        pastry = root.findViewById(R.id.pastry_card);

        pastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("pastry");
            }
        });


        dessert = root.findViewById(R.id.dessert_card);

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("dessert");
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void changeFragment(String s) {
        Log.e(TAG, "changeFragment: Clicked ! ");
        // Create new fragment and transaction
        Fragment newFragment = new CategoriesFragment(s);
        // consider using Java coding conventions (upper first char class names!!!)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}