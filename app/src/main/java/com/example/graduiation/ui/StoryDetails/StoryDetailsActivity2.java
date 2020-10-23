package com.example.graduiation.ui.StoryDetails;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.MealModel;
import com.example.graduiation.ui.Adapters.FoodItemRecyclerViewAdapter;
import com.example.graduiation.ui.Adapters.SectionsPagerAdapter;
import com.example.graduiation.ui.Data.UserModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;

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
    @BindView(R.id.follow_button)
    Button followButton;
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
        handleUi();
        String uid = getIntent().getStringExtra("uid");
        String category = getIntent().getStringExtra("category");


        Log.e(TAG, "onCreate: " + uid + " " + category);

        if (uid != null && category != null) {
            viewModel = ViewModelProviders.of(this).get(StoryDetailsViewModel.class);
            viewModel.getUserWithMealsById(this,uid).observe(this, new Observer<UserModel>() {
                @Override
                public void onChanged(UserModel userModel) {
                    Log.e(TAG, "onChanged: meals object" + userModel.getMeals() );
                    Log.e(TAG, "onChanged: userName" +userModel.getName() );
                    tvUserName.setText(userModel.getName()+"");
                    tvBio.setText(userModel.getBio()+"");
                    //TODO: i think the best way to do it is to make a network call for each category on its own.
                    HashSet<String> hashset = new HashSet<>();


                    for (int i = 0; i < userModel.getMeals().size(); i++) {
                        hashset.add(userModel.getMeals().get(i).getCategory());
                    }
                    Log.e(TAG, "" + hashset.size());
                    String[] tabsArray;
                    tabsArray = new String[hashset.size()];
                    int k = 0;
                    for (String s : hashset) {
                        tabsArray[k] = s;
                        k++;
                    }

                    for (int i = 1; i < tabsArray.length; i++) {
                        if (tabsArray[i].equals(category)) {
                            String temp;
                            temp = tabsArray[0];
                            tabsArray[0] = tabsArray[i];
                            tabsArray[i] = temp;
                            break;
                        }
                    }
                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                            StoryDetailsActivity2.this
                            , getSupportFragmentManager()
                            , tabsArray
                            , userModel.getMeals()
                            ,userModel
                    );
                    ViewPager viewPager = findViewById(R.id.current_orders_view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    TabLayout tabs = findViewById(R.id.current_orders_tabs);
                    tabs.setupWithViewPager(viewPager);

                }
            });



        }


    }

    private void handleUi() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profilePicture = findViewById(R.id.profile_image_details);

        textView = findViewById(R.id.tv_category_story_detailed);
        //collapsingToolbar.setTitleEnabled(false);



        String userName = getIntent().getStringExtra("userName");
        //token = getIntent().getStringExtra("token");
        String userNameFromSharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE).getString("name" , "folan");

        collapsingToolbar.setTitle(userName + "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mainConstraint.setAlpha(1.0f - Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void setUnfollowedState() {
        followButton.setBackgroundResource(R.drawable.unfollow_button_shape);
        followButton.setText("Unfollow");
        ((Button) followButton).setTextColor(Color.parseColor("#FF0000"));

    }

    private void setFollowedState() {


        followButton.setBackgroundResource(R.drawable.follow_button_shape);
        followButton.setText("Follow");
        ((Button) followButton).setTextColor(Color.parseColor("#3C8F00"));
    }
}
