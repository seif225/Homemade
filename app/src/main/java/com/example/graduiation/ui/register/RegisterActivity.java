package com.example.graduiation.ui.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;


import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.textInputName)
    TextInputLayout textInputName;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.textInputEmail)
    TextInputLayout textInputEmail;
    @BindView(R.id.editTextMobile)
    EditText editTextMobile;
    @BindView(R.id.textInputMobile)
    TextInputLayout textInputMobile;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.cirRegisterButton)
    CircularProgressButton cirRegisterButton;
    RegisterViewModel viewModel;
    @BindView(R.id.editTextPasswordConfirm)
    EditText editTextPasswordConfirm;
    @BindView(R.id.textInputPasswordConfirm)
    TextInputLayout textInputPasswordConfirm;
    private static final String TAG = "RegisterActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgiter);
        ButterKnife.bind(this);
        changeStatusBarColor();
        getSupportActionBar().hide();
        ProgressDialog pd = new ProgressDialog(this);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        cirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail="", phone="", pw1="", pw2="", name="";
                mail = editTextEmail.getText().toString();
                phone = editTextMobile.getText().toString();
                pw1 = editTextPassword.getText().toString();
                pw2 = editTextPasswordConfirm.getText().toString();
                name = editTextName.getText().toString();

                if(name.trim().isEmpty()){
                    editTextName.requestFocus();
                    editTextName.setError("you can't leave this field empty");
                }

                else if(mail.trim().isEmpty()){
                    editTextEmail.requestFocus();
                    editTextEmail.setError("you can't leave this field empty");
                }
                else if(!mail.contains(".com")){
                    editTextEmail.requestFocus();
                    editTextEmail.setError("wrong mail format");
                }
                else if (phone.trim().length()!=11){
                    editTextMobile.requestFocus();
                    editTextMobile.setError("this phone number is not correct");
                }
                else if(pw1.length()<6){
                    editTextPassword.requestFocus();
                    editTextPassword.setError("password should be at least 6 charcters");
                }
                else if(!pw1.equals(pw2)){
                    editTextPasswordConfirm.requestFocus();
                    editTextPasswordConfirm.setError("doesn't match your password");
                }
                else
                {
                    //TODO : Check internet connection and log user in
                    if(!isNetworkConnected()){

                        Toast.makeText(RegisterActivity.this, "it looks like you don't have an internet connection \n " +
                                ", please reconnect and try again", Toast.LENGTH_LONG).show();
                    }

                    else {

                        ProgressDialog pr = new ProgressDialog(RegisterActivity.this);
                        pr.setTitle("please wait");
                        pr.setMessage("working on registering you ..");
                        pr.setCancelable(false);
                        pr.setCanceledOnTouchOutside(false);
                        pr.show();

                        String finalName = name;
                        String finalPhone = phone;
                        String finalMail = mail;
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,pw1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    String id = FirebaseAuth.getInstance().getUid();
                                    UserParentModel user = new UserParentModel();
                                    user.setName(finalName);
                                    user.setId(id);
                                    String idToken = FirebaseInstanceId.getInstance().getToken();
                                    user.setToken(idToken);
                                    user.setEmail(finalMail);
                                    user.setPhone(finalPhone);
                                    user.setRegistrationTime(System.currentTimeMillis());

                                    viewModel.getUserRegisterationState(id,user).observe(RegisterActivity.this, new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            pr.dismiss();
                                            Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                }
                                else {
                                    pr.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }

                            }
                        });


                    }


                }
            }
        });


    }



    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }

    }

    public void onLoginClick(View view) {
      sendUserToLogin();

    }

    private void sendUserToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
