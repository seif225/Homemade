package com.example.graduiation.ui.Meal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.MealModel;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<MealModel> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(List<MealModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(MealModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider_layout_item, parent
                , false);
        return new SliderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        MealModel sliderItem = mSliderItems.get(0);

        if(position>0){

        viewHolder.textViewDescription.setText(sliderItem.getTitle());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        /*Picasso.get().load(sliderItem.getThumbnail())
                .fit()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(viewHolder.imageViewBackground, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(sliderItem.getThumbnail()).fit().into(viewHolder.imageViewBackground);
                    }
                });*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });}
        else {

            viewHolder.textViewDescription.setText(sliderItem.getTitle());
            viewHolder.textViewDescription.setTextSize(16);
            viewHolder.textViewDescription.setTextColor(Color.WHITE);
            Picasso.get().load("https://image.shutterstock.com/image-photo/healthy-food-clean-eating-selection-600w-722718082.jpg")
                   .fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.imageViewBackground, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load("https://image.shutterstock.com/image-photo/healthy-food-clean-eating-selection-600w-722718082.jpg").fit().into(viewHolder.imageViewBackground);
                        }
                    });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size()+1;
    }

    public void addItem(String thumbnail, String title) {
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {


        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);

        }
    }

}
