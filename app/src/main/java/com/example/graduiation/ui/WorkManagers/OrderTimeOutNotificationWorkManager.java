package com.example.graduiation.ui.WorkManagers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.R;
import com.example.graduiation.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class OrderTimeOutNotificationWorkManager extends Worker {
    private final String ORDER_CHANNEL_ID = "order_channel";
    private static final String TAG = "OrderTimeOutNotificatio";

    public OrderTimeOutNotificationWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Date currentTime = Calendar.getInstance().getTime();
        Log.e(TAG, "doWork: " + currentTime + "");


        String orderId = getInputData().getString("orderId");


            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .child("ordersReceived").child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String state = dataSnapshot.child("state").getValue().toString();
                    if (state.equals("1")) {

                        notifyUser();

                    }
                }

                private void notifyUser() {

                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    int notificationID = new Random().nextInt(3000);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        setupChannels(notificationManager);
                    }

                    Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.logo_circle);

                    final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("flag", "orderReceived");
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_ONE_SHOT);

                    Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), ORDER_CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo_circle)
                            .setLargeIcon(largeIcon)
                            .setContentTitle("you left some orders hanging")
                            .setContentText("orders are cancelled automatically withing 30 minutes after sending request")
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setSound(notificationSoundUri);

                    //Set notification color to match your app color template
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        notificationBuilder.setColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                    }
                    notificationManager.notify(2, notificationBuilder.build());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        return Result.success();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        CharSequence adminChannelName = "Orders";
        String adminChannelDescription = "this notification channel with broadcast only the notification that are related to orders";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ORDER_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

}
