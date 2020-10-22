package com.example.graduiation.ui.main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.OrdersHanging.OrdersHangingFragment;
import com.example.graduiation.ui.OrdersReceived.OrdersRecievedPlaceHolder;
import com.example.graduiation.ui.UserCart.UserCartActivity;
import com.example.graduiation.ui.WorkManagers.UploadUserTokenWorkManagerToFirebase;
import com.example.graduiation.ui.addMeal.AddMealActivity;
import com.example.graduiation.ui.login.LoginActivity;
import com.example.graduiation.ui.login.LoginActivity2;
import com.example.graduiation.ui.profileTEST.ProfileTesting;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private View navigationHeader;
    private CircleImageView navuseRImage;
    private TextView navUserName, navUserMail;
    private MainViewModel viewModel;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

       /* navUserName.setOnClickListener(view -> {

        });*/

        Toolbar toolbar = findViewById(R

                .id.toolbar);
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
        navigationView = findViewById(R.id.nav_view);
        //  hideItem();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawer)
                .build();


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Log.e(TAG, "onNavigationItemSelected: \n \n ");
                if (id == R.id.nav_log_out) {

                    logOut();
                }
                return false;
            }
        });


        navigationHeader = navigationView.getHeaderView(0);
        navuseRImage = navigationHeader.findViewById(R.id.imageView);
        navUserName = navigationHeader.findViewById(R.id.user_name);
        navUserMail = navigationHeader.findViewById(R.id.nav_user_mail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id == R.id.nav_log_out) {

                }
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            viewModel.getUserParentModel(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<UserParentModel>() {
                @Override
                public void onChanged(UserParentModel userParentModel) {
                    if (userParentModel != null) {
                        navUserName.setText(userParentModel.getName());
                        navUserMail.setText(userParentModel.getEmail());
                        if (userParentModel.getDueDate() == 0) {
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_recieved_orders).setVisible(false);
                        }

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

        String flag = getIntent().getStringExtra("flag");
        if (flag != null) {


            if (flag.equals("orderReceived")) {

                transactFragmnetWithActions();

            }

        }

    }

    private void transactFragmnetWithActions() {

        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_recieved_orders);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

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
        Log.e(TAG, "onSupportNavigateUp: ");
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();

    }


    @Override
    protected void onStart() {
        super.onStart();

        //Log.e(TAG, "\n \n \n onStart: TOKEN \n \n \n "+ FirebaseInstanceId.getInstance().getToken() );

        SharedPreferences sharedPref = getSharedPreferences(
                "userFile", Context.MODE_PRIVATE);
        Log.e(TAG, "onStart: "+sharedPref.getString("userToken","") );
        if (sharedPref.getString("userToken","")==null ||sharedPref.getString("userToken","").isEmpty()) {
            sendUserToLogin();
        } else {

            if (getSharedPreferences("userData", Context.MODE_PRIVATE).getString("token", null) == null) {
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadUserTokenWorkManagerToFirebase.class).build();
                WorkManager.getInstance(MainActivity.this).enqueue(oneTimeWorkRequest);

                viewModel.getUserParentModel(mAuth.getUid()).observe(this, new Observer<UserParentModel>() {
                    @Override
                    public void onChanged(UserParentModel userParentModel) {
                        Log.e(TAG, "onChanged: " + userParentModel.getToken());
                        Log.e(TAG, "onChanged: " + userParentModel.getId());
                        Log.e(TAG, "onChanged: " + userParentModel.getName());

                        if (userParentModel.getName() != null && userParentModel.getId() != null && userParentModel.getToken() != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
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
        }

    }

    private void sendUserToAddMeal() {
        Intent i = new Intent(this, AddMealActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void sendUserToLogin() {
        Intent i = new Intent(this, LoginActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri photo = result.getUri();
                FirebaseQueryHelperRepository.getInstance().uploadUserProfilePic(FirebaseAuth.getInstance().getUid(), photo);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        sendUserToLogin();
    }


}
