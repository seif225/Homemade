package com.example.graduiation.ui.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.StoryDetails.StoryDetailsActivity2;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KitchensRecyclerAdapter extends RecyclerView.Adapter<KitchensRecyclerAdapter.ViewHolder> {


    private ArrayList<UserParentModel> listOfKitchens;
    private String category;
    private int mode = 0;

    public KitchensRecyclerAdapter(ArrayList<UserParentModel> listOfKitchens, String category) {
        this.listOfKitchens = listOfKitchens;
        this.category=category;
    }

    public KitchensRecyclerAdapter(String category) {
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserParentModel model = listOfKitchens.get(position);

        holder.userNameTv.setText(model.getName());
       if (model.getImage()!=null) Picasso.get().load(model.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.userPic, new Callback() {
            @Override
            public void onSuccess() {
                if (model.getImage()!=null) Picasso.get().load(model.getImage()).into(holder.userPic);
            }

            @Override
            public void onError(Exception e) {

            }
        });

       if(model.getFollowing()!=null) holder.followTv.setText(model.getFollowing()+ " Followers") ;
        if(model.getNumberOfOrders()!=null) holder.ordersTv.setText(model.getNumberOfOrders()+ " Orders");
        if(model.getRate()!=null) holder.ratingBar.setRating(Float.parseFloat(model.getRate()));
        if(position==listOfKitchens.size()-1){
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(16, 16, 16, 48);
            holder.itemView.setLayoutParams(params);


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), StoryDetailsActivity2.class);
                i.putExtra("userName" , model.getName());
                i.putExtra("uid", model.getId());
                i.putExtra("category", category);
                i.putExtra("token", model.getToken());
                if(model.getToken()!=null)
                    i.putExtra("token" , model.getToken());
                holder.itemView.getContext().startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfKitchens==null ? 0 : listOfKitchens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView userNameTv , followTv , ordersTv;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPic = itemView.findViewById(R.id.food_image);
            userNameTv = itemView.findViewById(R.id.textView_Name);
            followTv = itemView.findViewById(R.id.followers_kitchen_tv);
            ordersTv = itemView.findViewById(R.id.orders_tv);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }




    public void setData(ArrayList<UserParentModel> listOfKitchens){
        this.listOfKitchens=listOfKitchens;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getLastItemDate() {
        return listOfKitchens.get(listOfKitchens.size()-1).getRegistrationTime();
    }
    public String getLastItemId() {
        return listOfKitchens.get(listOfKitchens.size()-1).getId();
    }


}
