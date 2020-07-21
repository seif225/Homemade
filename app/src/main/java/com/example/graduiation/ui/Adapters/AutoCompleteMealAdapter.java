package com.example.graduiation.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.FoodSearchModel;
import com.example.graduiation.ui.Meal.MealFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteMealAdapter extends ArrayAdapter<FoodSearchModel> {
private List<FoodSearchModel> countryListFull;
    private static final String TAG = "AutoCompleteMealAdapter";
    public AutoCompleteMealAdapter(@NonNull Context context, @NonNull List<FoodSearchModel> countryList) {
        super(context, 0, countryList);
        countryListFull = new ArrayList<>(countryList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.meal_search_row, parent, false
            );
        }
        TextView mealNameTv = convertView.findViewById(R.id.meal_name_tv);
        TextView kitchenNameTv = convertView.findViewById(R.id.kitchen_name_tv);
        TextView priceTextView = convertView.findViewById(R.id.price_search_tv);
        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        FoodSearchModel countryItem = getItem(position);
        if (countryItem != null) {
            mealNameTv.setText(countryItem.getMealName());
            if(countryItem.getKitchenName()!=null)kitchenNameTv.setText(countryItem.getKitchenName());

            if(countryItem.getMealImage()!=null)Picasso.get().load(countryItem.getMealImage()).centerCrop().resize(100,100).into(imageViewFlag);
            priceTextView.setText(countryItem.getPrice() + " EGP");

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("Food").child(countryItem.getId()).child(countryItem.getMealId())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);
                                    Log.e(TAG, "SearchingForFoodModel " + foodModel.getDescribtion() );
                                    Intent i = new Intent(getContext(), MealFragment.class);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(foodModel);
                                    i.putExtra("meal",json);
                                    getContext().startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                }
            });

        }
        return convertView;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FoodSearchModel> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FoodSearchModel item : countryListFull) {
                    if (item.getMealName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((FoodSearchModel) resultValue).getMealName();
        }
    };
}