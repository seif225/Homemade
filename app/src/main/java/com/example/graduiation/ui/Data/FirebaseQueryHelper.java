package com.example.graduiation.ui.Data;


import android.app.ProgressDialog;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.IllegalFormatException;

public class FirebaseQueryHelper {
    FirebaseAuth mAuth;

    public FirebaseQueryHelper() {
        mAuth = FirebaseAuth.getInstance();

    }

    public void SignUp(String email, String password, String confirmPassword, ProgressDialog
            pB) throws IllegalArgumentException {
        pB.setTitle("Please Wait");
        pB.show();

        if (password.length() >= 6 && password.equals(confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener

                    (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }
                    });

        } else if (password.length() < 6) {
            throw new IllegalArgumentException("1");
        } else if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("2");

        }
        pB.dismiss();
    }
}
