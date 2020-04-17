package com.example.graduiation.ui.Adapters;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Animators.Fx;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersRecieivedRecyclerAdapter extends RecyclerView.Adapter<OrdersRecieivedRecyclerAdapter.ViewHolder> {

    ArrayList<OrderModel> listOfOrders;
    private static final String TAG = "OrdersRecieivedRecycler";

    public OrdersRecieivedRecyclerAdapter(ArrayList<OrderModel> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_order_item, parent
                , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderModel orderModel = listOfOrders.get(position);

        long postTime = orderModel.getOrderPostTimeInUnix();
        Log.e(TAG, "onBindViewHolder: currentSystemMillies " + System.currentTimeMillis());
        Log.e(TAG, "onBindViewHolder: post time : " + postTime);
        //TODO : i will remove the outimed view temporarily , however , i will not query them at all later


            holder.orderId_tv.setText(orderModel.getOrderId());
            holder.status_tv.setText(orderModel.getState());
            StringBuilder sb = new StringBuilder();
            for (FoodModel s : orderModel.getListOfFood()) {
                sb.append("-").append(s.getQuantity()).append(" X ").append(s.getTitle()).append("\n");
            }
            holder.order_details.setText(sb.toString());
            holder.expandable_constraint.setVisibility(View.GONE);

            holder.expand_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle_contents(holder.expandable_constraint, holder.expand_tv);
                }
            });

        long remainingTime = (postTime+1800000 )- System.currentTimeMillis();
        new CountDownTimer(remainingTime, 1000) {

            public void onTick(long millisUntilFinished) {


                long remainingSecs = millisUntilFinished/1000;
                long minutes = remainingSecs /60;
                long seconds = remainingSecs -(minutes*60);
                holder.timer_tv.setText(minutes+" : " + seconds);

            }

            public void onFinish() {
              removeItem(position);
            }
        }.start();




    }

    @Override
    public int getItemCount() {
        return listOfOrders == null ? 0 : listOfOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timer_tv, orderId_tv, status_tv, order_details;
        Button expand_tv;
        CircleImageView statusIndicator;
        View expandable_constraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timer_tv = itemView.findViewById(R.id.timer);
            orderId_tv = itemView.findViewById(R.id.order_id);
            status_tv = itemView.findViewById(R.id.status_tv);
            expand_tv = itemView.findViewById(R.id.expand_tv);
            order_details = itemView.findViewById(R.id.order_details);
            statusIndicator = itemView.findViewById(R.id.status_label);
            expandable_constraint = itemView.findViewById(R.id.expandable_constraint);

        }
    }

    public void toggle_contents(View v, Button order_details) {

        if (v.isShown()) {
            Fx.collapse(v);
            order_details.setText("expand");
        } else {
            Fx.expand(v);
            order_details.setText("collapse");

        }


    }

    private void removeItem(int position) {

        listOfOrders.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listOfOrders.size());

    }
}
