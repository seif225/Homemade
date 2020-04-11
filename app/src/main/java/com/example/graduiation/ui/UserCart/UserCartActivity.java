package com.example.graduiation.ui.UserCart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCartActivity extends AppCompatActivity {


    RecyclerView cartRecyclerView;
    @BindView(R.id.circular_send_request_button)
    CircularProgressButton circularSendRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        ButterKnife.bind(this);
        cartRecyclerView= findViewById(R.id.cart_recyclerView);

    }
}
