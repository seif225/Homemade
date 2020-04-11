package com.example.graduiation.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.graduiation.R;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;


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
                String mail, phone, pw1, pw2, name;
                mail = editTextEmail.getText().toString();
                phone = editTextMobile.getText().toString();
                pw1 = editTextPassword.getText().toString();
                pw2 = editTextPasswordConfirm.getText().toString();
                name = editTextName.getText().toString();
                try {
                    viewModel.Authenticate(mail, pw1, pw2, pd, phone, name);
                } catch (IllegalArgumentException ex) {
                    Log.i(TAG, "onClick: " + ex.getMessage());
                    handleException(""+ex.getMessage());
                }
                finally {
                   //TODO(2):fix this bug where the user is automatically send to login activity even if an error has occurr    ed during registering
                    sendUserToLogin();
                }

            }
        });


    }

    private void handleException(String message) {

        switch (message) {

            case "1":
                editTextPassword.setError("password must be more than 6 chars");
                break;
            case "2":
                Toast.makeText(this, "password doesnt match", Toast.LENGTH_SHORT).show();
                break;
            case "3":
                editTextMobile.setError("invalid phone number");
                break;
            case "4":
                editTextName.setError("you must add a name");
                break;
        }


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


}
