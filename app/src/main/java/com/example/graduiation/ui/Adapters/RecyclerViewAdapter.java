package com.example.graduiation.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<UserParentModel> userParentModelList;
    private Context context;
    private static final String TAG = "RecyclerViewAdapter";
    private String category;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,
                false);
        return new ViewHolder(view);
    }

    public RecyclerViewAdapter(List<UserParentModel> userParentModels, Context context, String category) {
        this.userParentModelList = userParentModels;
        this.context = context;
        this.category = category;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        UserParentModel userParentModel = userParentModelList.get(position);
        Log.e(TAG, "onBindViewHolder: " + userParentModel.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, StoryDetailsActivity2.class);
                i.putExtra("userName" , userParentModel.getName());
                i.putExtra("uid", userParentModel.getId());
                i.putExtra("category", category);
                i.putExtra("token", userParentModel.getToken());
                if(userParentModel.getToken()!=null)
                    i.putExtra("token" , userParentModel.getToken());
                context.startActivity(i);


            }
        });


        if (userParentModel.getImage() != null) {
            Picasso.get().load(userParentModel.getImage()).resize(100,100)
                    .networkPolicy(NetworkPolicy.OFFLINE).into(holder.imgStory, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(userParentModel.getImage()).resize(100,100).into(holder.imgStory);
                }
            });

        }
        else {

            holder.imgStory.setImageResource(R.drawable.profile_round);

        }

        if (userParentModel.getName() != null) holder.tvName.setText(userParentModel.getName());


    }

    @Override
    public int getItemCount() {
        if (userParentModelList == null) {
            return 0;
        } else {
            return userParentModelList.size();
        }

    }

    public RecyclerViewAdapter(Context context, String category) {
        this.context = context;
        this.category = category;
    }

    public void setData(ArrayList<UserParentModel> userParentModels) {
        this.userParentModelList=userParentModels;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgStory;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.imgStory);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
