package com.example.graduiation.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.MealCategories.CategoriesFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends Fragment {


   /* @BindView(R.id.pastryImg)
    ImageButton pastryImg;
    @BindView(R.id.semiCookedImg)
    ImageButton semiCookedImg;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.fullyCookedImg)
    ImageButton fullyCookedImg;
    @BindView(R.id.dessertsCookedImg)
    ImageButton dessertsCookedImg;
    @BindView(R.id.mainConstraint)
    ConstraintLayout mainConstraint;*/
    private HomeViewModel homeViewModel;
    ImageButton pastry ;
    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        pastry = root.findViewById(R.id.pastryImg);
        pastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /*@OnClick({R.id.pastryImg, R.id.semiCookedImg, R.id.fullyCookedImg, R.id.dessertsCookedImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pastryImg:
                changeFragment();
                break;
            case R.id.semiCookedImg:
                changeFragment();
                break;
            case R.id.fullyCookedImg:
                changeFragment();
                break;
            case R.id.dessertsCookedImg:
                changeFragment();
                break;
        }
    }
*/
    public void changeFragment() {
        Log.e(TAG, "changeFragment: Clicked ! ");
        // Create new fragment and transaction
        Fragment newFragment = new CategoriesFragment();
        // consider using Java coding conventions (upper first char class names!!!)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}