package com.example.graduiation.ui.WorkManagers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.OrderModel;
import com.google.gson.Gson;

public class AddingActionsOnOrdersWorkManager extends Worker {
    public AddingActionsOnOrdersWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Gson gson = new Gson();
        OrderModel model = gson.fromJson(getInputData().getString("order"), OrderModel.class);
        if(model.getState().equals("4")){

            //TODO:  notify user every quarter
        }

        FirebaseQueryHelperRepository.getInstance().addActionsOnOrder(model,getInputData().getString("name"));


        return Result.success();
    }
}
