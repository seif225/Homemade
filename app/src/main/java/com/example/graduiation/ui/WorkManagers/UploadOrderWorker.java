package com.example.graduiation.ui.WorkManagers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.OrderModel;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UploadOrderWorker extends Worker {

    private FirebaseQueryHelperRepository firebaseQueryHelperRepository = FirebaseQueryHelperRepository.getInstance();

    public UploadOrderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String inp = getInputData().getString("data");
        Gson gson = new Gson();
        OrderModel model = gson.fromJson(getInputData().getString("order"), OrderModel.class);
        ArrayList<FoodModel> listOfFoodModels = model.getListOfFood();
        HashMap<String,OrderModel> orderModelHashSet = new HashMap<>();
        HashSet<String> hashset = new HashSet<>();
        for (FoodModel model1  : listOfFoodModels) {
            hashset.add(model1.getCookId());
        }
        
        
        
        for (String id : hashset) {
            OrderModel orderModel = new OrderModel();
            ArrayList<FoodModel> listOfFoodModelPerEachCook = new ArrayList<>();

            for (FoodModel foodModel2: listOfFoodModels) {
                if(foodModel2.getCookId().equals(id)){
                    listOfFoodModelPerEachCook.add(foodModel2);
                }
                
            }

            orderModel.setListOfFood(listOfFoodModelPerEachCook);
            orderModel.setState("1");
            orderModel.setCookId(id);
            orderModel.setOrderPostTimeInUnix(model.getOrderPostTimeInUnix());
            orderModel.setOrderId(model.getOrderId());
            orderModel.setAddress(model.getAddress());
            orderModel.setUserCoordinates(model.getUserCoordinates());
            
            orderModelHashSet.put(id,orderModel);
            
        }


        firebaseQueryHelperRepository.addCartToOrders(orderModelHashSet,model.getOrderId());

        return Result.success();
    }
}
