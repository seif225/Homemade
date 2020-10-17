package com.example.graduiation.ui.login;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.graduiation.ui.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends androidx.lifecycle.ViewModel {

    MutableLiveData<String> phoneNumberMutableLiveData= new MutableLiveData<>();
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private String mVerificationId;
    FirebaseAuth mAuth;
    Ilogin ilogin;
    ViewModel(Ilogin ilogin){

        mAuth=FirebaseAuth.getInstance();
        this.ilogin=ilogin;
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("onField",e.getMessage()+"");
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId,token);
                mVerificationId = verificationId;
                mToken = token;
                ilogin.codeVerify(mVerificationId,mToken);

                // ...
            }

        };

    }

    void  signInWithCredential(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e("Samra", "Great job");
                            sendUserToServer(mAuth.getCurrentUser().getUid(),mAuth.getCurrentUser().getPhoneNumber());
                            ilogin.enterHomePage();

                        }else{
                            Log.e("Samra","great job also");
                        }
                    }
                });
    }

    void sendUserToServer(String id,String phone){
        UserModel user=new UserModel(id,"Mohamed",phone);
        Call<UserModel> call=ApiManager.getAPIS().postUser(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e("Send User To Server",response.body()+"");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Send User To Server","field");

            }
        });
    }
}

