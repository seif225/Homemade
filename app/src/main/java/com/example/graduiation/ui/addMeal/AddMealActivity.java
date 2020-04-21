package com.example.graduiation.ui.addMeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddMealActivity extends AppCompatActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_mealName)
    EditText etMealName;
    @BindView(R.id.tv_bookPrice)
    TextView tvBookPrice;
    @BindView(R.id.et_bookPrice)
    EditText etBookPrice;
    @BindView(R.id.et_min)
    EditText etMin;
    @BindView(R.id.et_max)
    EditText etMax;
    @BindView(R.id.tv_meal_category)
    TextView tvMealCategory;
    @BindView(R.id.spinnerGenre)
    Spinner spinnerGenre;
    @BindView(R.id.tv_mealPhoto)
    TextView tvMealPhoto;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.img_bookPhoto)
    CircleImageView imgBookPhoto;
    @BindView(R.id.btn_upload_bookPhoto)
    FloatingActionButton btnUploadBookPhoto;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.logout_button)
    Button logoutButton;
    Uri photo;
    AddMealViewModel viewModel;
    private static final String TAG = "AddMealActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ButterKnife.bind(this);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sendUserToLogin();
            }
        });
        viewModel = ViewModelProviders.of(this).get(AddMealViewModel.class);
        ProgressDialog pd = new ProgressDialog(this);
        btnUploadBookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etMealName.getText().toString();
                String price = etBookPrice.getText().toString();
                String category = spinnerGenre.getSelectedItem().toString();
                String min = etMin.getText().toString();
                String max = etMax.getText().toString();
                String des = etDescription.getText().toString();
                String foodId = UUID.randomUUID().toString();
                String userId = FirebaseAuth.getInstance().getUid();
                String idToken = FirebaseInstanceId.getInstance().getToken();

                //Log.e(TAG, "onClick: " + category);
                if (title.isEmpty()) {
                    etMealName.requestFocus();
                    etMealName.setError("you can't leave this field empty");
                } else if (price.isEmpty()) {
                    etBookPrice.requestFocus();
                    etBookPrice.setError("you can't leave this field empty");
                } else if (category.isEmpty()) {
                    Toast.makeText(AddMealActivity.this, "you should choose a category", Toast.LENGTH_SHORT).show();
                } else if (min.isEmpty()) {
                    etMin.requestFocus();
                    etMin.setError("you can't leave this field empty");
                } else if (max.isEmpty()) {
                    etMax.requestFocus();
                    etMax.setError("you can't leave this field empty");
                } else if (des.isEmpty()) {
                    etDescription.requestFocus();
                    etDescription.setError("you can't leave this field empty");
                }
                else if (photo==null){
                    Toast.makeText(AddMealActivity.this, "you must upload a photo first", Toast.LENGTH_SHORT).show();
                }
                else {
                    FoodModel model = new FoodModel();
                    model.setTitle(title);
                    model.setPrice(price);
                    model.setCategory(category);
                    model.setMin(min);
                    model.setMax(max);
                    model.setId(foodId);
                    model.setCookId(userId);
                    model.setDescribtion(des);
                    model.setCookToken(idToken);

                    viewModel.uploadFoodDataWork(model, getBaseContext(), photo);
                    finish();


                }
            }
        });
    }

    private void sendUserToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }

    private void pickPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                photo = result.getUri();
                Picasso.get().load(photo).into(imgBookPhoto);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
