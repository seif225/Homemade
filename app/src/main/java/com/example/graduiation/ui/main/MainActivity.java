package com.example.graduiation.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.MyApplication;
import com.example.graduiation.ui.Profile.ProfileViewModel;
import com.example.graduiation.ui.addMeal.AddMealActivity;
import com.example.graduiation.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
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

        return super.onOptionsItemSelected(item);
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
        } else if (mAuth.getCurrentUser().getUid().equals("7zOFRqGEuwcyL9h7IuBRW9OWdDn1")
                || mAuth.getCurrentUser().getUid().equals("a10286zJHwaaq0zAPW26XX6uITB2")) {
            sendUserToAddMeal();
        }

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
