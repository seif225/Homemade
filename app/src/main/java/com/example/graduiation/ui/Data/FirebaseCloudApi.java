package com.example.graduiation.ui.Data;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseCloudApi {

            @Headers({
                "Content-Type:application/json",
                "Authorization:key=AAAAu_2Vngs:APA91bE03BZxcRZp1BjKavBDwUZvB0Gc6B0uBr5uF2ssiDFHttSdwvMRiUP2gfUKimAGrLjRIp9z1g8CxGPc0jpEIHM1V9a5bAEKqxt8zENJnYbLSfmx81jpkDQiy7nvLbFUVcwBJKMY"
            })
             @POST("send")
              Observable<PostModel> sendNotification(@Body PostModel postModel);


            }
