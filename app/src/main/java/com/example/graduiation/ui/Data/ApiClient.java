package com.example.graduiation.ui.Data;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL ="https://fcm.googleapis.com/fcm/";
    private FirebaseCloudApi api;
    private static ApiClient Instance;

    private ApiClient(){


        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

       api = retro.create(FirebaseCloudApi.class);
    }


    public static ApiClient getInstance() {
       if(Instance==null){
           Instance = new ApiClient();
       }
        return Instance;
    }

    public Observable<PostModel> getApi(PostModel postModel) {
        return api.sendNotification(postModel);
    }
}
