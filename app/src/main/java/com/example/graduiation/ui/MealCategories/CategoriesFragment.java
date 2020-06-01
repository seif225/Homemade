package com.example.graduiation.ui.MealCategories;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ProgressBar;
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
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.processors.PublishProcessor;

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
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    private NestedScrollView scroll;
    private Handler handler;
    private TextView tv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_categories, container, false);


        category = getArguments().getString("category");
        Log.e(TAG, "onCreate: current category is " + category);
        recyclerView = root.findViewById(R.id.recyclerView);
        foodRecyclerView = root.findViewById(R.id.food_recyclerView);
        categoryTv = root.findViewById(R.id.tv_category);
        categoryTv.setText(category);
        scroll = root.findViewById(R.id.nested_scroll_view_categories_fragment);
        handler = new Handler();
        tv = root.findViewById(R.id.tv_highly_rated_kitchens_dummy);

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
                    exThread th = new exThread(1.25, userParentModels);
                    th.start();
                   /* adapter.setData(userParentModels);
                    adapter.notifyDataSetChanged();
                    foodAdapter.setData(userParentModels);
                    foodAdapter.notifyDataSetChanged();
                    listOfKitchens = userParentModels;*/

                }
            }
        });


    }

    class exThread extends Thread {
        double sec;
        ArrayList<UserParentModel> userParentModels;

        exThread(double sec, ArrayList<UserParentModel> userParentModels) {
            this.sec = sec * 1000;
            this.userParentModels = userParentModels;
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep((int) sec);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /*AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        progressBar.startAnimation(anim);*/

                        progressBar.animate().setDuration(400).alpha(0.05f)
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
                                        adapter.setData(userParentModels);
                                        adapter.notifyDataSetChanged();
                                        foodAdapter.setData(userParentModels);
                                        foodAdapter.notifyDataSetChanged();
                                        Animation fadeOut = new AlphaAnimation(0, 1);
                                        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                                        fadeOut.setStartOffset(1000);
                                        fadeOut.setDuration(400);
                                        scroll.setVisibility(View.VISIBLE);
                                        scroll.setAnimation(fadeOut);
                                        listOfKitchens = userParentModels;
                                        progressBar.setVisibility(View.GONE);
                                    }
                                })
                        ;

                        // progressBar.setVisibility(View.GONE);

                       /* adapter.setData(userParentModels);
                        adapter.notifyDataSetChanged();
                        foodAdapter.setData(userParentModels);
                        foodAdapter.notifyDataSetChanged();
                        listOfKitchens = userParentModels;
                        categoryTv.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
*/
                       /* progressBar.startAnimation(anim);
                        progressBar.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        progressBar.setVisibility(View.GONE);


                                    }
                                });*/


                    }
                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}