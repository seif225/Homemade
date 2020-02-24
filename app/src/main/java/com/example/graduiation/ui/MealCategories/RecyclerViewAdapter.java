package com.example.graduiation.ui.MealCategories;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserParentModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<UserParentModel> userParentModelList;
    private Context context;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,
                false);
        return new ViewHolder(view);
    }

    public RecyclerViewAdapter(List<UserParentModel> userParentModels, Context context) {
        this.userParentModelList = userParentModels;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        UserParentModel userParentModel = userParentModelList.get(position);

    }

    @Override
    public int getItemCount() {
        if (userParentModelList == null) {
            return 0;
        } else {
            return userParentModelList.size();
        }

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
