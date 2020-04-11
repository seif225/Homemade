package com.example.graduiation.ui.UserCart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.CartRecyclerViewAdapter;
import com.example.graduiation.ui.Data.CartItemModel;
import com.example.graduiation.ui.Data.FoodModel;
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
                cartRecyclerViewAdapter = new CartRecyclerViewAdapter(foodModels,getBaseContext());
                cartRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
            }

        });

    }
}
