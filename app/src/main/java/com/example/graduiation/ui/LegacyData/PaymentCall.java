package com.example.graduiation.ui.LegacyData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PaymentCall {

    @Headers("Content-Type: application/json")
    @POST("charge")
     Observable<PaymentModel> paymentCall(@Body PaymentModel PaymentModel);
}
