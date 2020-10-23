package com.example.graduiation.ui.Adapters;

import android.content.Context;
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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.graduiation.R;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.example.graduiation.ui.UserCart.UserCartViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private List<FoodModel> foodModelList;
    private Context context;
    private static final String TAG = "CartRecyclerViewAdapter";
    private int overAllPrice = 0;
    private UserCartViewModel viewModel;

    public CartRecyclerViewAdapter(List<FoodModel> buyerModels, Context context, UserCartViewModel viewModel) {
        this.foodModelList = buyerModels;
        this.context = context;
        this.viewModel = viewModel;
        for (FoodModel foodModel: foodModelList){



            overAllPrice = overAllPrice + (Integer.parseInt(foodModel.getPrice()) * Integer.parseInt(foodModel.getQuantity()));
            Log.e(TAG, "onCreateViewHolder: "+(Integer.parseInt(foodModel.getPrice()) * Integer.parseInt(foodModel.getQuantity())));

        }
        viewModel.getTotalPrice().setValue(overAllPrice);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent
                , false);




        return new CartRecyclerViewAdapter.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        FoodModel foodModel = foodModelList.get(position);

        if (foodModel.getTitle() != null) holder.textView_Name.setText(foodModel.getTitle());
        if (foodModel.getDescribtion() != null)
            Log.e(TAG, "onBindViewHolder:FOOD " + foodModel.getDescribtion());
        ;
        if (foodModel.getThumbnail() != null) {
            Picasso.get().load(foodModel.getThumbnail())
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

        if (foodModel.getPrice() != null) {
            holder.price_tv.setText(foodModel.getPrice() + "$");

        }
        ;


        holder.elegantNumberButton.setNumber(foodModel.getQuantity());

        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int price = Integer.parseInt(foodModel.getPrice());
                foodModel.setQuantity(newValue + "");
                int total = Integer.parseInt(foodModel.getQuantity()) * price;
                holder.price_tv.setText(total + "$");
                overAllPrice = overAllPrice - (price * oldValue);
                overAllPrice = overAllPrice + total;
                viewModel.getTotalPrice().setValue(overAllPrice);

            }
        });


        holder.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseQueryHelperRepository.deleteItemFromCart(FirebaseAuth.getInstance().getUid(), foodModel.getId());
                removeItem(position);

            }
        });

    }

    private void removeItem(int position) {
        FoodModel foodModel = foodModelList.get(position);
        overAllPrice = overAllPrice - (Integer.parseInt(foodModel.getPrice()) * Integer.parseInt(foodModel.getQuantity()));
        viewModel.getTotalPrice().setValue(overAllPrice);
        foodModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, foodModelList.size());

    }

    @Override
    public int getItemCount() {
        return foodModelList == null ? 0 : foodModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Name;
        ImageView imageView_foodImage;
        TextView price_tv;
        ConstraintLayout constraintLayout;
        ElegantNumberButton elegantNumberButton;
        Button deleteItemButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_Name = itemView.findViewById(R.id.textView_Name);
            constraintLayout = itemView.findViewById(R.id.parent);
            imageView_foodImage = itemView.findViewById(R.id.food_image);
            price_tv = itemView.findViewById(R.id.textView_price);
            elegantNumberButton = itemView.findViewById(R.id.elegant_button);
            deleteItemButton = itemView.findViewById(R.id.delete_item_button);
        }
    }
}
