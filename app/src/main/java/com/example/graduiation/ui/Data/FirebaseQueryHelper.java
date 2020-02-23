package com.example.graduiation.ui.Data;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.graduiation.R;
import com.example.graduiation.ui.intro.IntroActivity;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.IllegalFormatException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseQueryHelper {
    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseQueryHelper";
    private static final DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference().child("Users");

    public FirebaseQueryHelper() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void SignUp(String email, String password, String confirmPassword, ProgressDialog pB, String phoneNum, String name) throws IllegalArgumentException {
        pB.setTitle("Please Wait");
        pB.show();
        if (password.length() < 6) {
            Log.i(TAG, "SignUp: " + "1");
            pB.dismiss();
            throw new IllegalArgumentException("1");
        } else if (!password.equals(confirmPassword)) {
            Log.i(TAG, "SignUp: " + "2");
            pB.dismiss();
            throw new IllegalArgumentException("2");
        } else if (phoneNum.length() < 11) {
            Log.i(TAG, "SignUp: " + "3");
            pB.dismiss();
            throw new IllegalArgumentException("3");
        } else if (name.isEmpty()) {
            Log.i(TAG, "SignUp: " + "4");
            pB.dismiss();

            throw new IllegalArgumentException("4");
        } else {
            Log.i(TAG, "SignUp: " + "1");
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                    (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pB.dismiss();
                                sendUsersDataToDatabase(name, email, password, phoneNum);
                            } else {
                                pB.dismiss();

                            }
                        }
                    });
        }

    }

    private void sendUsersDataToDatabase(String name, String email, String password, String phoneNum) {
        BuyerModel model = new BuyerModel();
        String id = mAuth.getUid();
        model.setId(id);
        model.setName(name);
        model.setPhone(phoneNum);
        model.setEmail(email);
        model.setPassword(password);
        io.reactivex.Observable<BuyerModel> observable = Observable.just(model);
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                USER_REF.child(id).setValue(o);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);

    }

    public void SignIn(String email, String password, Context context, ProgressDialog progressDialog) {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Signing you in");
        progressDialog.show();
        if (email.isEmpty()) {
            Toast.makeText(context, "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Enter a valid password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        SendUserToIntro(context);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            });
        }

    }

    private void SendUserToIntro(Context context) {
        Intent intro = new Intent(context, IntroActivity.class);
        intro.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intro);

    }


}
