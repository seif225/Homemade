package com.example.graduiation.ui.MealCategories;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;

class KitchensViewHolder extends RecyclerView.ViewHolder {
    ImageView userPic;
    TextView userNameTv , followTv , ordersTv;
    RatingBar ratingBar;
    public KitchensViewHolder(@NonNull View itemView) {
        super(itemView);
        userPic = itemView.findViewById(R.id.food_image);
        userNameTv = itemView.findViewById(R.id.textView_Name);
        followTv = itemView.findViewById(R.id.followers_kitchen_tv);
        ordersTv = itemView.findViewById(R.id.orders_tv);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
