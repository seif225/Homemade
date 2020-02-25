package com.example.graduiation.ui.StoryDetails;

import androidx.lifecycle.ViewModel;

import com.example.graduiation.ui.Data.FirebaseQueryHelper;

public class StoryDetailsViewModel extends ViewModel {

    private String uid, category;
    private FirebaseQueryHelper firebaseQueryHelper;
    public void setCategoryAndId(String uid, String category) {
        this.uid = uid;
        this.category = category;

    }
}
