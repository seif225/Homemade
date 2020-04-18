package com.example.graduiation.ui.WorkManagers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UploadOrderWorker extends Worker {

    private FirebaseQueryHelperRepository firebaseQueryHelperRepository = FirebaseQueryHelperRepository.getInstance();
    private static final String TAG = "UploadOrderWorker";
    public UploadOrderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private String userName ;

    @NonNull
    @Override
    public Result doWork() {



         userName = getInputData().getString("name");

        String inp = getInputData().getString("data");
        Gson gson = new Gson();
        OrderModel model = gson.fromJson(getInputData().getString("order"), OrderModel.class);

        ArrayList<FoodModel> listOfFoodModels = model.getListOfFood();
        HashMap<String, OrderModel> orderModelHashSet = new HashMap<>();
        HashSet<String> hashSetOfCookIds = new HashSet<>();
        HashSet<String> hashSetOfCookTokens = new HashSet<>();




        for (FoodModel model1 : listOfFoodModels) {
            Log.e(TAG, "doWork: "+ model1.getCookId() );
            hashSetOfCookIds.add(model1.getCookId());
            if (model1.getCookToken() != null) {
                hashSetOfCookTokens.add(model1.getCookToken());
                Log.e(TAG, "doWork: "+model1.getCookToken());
            }
        }


        for (String id : hashSetOfCookIds) {
            OrderModel orderModel = new OrderModel();

            ArrayList<FoodModel> listOfFoodModelPerEachCook = new ArrayList<>();

            for (FoodModel foodModel2 : listOfFoodModels) {
                if (foodModel2.getCookId().equals(id)) {
                    listOfFoodModelPerEachCook.add(foodModel2);
                }

            }

            orderModel.setListOfFood(listOfFoodModelPerEachCook);
            orderModel.setState("1");
            orderModel.setCookId(id);
            orderModel.setCookToken(model.getCookToken());
            orderModel.setOrderPostTimeInUnix(model.getOrderPostTimeInUnix());
            orderModel.setOrderId(model.getOrderId());
            orderModel.setAddress(model.getAddress());
            orderModel.setLng(model.getLng());
            orderModel.setLat(model.getLat());
            orderModel.setBuyerId(model.getBuyerId());
            orderModel.setBuyerToken(model.getBuyerToken());

            orderModelHashSet.put(id, orderModel);

        }


        firebaseQueryHelperRepository.addCartToOrders(orderModelHashSet, model.getOrderId(), hashSetOfCookTokens , userName);

        return Result.success();
    }
}
