package com.example.graduiation.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.DataRepo;
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

public class ViewModel extends AndroidViewModel {

    MutableLiveData<String> phoneNumberMutableLiveData = new MutableLiveData<>();
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private String mVerificationId;
    FirebaseAuth mAuth;
    Ilogin ilogin;
    private static final String TAG = "ViewModel";

    public ViewModel(@NonNull Application application, Ilogin ilogin , LoginCallback callback) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        this.ilogin = ilogin;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential , callback);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("onField", e.getMessage() + "");
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);
                mVerificationId = verificationId;
                mToken = token;
                ilogin.codeVerify(mVerificationId, mToken);

                // ...
            }

        };
    }

  /*  ViewModel(Ilogin ilogin) {

        mAuth = FirebaseAuth.getInstance();
        this.ilogin = ilogin;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("onField", e.getMessage() + "");
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);
                mVerificationId = verificationId;
                mToken = token;
                ilogin.codeVerify(mVerificationId, mToken);

                // ...
            }

        };

    }*/


  /*  public void provideI(Ilogin ilogin) {

        mAuth = FirebaseAuth.getInstance();
        this.ilogin = ilogin;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("onField", e.getMessage() + "");
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);
                mVerificationId = verificationId;
                mToken = token;
                ilogin.codeVerify(mVerificationId, mToken);

                // ...
            }

        };

    }
*/
    void signInWithCredential(PhoneAuthCredential phoneAuthCredential,LoginCallback callback) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("Samra", "Great job");
                            sendUserToServer(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getPhoneNumber() , callback);
                            ilogin.enterHomePage();

                        } else {
                            Log.e("Samra", "great job also");
                        }
                    }
                });
    }

    void sendUserToServer(String id, String phone , LoginCallback i) {
        UserModel user = new UserModel(id, "Mohamed", phone);
        Call<UserModel> call = ApiManager.getAPIS().postUser(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e(TAG, "onResponse: " + phone );
                Log.e("Send User To Server", response.body() + "");
                Context context =getApplication().getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                       "userFile", Context.MODE_PRIVATE);



                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userToken", response.body().getTokens());
                editor.putString("userId",id);
                editor.apply();
                Log.e(TAG, "onResponse: " + sharedPref.getString("userToken","empty :D ") );

                i.sendUserToMain();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Send User To Server", "field"
                        + t.getMessage()
                        + " \n" + t.getLocalizedMessage()
                        + "\n" + t.getCause()) ;

            }
        });
    }
}