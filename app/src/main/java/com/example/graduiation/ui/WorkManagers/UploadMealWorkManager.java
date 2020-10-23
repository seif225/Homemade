package com.example.graduiation.ui.WorkManagers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.graduiation.R;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.UUID;

public class UploadMealWorkManager extends Worker {
    private static final String CHANNEL_ID_ORDER_UPLOAD = "1111";
    private NotificationManagerCompat notificationManager;
    private static final String TAG = "UploadMealWorkManager";
    public UploadMealWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        createNotificationChannel();
        Log.e(TAG, "doWork: in first line of method" );
        Gson gson = new Gson();
        FoodModel model = gson.fromJson(getInputData().getString("json"), FoodModel.class);





        notificationManager = NotificationManagerCompat.from(getApplicationContext());


        final NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_ORDER_UPLOAD)
                .setSmallIcon(R.drawable.logo_circle)
                .setContentTitle("Upload")
                .setContentText("Upload in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, false);

        notificationManager.notify(100, notification.build());



        final String imageName = UUID.randomUUID().toString() + ".jpg";

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child("Food").child(model.getCookId()).child(model.getId()).child(imageName).putFile(Uri.parse(model.getThumbnail()))
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        notification.setProgress(1, progress, false);
                        notification.setContentText("Upload in progress " + progress + "%");
                        notificationManager.notify(100, notification.build());

                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                               if (task.isSuccessful()) {
                                                   // path = task.getResult().getStorage().getPath();
                                                   notification.setContentText("Upload finished")
                                                           .setProgress(0, 0, false)
                                                           .setOngoing(false);
                                                   notificationManager.notify(100, notification.build());
                                               } else {
                                                   Log.d("Failure:", "upload Failed " + task.getException().getLocalizedMessage());
                                                   Toast.makeText(getApplicationContext(), "something went wrong :( , try again", Toast.LENGTH_SHORT).show();

                                                   notification.setContentText("upload failed")
                                                           .setProgress(0, 0, false)
                                                           .setOngoing(false);
                                                   notificationManager.notify(100, notification.build());
                                                   Toast.makeText(getApplicationContext(), "Uplaod Failed :( " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                               }
                                           }
                                       }

                )
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child("Food").child(model.getCookId()).child(model.getId()).child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Log.e("PhotoUploadFirebaseQueryHelper", uri + "");
                                String link = uri.toString();
                                model.setThumbnail(link);
                                FirebaseQueryHelperRepository.getInstance().uploadFoodDataToRealTimeDataBase(model, getApplicationContext());
                            }
                        });
                    }
                });


        return Result.success();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID_ORDER_UPLOAD,
                    "Upload pic",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
