package com.example.graduiation.ui.MealCategories;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.KitchensRecyclerAdapter;
import com.example.graduiation.ui.Adapters.RecyclerViewAdapter;
import com.example.graduiation.ui.Data.UserModel;
import com.example.graduiation.ui.LegacyData.UserParentModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;

import io.reactivex.processors.PublishProcessor;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private ProgressBar progressBar;
    private NestedScrollView scroll;
    private Handler handler;
    private TextView tv;
    private SearchView searchView;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_categories, container, false);


        category = getArguments().getString("category");
        searchView = root.findViewById(R.id.search_view);
        searchView.setBackgroundResource(R.drawable.searchview_rounded);

        Log.e(TAG, "onCreate: current category is " + category);
        recyclerView = root.findViewById(R.id.recyclerView);
        foodRecyclerView = root.findViewById(R.id.food_recyclerView);
        categoryTv = root.findViewById(R.id.tv_category);
        categoryTv.setText(category);
        scroll = root.findViewById(R.id.nested_scroll_view_categories_fragment);
        handler = new Handler();
        tv = root.findViewById(R.id.tv_highly_rated_kitchens_dummy);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                InputMethodManager im = ((InputMethodManager) root.getContext().getSystemService(INPUT_METHOD_SERVICE));
                im.showSoftInput(searchView, 0);
            }
        });
        progressBar = root.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        switch (category) {
            case "semi-cooked":
                doubleBounce.setColor(Color.parseColor("#FF641A"));
                break;
            case "Pastry":
                doubleBounce.setColor(Color.parseColor("#FF416C"));
                break;
            case "dessert":
                doubleBounce.setColor(Color.parseColor("#8A52E9"));
                break;
        }


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
        adapter = new RecyclerViewAdapter(getActivity(), category);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
        foodRecyclerView.setAdapter(foodAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // foodAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getUsersInCategory(category,0,getActivity()).observe(getActivity(), new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                if(userModels!=null){
                    for (UserModel user:userModels) {
                        Log.e(TAG, "onChanged: " + user );
                    }
                    progressBar.animate().setDuration(200).alpha(0.05f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                }
                            })
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    scroll.setVisibility(View.GONE);
                                    //adapter.setData(userModels);
                                    adapter.notifyDataSetChanged();
                                    foodAdapter.setData(userModels);
                                    foodAdapter.notifyDataSetChanged();
                                    Animation fadeOut = new AlphaAnimation(0, 1);
                                    fadeOut.setInterpolator(new AccelerateInterpolator());
                                    fadeOut.setStartOffset(700);
                                    fadeOut.setDuration(250);
                                    scroll.setVisibility(View.VISIBLE);
                                    scroll.setAnimation(fadeOut);
                                    //listOfKitchens = userParentModels;
                                    progressBar.setVisibility(View.GONE);
                                    if(userModels.size()>0)Toast.makeText(getActivity(),userModels.get(0).getName() , Toast.LENGTH_SHORT).show();
                                }
                            })
                    ;

                }


            }
        });



    }


}