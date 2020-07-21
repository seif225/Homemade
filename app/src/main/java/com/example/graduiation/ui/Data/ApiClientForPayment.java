package com.example.graduiation.ui.Data;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientForPayment {
    private static final String BASE_URL ="https://payment-server123.herokuapp.com/";
    private PaymentCall api;
    private static ApiClientForPayment Instance;
    private static final Object lock = new Object();

    private ApiClientForPayment(){


        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retro.create(PaymentCall.class);
    }


    public static ApiClientForPayment getInstance() {
        if(Instance==null){
            synchronized (lock) {
                Instance = new ApiClientForPayment();
            }
        }
        return Instance;
    }

    public Observable<PaymentModel> getApi(PaymentModel paymentModel) {
        return api.paymentCall(paymentModel);
    }
}
