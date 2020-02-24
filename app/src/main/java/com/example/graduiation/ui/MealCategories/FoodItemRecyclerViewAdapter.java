package com.example.graduiation.ui.MealCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.BuyerModel;

import java.util.List;

public class FoodItemRecyclerViewAdapter extends RecyclerView.Adapter
        <FoodItemRecyclerViewAdapter.ViewHolder> {
    private List<BuyerModel> buyerModelList;
    private Context context;

    @NonNull
    @Override
    public FoodItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent
                , false);
        return new ViewHolder(view);
    }

    public FoodItemRecyclerViewAdapter(List<BuyerModel> buyerModels, Context context) {
        this.buyerModelList = buyerModels;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemRecyclerViewAdapter.ViewHolder holder, int position) {
        BuyerModel buyerModel = buyerModelList.get(position);

    }

    @Override
    public int getItemCount() {
        if (buyerModelList == null) {
            return 0;
        } else {
            return buyerModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Description;
        TextView textView_Name;
        ImageView imageView_foodImage;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            TextView textView_Description = itemView.findViewById(R.id.textView_Description);
            TextView textView_Name = itemView.findViewById(R.id.textView_Name);
            ImageView imageView_foodImage = itemView.findViewById(R.id.imageView_foodImage);
        }
    }
}
