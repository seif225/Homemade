package com.example.graduiation.ui.login;

import androidx.annotation.NonNull;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface Ilogin {

    void verifyPhoneNumber();
    void codeVerify(String verificationId, PhoneAuthProvider.ForceResendingToken token);
}
