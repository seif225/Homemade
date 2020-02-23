package com.example.graduiation.ui.addMeal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduiation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mean);
        ButterKnife.bind(this);



    }

}
