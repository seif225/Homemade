package com.example.graduiation.ui.Adapters;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduiation.R;
import com.example.graduiation.ui.Animators.Fx;
import com.example.graduiation.ui.Data.FoodModel;
import com.example.graduiation.ui.Data.OrderModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.graphics.BlendMode.COLOR;

public class CurrentOrdersRecyclerAddapter extends RecyclerView.Adapter<CurrentOrdersRecyclerAddapter.ViewHolder> {

    private ArrayList<OrderModel> listOfAcceptedOrders;
    private static final String TAG = "AcceptedOrdersRecyclerA";

    public CurrentOrdersRecyclerAddapter(ArrayList<OrderModel> listOfAcceptedOrders) {
        this.listOfAcceptedOrders = listOfAcceptedOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_list_item, parent
                , false);
        return new ViewHolder(view);
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

      //  if(holder.countDownTimer!=null) holder.countDownTimer.cancel();

    }





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderModel orderModel = listOfAcceptedOrders.get(position);

        long postTime = orderModel.getLastActionTime();
        Log.e(TAG, "onBindViewHolder: currentSystemMillies " + System.currentTimeMillis());
        Log.e(TAG, "onBindViewHolder: post time : " + postTime);



        holder.orderId_tv.setText(orderModel.getOrderId());
        holder.status_tv.setText(orderModel.getState());
        holder.userId_tv.setText("Seller ID: "+orderModel.getCookId());


        StringBuilder sb = new StringBuilder();
        for (FoodModel s : orderModel.getListOfFood()) {
            sb.append("-").append(s.getQuantity()).append(" X ").append(s.getTitle()).append("\n");
        }

        long maxOrderDuration=0 ;


        for (FoodModel foodModel: listOfAcceptedOrders.get(position).getListOfFood()) {

            if(foodModel.getPreparingTime()>0){
                if(foodModel.getPreparingTime()>maxOrderDuration)
                    maxOrderDuration=foodModel.getPreparingTime();
            }

        }

        if(maxOrderDuration==0){
            //a full day
            maxOrderDuration = 24*60*60*1000;

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

          //  holder.status_tv.setText(orderModel.getState()+"");

        if(orderModel.getState().equals("1")){
            holder.status_tv.setText("Hanging");
        }
        else if (orderModel.getState().equals("2")){
            holder.status_tv.setText("Declined");

        }
        else if (orderModel.getState().equals("3")){
            holder.status_tv.setText("");

        }
        else if (orderModel.getState().equals("4")){
            holder.status_tv.setText("Preparing");

        }
        else if (orderModel.getState().equals("5")){
            holder.status_tv.setText("Preparing");

        }
        else if (orderModel.getState().equals("6")){
            holder.status_tv.setText("On Delivery");

        }
        else if (orderModel.getState().equals("7")){
            holder.status_tv.setText("Delivered");

        }
    }

    @Override
    public int getItemCount() {
        return listOfAcceptedOrders == null ? 0 : listOfAcceptedOrders.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timer_tv, orderId_tv, status_tv, order_details ,userId_tv;
        Button expand_tv;
        CircleImageView statusIndicator;
        View expandable_constraint;
        CountDownTimer countDownTimer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timer_tv = itemView.findViewById(R.id.timer);
            orderId_tv = itemView.findViewById(R.id.order_id);
            status_tv = itemView.findViewById(R.id.status_tv);
            expand_tv = itemView.findViewById(R.id.expand_tv);
            order_details = itemView.findViewById(R.id.order_details);
            statusIndicator = itemView.findViewById(R.id.status_label);
            expandable_constraint = itemView.findViewById(R.id.expandable_constraint);
            userId_tv = itemView.findViewById(R.id.user_id);



        }


        public CountDownTimer getCountDownTimer(long remainingTime) {
            countDownTimer = new CountDownTimer(remainingTime, 1000) {

                public void onTick(long millisUntilFinished) {
                    long remainingSecs = millisUntilFinished / 1000;
                    long hours = remainingSecs / 60 / 60;
                    long minutes = (remainingSecs / 60) - (hours * 60);
                    long seconds = remainingSecs - (minutes * 60) - (hours * 60 * 60);
                    timer_tv.setText(hours + " : " + minutes + " : " + seconds);
                    Log.e(TAG, "onTick: " + "im still ticking my friend :D  : " + millisUntilFinished);
                }

                public void onFinish() {
                    // removeItem(position);
                    timer_tv.setText("OVERDUE");
                    timer_tv.setTextColor(Color.RED);
                }


            };
            return countDownTimer;

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

        listOfAcceptedOrders.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listOfAcceptedOrders.size());

    }

}
