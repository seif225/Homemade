package com.example.graduiation.ui.Meal;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    ScaleRatingBar rate;
    @BindView(R.id.ratingBar_minimal)
    RatingBar ratingBarMinimal;
    @BindView(R.id.number_of_ratings)
    TextView numberOfRatings;

    private MealViewModel mViewModel;
    FoodModel model;
    String userName, userPicture, userId;
    private static final String TAG = "MealFragment";
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_fragment);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(MealFragment.this).get(MealViewModel.class);
        initializeFields();

        rate.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                /* Log.e(TAG, "onRatingChange: " + userId , model.getCookId() );*/
                mViewModel.addNewRating(FirebaseAuth.getInstance().getUid(), model.getCookId(), model.getId(), rating);
            }
        });


       /* mViewModel.getMapOfRatings(model.getCookId(),model.getId()).observe(this, new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> stringObjectHashMap) {
                Log.e(TAG, "onChanged: HASH"+ stringObjectHashMap.size());
            }
        });
*/


        if (model.getRateMap() != null) {
            Log.e(TAG, "onCreate: " + model.getRateMap().size());
            double accRate = 0;
            Iterator<HashMap.Entry<String, Object>> it = model.getRateMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> pair = it.next();
                if (pair.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                    rate.setRating(Float.parseFloat(pair.getValue() + ""));
                }

                accRate += (Double) pair.getValue();
            }
            float ratePercent = (float) (accRate / model.getRateMap().size()) / 5 * 100;
            progressRing.setProgress(ratePercent / 100);
            accumlatedRate.setText(ratePercent + "%");
            ratingBarMinimal.setRating((float) (accRate / model.getRateMap().size()));
            handleProgressColor((float) accRate / model.getRateMap().size());
            numberOfRatings.setText(model.getRateMap().size() + " people rated this product");


        } else {
            Log.e(TAG, "onCreate:: NO HASH");
        }
    }

    private void initializeFields() {
        String foodModelString = getIntent().getExtras().getString("meal");
        model = new Gson().fromJson(foodModelString, FoodModel.class);

        userName = getIntent().getExtras().getString("userName");
        userPicture = getIntent().getExtras().getString("userPicture");
        userId = getIntent().getExtras().getString("userId");


        rate = findViewById(R.id.rate);
        Log.e(TAG, "onCreate: " + userName);
        Log.e(TAG, "onCreate: " + userPicture);
        Log.e(TAG, "onCreate: " + userId);


        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this);
        adapter.addItem(model);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        floatingActionButton.setScaleType(ImageView.ScaleType.CENTER);
        //set indicator animation by using SliderLayout.IndicatorAnimations.
        // :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.parseColor("#11CFC5"));
        sliderView.setIndicatorUnselectedColor(Color.WHITE);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        userNameInProductActivity.setText(userName);
        TextView mealTitle = findViewById(R.id.meal_title);
        mealTitle.setText(model.getTitle());
        if (userPicture != null)
            Picasso.get().load(userPicture).into(profilePictureInProductActivity);
        productCodeTv.setText(model.getId());
        productCategoryTv.setText(model.getCategory());
        productDescribtionTv.setText(model.getDescribtion());
        productPriceTv.setText((Double.parseDouble((model.getPrice())) + (Double.parseDouble(model.getPrice()) * 0.12)) + " EGP");
        progressRing.setAnimated(true);
        progressRing.setAnimationDuration(800);
        progressRing.setProgress(0.0f);
        rate.setRating(0f);

       /* rate.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                Log.e(TAG, "onRatingChanged: " + rating);
                progressRing.setProgress(rating / 5);
                accumlatedRate.setText(df2.format(((rating) / 5 * 100)) + "%");
                if (rating < 0.5) {
                    progressRing.setProgress(0.0f);
                    Log.e(TAG, "onRatingChanged: " + " its ZEROOOO !! ");
                }

                if (rating <= 1) {

                    progressRing.setProgressColor(Color.RED);

                } else if (rating <= 2) {
                    progressRing.setProgressColor(Color.parseColor("#ff8000"));

                } else if (rating <= 3) {
                    progressRing.setProgressColor(Color.parseColor("#FFEB3B"));


                } else if (rating <= 4) {
                    progressRing.setProgressColor(Color.parseColor("#83b735"));


                } else if (rating > 4) {

                    progressRing.setProgressColor(Color.GREEN);

                }

            }

        });*/

    }

    public void handleProgressColor(float rating) {

        if (rating < 0.5) {
            progressRing.setProgress(0.0f);
            Log.e(TAG, "onRatingChanged: " + " its ZEROOOO !! ");
        }

        if (rating <= 1) {

            progressRing.setProgressColor(Color.RED);

        } else if (rating <= 2) {
            progressRing.setProgressColor(Color.parseColor("#ff8000"));

        } else if (rating <= 3) {
            progressRing.setProgressColor(Color.parseColor("#FFEB3B"));


        } else if (rating <= 4) {
            progressRing.setProgressColor(Color.parseColor("#83b735"));


        } else if (rating > 4) {

            progressRing.setProgressColor(Color.GREEN);

        }

    }

}
