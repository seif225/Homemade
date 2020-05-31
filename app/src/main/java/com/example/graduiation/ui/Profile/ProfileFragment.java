package com.example.graduiation.ui.Profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.addMeal.AddMealActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private View root;
    private ProfileViewModel viewModel;
    private CircleImageView userProfilePicture;
    private TextView userName, followers, orders;
    private FloatingActionButton fab, addProfilePic;
    private static final String TAG = "ProfileFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfilePicture = root.findViewById(R.id.profileImage);
        userName = root.findViewById(R.id.tvUserName);
        fab = root.findViewById(R.id.profiel_fab);
        addProfilePic = root.findViewById(R.id.add_profile_picture);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToAddMealActivity();
            }
        });

        followers = root.findViewById(R.id.tvFollowers);
        orders = root.findViewById(R.id.tvMeals);

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        viewModel.getNumOfFollowers(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e(TAG, "onChanged: " + integer);
                followers.setText(integer + " Followers");
            }
        });

        viewModel.getNumOfOrders(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                orders.setText(integer + " Orders");

            }
        });



        viewModel.getUserParentModel(FirebaseAuth.getInstance().getUid()).observe(this, new Observer<UserParentModel>() {
            @Override
            public void onChanged(UserParentModel userParentModel) {
                if (userParentModel != null) {
                    userName.setText(userParentModel.getName());
                    Picasso.get().load(userParentModel.getImage()).fit().networkPolicy(NetworkPolicy.OFFLINE).into(userProfilePicture, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(userParentModel.getImage()).into(userProfilePicture);

                        }
                    });


                }
            }
        });


        addProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });

        return root;
    }

    private void sendUserToAddMealActivity() {

        Intent i = new Intent(getActivity(), AddMealActivity.class);
        getActivity().startActivity(i);
    }

    private void pickPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getActivity());
    }

}
