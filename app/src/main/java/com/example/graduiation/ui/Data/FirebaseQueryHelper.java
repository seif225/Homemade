package com.example.graduiation.ui.Data;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.graduiation.R;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.IllegalFormatException;

public class FirebaseQueryHelper {
    FirebaseAuth mAuth;
    private static final String TAG = "FirebaseQueryHelper";

    public FirebaseQueryHelper() {
        mAuth = FirebaseAuth.getInstance();

    }

    public void SignUp(String email, String password, String confirmPassword, ProgressDialog
            pB, String phoneNum, String name) throws IllegalArgumentException {
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
        } else if (phoneNum.length()<11) {
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
                            pB.dismiss();


                        }
                    });
        }

    }


}
