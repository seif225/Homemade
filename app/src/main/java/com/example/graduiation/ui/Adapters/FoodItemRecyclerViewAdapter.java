package com.example.graduiation.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.Meal.MealFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodItemRecyclerViewAdapter extends RecyclerView.Adapter
        <FoodItemRecyclerViewAdapter.ViewHolder> {

    private List<FoodModel> foodModelList;
    private Context context;
    private static final String TAG = "FoodItemRecyclerViewAda";
    private String userName,userPicture , userId;

    @NonNull
    @Override
    public FoodItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent
                , false);
        return new ViewHolder(view);
    }

    public FoodItemRecyclerViewAdapter(List<FoodModel> buyerModels, Context context ) {
        this.foodModelList = buyerModels;
        this.context = context;

    }

    public FoodItemRecyclerViewAdapter(ArrayList<FoodModel> arraylist, Context context, String userName, String userPicture , String userId) {

        this.foodModelList = arraylist;
        this.context = context;
        this.userName=userName;
        this.userPicture=userPicture;
        this.userId=userId;

    }

    public void setFoodModelList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemRecyclerViewAdapter.ViewHolder holder, int position) {
        FoodModel foodModel = foodModelList.get(position);
        if(foodModel.getDescribtion()!= null) holder.textView_Description.setText(foodModel.getDescribtion());
        if(foodModel.getTitle()!= null) holder.textView_Name.setText(foodModel.getTitle());
        if(foodModel.getDescribtion()!= null) Log.e(TAG, "onBindViewHolder:FOOD " + foodModel.getDescribtion());;
        if(foodModel.getThumbnail()!= null) {Picasso.get().load(foodModel.getThumbnail())
                .networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView_foodImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    Picasso.get().load(foodModel.getThumbnail()).into(holder.imageView_foodImage);

                    }
                });

        }
        if(foodModel.getPrice()!= null) holder.price_tv.setText(foodModel.getPrice()+" EGP");

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseQueryHelperRepository.getInstance().addItemToCart(
                        context
                        ,FirebaseAuth.getInstance().getUid()
                        ,foodModel
                );


            }
        });

        if(foodModel.getCookId().equals(FirebaseAuth.getInstance().getUid())) {
            holder.addToCartButton.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MealFragment.class);
                String gsonMeal = new Gson().toJson(foodModel);
                i.putExtra("meal"  , gsonMeal);

                i.putExtra("userName"  , userName);
                i.putExtra("userPicture"  , userPicture);
                i.putExtra("userId",userId);

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (foodModelList == null) {
            return 0;
        } else {
            return foodModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Description;
        TextView textView_Name;
        ImageView imageView_foodImage;
        TextView price_tv;
        ConstraintLayout constraintLayout;
        Button addToCartButton;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView_Description = itemView.findViewById(R.id.textView_Description);
            textView_Name = itemView.findViewById(R.id.textView_Name);
            constraintLayout = itemView.findViewById(R.id.parent);
            imageView_foodImage = itemView.findViewById(R.id.food_image);
            price_tv = itemView.findViewById(R.id.textView_price);
            addToCartButton=itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}
