package com.example.graduiation.ui.UserCart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.CartRecyclerViewAdapter;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.PlacePickerActivity.PlacePickerActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCartActivity extends AppCompatActivity {


    UserCartViewModel viewModel;
    CartRecyclerViewAdapter cartRecyclerViewAdapter;
    RecyclerView cartRecyclerView;
    @BindView(R.id.circular_send_request_button)
    CircularProgressButton circularSendRequestButton;
    @BindView(R.id.pick_location_button)
    Button pickLocationButton;
    private static final int PLACE_PICKER_REQUEST = 111;
    private static final String TAG = "UserCartActivity";
    @BindView(R.id.total_price_tv)
    TextView totalPriceTv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String address = data.getStringExtra("address");
                Log.e(TAG, "onActivityResult: " + address);
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        ButterKnife.bind(this);
        cartRecyclerView = findViewById(R.id.cart_recyclerView);
        viewModel = ViewModelProviders.of(this).get(UserCartViewModel.class);
        viewModel.getCartItems(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {
                cartRecyclerViewAdapter = new CartRecyclerViewAdapter(foodModels, getBaseContext(), viewModel);
                cartRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
            }

        });


        viewModel.getTotalPrice().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                totalPriceTv.setText("Total price is : " + integer);

            }
        });

        pickLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserCartActivity.this, PlacePickerActivity.class);
                startActivityForResult(i, PLACE_PICKER_REQUEST);

            }
        });


    }
}
