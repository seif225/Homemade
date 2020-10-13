package com.example.graduiation.ui.login;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.graduiation.R;
import com.example.graduiation.databinding.ActivityLoginBinding;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity2 extends AppCompatActivity implements Ilogin{
    String phoneNumber;
    ViewModel viewModel;
    String id;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        viewModel=new ViewModel(this);
        binding.cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.editTextPhoneNumber.getText().toString().trim().isEmpty()) {
                    phoneNumber = binding.editTextPhoneNumber.getText().toString();
                    verifyPhoneNumber();
                }else{
                    Toast.makeText(LoginActivity2.this,"Please Enter Your Phone Number",Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(id,
                        binding.editTextPhoneNumber.getText().toString());
                viewModel.signInWithCredential(credential);
            }
        });

    }


    @Override
    public void verifyPhoneNumber() {
        if(phoneNumber.charAt(0)=='0'&&phoneNumber.charAt(1)=='1'){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    viewModel.mCallbacks);
        }
    }

    @Override
    public void codeVerify(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        binding.editTextPhoneNumber.setHint("Enter Code");
       binding.send.setVisibility(View.VISIBLE);
       binding.cirLoginButton.setVisibility(View.GONE);
        id=verificationId;
    }
}
