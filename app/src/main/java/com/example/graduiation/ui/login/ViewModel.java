package com.example.graduiation.ui.login;

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

public class ViewModel extends androidx.lifecycle.ViewModel {

    MutableLiveData<String> phoneNumberMutableLiveData= new MutableLiveData<>();
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private String mVerificationId;
    FirebaseAuth mAuth;
    ViewModel(Ilogin ilogin){

        mAuth=FirebaseAuth.getInstance();
        ilogin.verifyPhoneNumber();
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

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
                        }else{
                            Log.e("Samra","great job also");
                        }
                    }
                });
    }

}

