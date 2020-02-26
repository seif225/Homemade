package com.example.graduiation.ui.StoryDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.MealCategories.FoodItemRecyclerViewAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoryDetailsActivity2 extends AppCompatActivity {

    private static final String TAG = "StoryDetailsActivity2";

    @BindView(R.id.ring)
    CircleImageView ring;
    @BindView(R.id.profile_image_details)
    CircleImageView profileImageDetails;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_category_story_detailed)
    TextView tvCategoryStoryDetailed;
    @BindView(R.id.story_details_recycler)
    RecyclerView storyDetailsRecycler;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvBio)
    TextView tvBio;
    @BindView(R.id.tvFollowers)
    TextView tvFollowers;
    @BindView(R.id.tvMeals)
    TextView tvMeals;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.mainConstraint)
    ConstraintLayout mainConstraint;
    private StoryDetailsViewModel viewModel;
    private RecyclerView recyclerView;
    private FoodItemRecyclerViewAdapter foodAdapter;
    private TextView textView;
    private CircleImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details2);


        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profilePicture = findViewById(R.id.profile_image_details);
        recyclerView = findViewById(R.id.story_details_recycler);
        textView = findViewById(R.id.tv_category_story_detailed);
        //collapsingToolbar.setTitleEnabled(false);

        String uid = getIntent().getStringExtra("uid");
        String category = getIntent().getStringExtra("category");

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mainConstraint.setAlpha(1.0f - Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });


        Log.e(TAG, "onCreate: " + uid + " " + category);

        if (uid != null && category != null) {
            viewModel = ViewModelProviders.of(this).get(StoryDetailsViewModel.class);
            viewModel.setCategoryAndId(uid, category);
            viewModel.getUserParentModelMutableLiveData().observe(this, new Observer<UserParentModel>() {
                @Override
                public void onChanged(UserParentModel userParentModel) {
                    Log.e(TAG, "onChanged: " + userParentModel.getName());
                    if (userParentModel.getImage() != null) {
                        Picasso.get().load(userParentModel.getImage()).networkPolicy(NetworkPolicy.OFFLINE)
                                .into((ImageView) findViewById(R.id.backdrop), new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(userParentModel.getImage()).into((ImageView) findViewById(R.id.backdrop));
                                    }
                                });

                        Picasso.get().load(userParentModel.getImage()).networkPolicy(NetworkPolicy.OFFLINE)
                                .into(profilePicture, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(userParentModel.getImage()).into(profilePicture);
                                    }
                                });


                        toolbar.setTitle(userParentModel.getName());
                        tvUserName.setText(userParentModel.getName() + "");
                        if(userParentModel.getBio()!=null)tvBio.setText(userParentModel.getBio() + "");
                        else tvBio.setVisibility(View.GONE);
                        textView.setText(userParentModel.getName() + "'s " + category + " menu");


                    }

                }
            });

            viewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<FoodModel>>() {
                @Override
                public void onChanged(ArrayList<FoodModel> foodModels) {

                    Log.e(TAG, "onChanged: " + foodModels.get(0).getTitle());
                    foodAdapter = new FoodItemRecyclerViewAdapter(foodModels, getBaseContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext()
                            , RecyclerView.VERTICAL,
                            false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(foodAdapter);
                }
            });

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
