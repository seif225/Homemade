package com.example.graduiation.ui.Services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.WorkManagers.OrderTimeOutNotificationWorkManager;
import com.example.graduiation.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {




    private static final String TAG = "MyFirebaseMessagingServ";
    private final String ADMIN_CHANNEL_ID = "admin_channel";
    private final String ORDER_CODE = "order";
    private final String ORDER_CHANNEL_ID = "order_channel";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("flag" , "orderReceived");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationID = new Random().nextInt(3000);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                setupChannels(notificationManager);
            }


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.logo_circle);
            Log.e(TAG, "onMessageReceived: NotificationType" + remoteMessage.getData().get("key1"));

            if (remoteMessage.getData().get("key1") != null) {

                if (remoteMessage.getData().get("key1").equals(ORDER_CODE)) {

                    long orderTime = Long.parseLong(remoteMessage.getData().get("key2"));
                    String orderId= remoteMessage.getData().get("key3");
                    if (orderTime + 1800000 > System.currentTimeMillis()&&orderId!=null) {

                        //Create oneTime workManager request to notify the user
                        // that he still has time to make the order before timeout
                        // using workManager

                        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ORDER_CHANNEL_ID)
                                .setSmallIcon(R.drawable.logo_circle)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(remoteMessage.getData().get("title"))
                                .setContentText(remoteMessage.getData().get("message"))
                                .setAutoCancel(true)
                                .setSound(notificationSoundUri)
                                .setContentIntent(pendingIntent);

                        //Set notification color to match your app color template
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                        notificationManager.notify(notificationID, notificationBuilder.build());

                        Log.e(TAG, "onMessageReceived: " + " From Service to work manager" );
                        Data data = new Data.Builder()
                                .putString("orderId", orderId)
                                .build();
                        OneTimeWorkRequest myWork =
                                new OneTimeWorkRequest.Builder(OrderTimeOutNotificationWorkManager.class)
                                        .setInputData(data).setInitialDelay( 15, TimeUnit.MINUTES).build();

                        WorkManager.getInstance(this).enqueue(myWork);

                    }

                } else {

                    Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo_circle)
                            .setLargeIcon(largeIcon)
                            .setContentTitle(remoteMessage.getData().get("title"))
                            .setContentText(remoteMessage.getData().get("message"))
                            .setAutoCancel(true)
                            .setSound(notificationSoundUri)
                            .setContentIntent(pendingIntent);

                    //Set notification color to match your app color template
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                    notificationManager.notify(1, notificationBuilder.build());


                }


            }


        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        CharSequence adminChannelName = "Orders";
        String adminChannelDescription = "this notification channel with broadcast only the notification that are related to orders";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)

        {
            String userId = FirebaseAuth.getInstance().getUid();
            FirebaseQueryHelperRepository.getInstance().updateUserToken(userId,s);
            FirebaseQueryHelperRepository.getInstance().UploadNewTokenToUsersFoodList(userId ,s);

            SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", s);



        }




    }



}
