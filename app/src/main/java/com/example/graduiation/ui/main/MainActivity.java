package com.example.graduiation.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.UserCart.UserCartActivity;
import com.example.graduiation.ui.WorkManagers.UploadUserTokenWorkManagerToFirebase;
import com.example.graduiation.ui.addMeal.AddMealActivity;
import com.example.graduiation.ui.login.LoginActivity;
import com.example.graduiation.ui.profileTEST.ProfileTesting;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private View navigationHeader;
    private CircleImageView navuseRImage;
    private TextView navUserName, navUserMail;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationHeader = navigationView.getHeaderView(0);
        navuseRImage = navigationHeader.findViewById(R.id.imageView);
        navUserName = navigationHeader.findViewById(R.id.user_name);
        navUserMail = navigationHeader.findViewById(R.id.nav_user_mail);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            viewModel.getUserParentModel(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<UserParentModel>() {
                @Override
                public void onChanged(UserParentModel userParentModel) {
                    if (userParentModel != null) {

                        navUserName.setText(userParentModel.getName());
                        navUserMail.setText(userParentModel.getEmail());

                        Picasso.get().load(userParentModel.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(navuseRImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(userParentModel.getImage()).into(navuseRImage);

                            }
                        });


                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            sendUserToLogin();
        }

        if (item.getItemId() == R.id.cart_button) {

            sendUserToCart();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendUserToCart() {
        Intent i = new Intent(this, UserCartActivity.class);
        startActivity(i);
    }

    private void sendUserToTestingProfile() {
        Intent i = new Intent(this, ProfileTesting.class);
        startActivity(i);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            sendUserToLogin();
        } /*else if (mAuth.getCurrentUser().getUid().equals("7zOFRqGEuwcyL9h7IuBRW9OWdDn1")
                || mAuth.getCurrentUser().getUid().equals("a10286zJHwaaq0zAPW26XX6uITB2")) {
            sendUserToAddMeal();
        }*/



        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadUserTokenWorkManagerToFirebase.class).build();
        WorkManager.getInstance(MainActivity.this).enqueue(oneTimeWorkRequest);

        viewModel.getUserParentModel(mAuth.getUid()).observe(this, new Observer<UserParentModel>() {
            @Override
            public void onChanged(UserParentModel userParentModel) {
                Log.e(TAG, "onChanged: " +userParentModel.getToken() );
                Log.e(TAG, "onChanged: " +userParentModel.getId() );
                Log.e(TAG, "onChanged: " +userParentModel.getName() );
                if(userParentModel.getName()!=null && userParentModel.getId()!=null && userParentModel.getToken()!=null){

                    SharedPreferences sharedPreferences=getSharedPreferences("userData", Context.MODE_PRIVATE);


                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("id", userParentModel.getId());
                    editor.putString("token", userParentModel.getToken());
                    editor.putString("name", userParentModel.getName());
                    editor.apply();

                }


            }
        });


        Log.e(TAG, "onStart: " + mAuth.getUid());

    }

    private void sendUserToAddMeal() {
        Intent i = new Intent(this, AddMealActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void sendUserToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



}
