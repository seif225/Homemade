package com.example.graduiation.ui.UserCart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.graduiation.R;
import com.example.graduiation.ui.Adapters.CartRecyclerViewAdapter;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.OrderModel;
import com.example.graduiation.ui.DataStructuresAndAlgos.FoodSort;
import com.example.graduiation.ui.PlacePickerActivity.PlacePickerActivity;
import com.example.graduiation.ui.WorkManagers.UploadOrderWorker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    @BindView(R.id.address)
    TextView address;
    private LatLng userCoordinates;
    private OrderModel orderModel;
    private String addressStr;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                 addressStr = data.getStringExtra("address");
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);

                if (lat > 0 && lng > 0) {
                    Log.e(TAG, "onActivityResult: " + lat);
                    Log.e(TAG, "onActivityResult: " + lng);
                   // userCoordinates = new LatLng(lat, lng);
                    orderModel.setLat(lat+"");
                    orderModel.setLng(lng+"");

                }
                Log.e(TAG, "onActivityResult: " + addressStr);


                if (addressStr != null) {address.setText(addressStr);
                orderModel.setAddress(addressStr);
                }
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        ButterKnife.bind(this);
        orderModel = new OrderModel();
        cartRecyclerView = findViewById(R.id.cart_recyclerView);
        viewModel = ViewModelProviders.of(this).get(UserCartViewModel.class);
        viewModel.getCartItems(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<ArrayList<FoodModel>>() {
            @Override
            public void onChanged(ArrayList<FoodModel> foodModels) {
                cartRecyclerViewAdapter = new CartRecyclerViewAdapter(foodModels, getBaseContext(), viewModel);
                cartRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
                orderModel.setListOfFood(foodModels);
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


        circularSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addressStr!=null){


                ProgressDialog pd = new ProgressDialog(UserCartActivity.this);
                pd.setTitle("saving your order ..");
                pd.setMessage("please wait ...");
                pd.show();;
                    SharedPreferences sharedPref = getSharedPreferences("userData",Context.MODE_PRIVATE);
                    String name = sharedPref.getString("name", "No name defined");
                    String token = sharedPref.getString("token", "No name defined");
                    String id = sharedPref.getString("id", "No name defined");
                String orderId = UUID.randomUUID().toString();

                long unixTime = System.currentTimeMillis();

                        orderModel.setOrderId(orderId);
                        orderModel.setOrderPostTimeInUnix(unixTime);
                        orderModel.setState("1");
                        orderModel.setBuyerToken(token);
                        orderModel.setBuyerId(id);


                Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

                Gson gson = new Gson();
                String json = gson.toJson(orderModel);



                Data data = new Data.Builder()
                        .putString("data", "dummy text ")
                        .putString("order",json)
                        .putString("name",name)
                        .build();

                OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadOrderWorker.class)
                        .setInputData(data)
                        .setConstraints(constraints)
                        .build();

                WorkManager.getInstance(UserCartActivity.this).enqueue(uploadWorkRequest);


                WorkManager.getInstance(UserCartActivity.this).getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                        .observe(UserCartActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(@Nullable WorkInfo workInfo) {
                                if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                    Toast.makeText(UserCartActivity.this,
                                            "your request has been sent to seller" +
                                                    "\n you can follow up your order from the orders page " +
                                                    " "+workInfo.getState(),
                                            Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                    finish();
                                }
                                else{

                                   if(!isNetworkAvailable()){
                                    Toast.makeText(UserCartActivity.this
                                            , "no internet connection available , once you're connected to network your request will proceed  : "+ workInfo.getState()
                                            , Toast.LENGTH_LONG).show();
                                       pd.dismiss();

                                   }
                                   else if(workInfo.getState()==WorkInfo.State.FAILED) {
                                       Toast.makeText(UserCartActivity.this
                                               , "looks like something went wrong",
                                               Toast.LENGTH_SHORT).show();
                                   }
                                }
                            }
                        });
            }else {
                    Toast.makeText(UserCartActivity.this, "you need to pick location first", Toast.LENGTH_SHORT).show();
                }
                }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
