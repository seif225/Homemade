package com.example.graduiation.ui.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.UserModel;
import com.example.graduiation.ui.LegacyData.UserParentModel;
import com.example.graduiation.ui.StoryDetails.StoryDetailsActivity2;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KitchensRecyclerAdapter extends RecyclerView.Adapter<KitchensRecyclerAdapter.ViewHolder>  {
    private List<UserModel> listOfKitchens;
    private String category;
    private Integer page = 5;
    private boolean last = false;
    private static final String TAG = "KitchensRecyclerAdapter";
    private List<UserModel> exampleListFull;

    public KitchensRecyclerAdapter(ArrayList<UserModel> listOfKitchens, String category) {
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
        Log.e(TAG, "onBindViewHolder: Position " + position );




        UserModel model = listOfKitchens.get(position);

        holder.userNameTv.setText(model.getName());
       /*if (model.getImage()!=null) Picasso.get().load(model.getImage())
               .fit().networkPolicy(NetworkPolicy.OFFLINE).into(holder.userPic, new Callback() {
            @Override
            public void onSuccess() {
                if (model.getImage()!=null) Picasso.get().load(model.getImage()).into(holder.userPic);
            }

            @Override
            public void onError(Exception e) {

            }
        });

       if(model.getNumberOfFollower()!=null) holder.followTv.setText(model.getNumberOfFollower()+ " Followers") ;
        if(model.getNumberOfOrders()!=null) holder.ordersTv.setText(model.getNumberOfOrders()+ " Orders");
        if(model.getRate()>0) holder.ratingBar.setRating(Float.parseFloat(model.getRate()+""));*/
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
               /* i.putExtra("token", model.getToken());
                if(model.getToken()!=null)
                    i.putExtra("token" , model.getToken());*/
                holder.itemView.getContext().startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount()
    {
      return listOfKitchens!=null ? listOfKitchens.size():0;
    }

    public void addPages() {
        page=+5;

        //notifyDataSetChanged();
    }

    /*@Override
    public Filter getFilter() {
        return kitchenFilter;
    }*/

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

    public void setData(List<UserModel> listOfKitchens){
        this.listOfKitchens=listOfKitchens;
        exampleListFull = new ArrayList<UserModel>(listOfKitchens);

    }

   /* public long getLastItemDate() {
        return listOfKitchens.get(listOfKitchens.size()-1).getRegistrationTime();
    }*/
    public String getLastItemId() {
        return listOfKitchens.get(listOfKitchens.size()-1).getId();
    }

    public boolean isLast() {
        return last;
    }


/*

    private Filter kitchenFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserParentModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (UserParentModel item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfKitchens.clear();
            listOfKitchens.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
*/

}
