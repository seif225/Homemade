package com.example.graduiation.ui.WorkManagers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;

public class UploadUserTokenWorkManagerToFirebase extends Worker {
    public UploadUserTokenWorkManagerToFirebase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        String idToken = FirebaseInstanceId.getInstance().getToken();
        String id = FirebaseAuth.getInstance().getUid();
        FirebaseQueryHelperRepository.getInstance().uploadUsersToken(id, idToken);
        FirebaseQueryHelperRepository.getInstance().UploadNewTokenToUsersFoodList(id ,idToken);


        return Result.success();
    }
}
