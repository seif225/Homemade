package com.example.graduiation.ui.Meal;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import flepsik.github.com.progress_ring.ProgressRingView;


public class MealFragment extends AppCompatActivity {



    @BindView(R.id.remove_product_button)
    Button removeProductButton;
    @BindView(R.id.edit_product)
    Button editProduct;
    @BindView(R.id.product_describtion_tv)
    TextView productDescribtionTv;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.product_price_tv)
    TextView productPriceTv;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.product_category_tv)
    TextView productCategoryTv;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.like_image_button_in_product_activity)
    ShineButton likeImageButtonInProductActivity;
    @BindView(R.id.profile_picture_in_product_activity)
    CircleImageView profilePictureInProductActivity;
    @BindView(R.id.user_name_in_product_activity)
    TextView userNameInProductActivity;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.product_code_tv)
    TextView productCodeTv;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.progressRing)
    ProgressRingView progressRing;
    @BindView(R.id.accumlated_rate)
    TextView accumlatedRate;
    @BindView(R.id.ratings_counter)
    TextView ratingsCounter;
    @BindView(R.id.rate)
    RatingBar rate;
    private MealViewModel mViewModel;
    private FoodModel foodModel;
    private static final String TAG = "MealFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_fragment);
        ButterKnife.bind(this);

        String foodModelString = getIntent().getExtras().getString("meal");
        FoodModel model = new Gson().fromJson(foodModelString, FoodModel.class);
        Log.e(TAG, "onCreate: " + model.getTitle());


        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this);
        adapter.addItem(model);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);

        //set indicator animation by using SliderLayout.IndicatorAnimations.
        // :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        userNameInProductActivity.setText(model.getCookId());
        productCodeTv.setText(model.getId());
        productCategoryTv.setText(model.getCategory());
        productDescribtionTv.setText(model.getDescribtion());
        productPriceTv.setText(model.getPrice());
        rate.setRating(1.4f);


    }



}
