package com.example.graduiation.ui;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.PaymentConfiguration;

public class MyApplication extends Application {

    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mContext = this;

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51H70zFFDq9nTOUxSY2ddNZdgHAkbdYI3sCD3IEyCOiQVH10Cenhl1l1WznGqtvGBOMltt7wu3meQavBmp15mt0pk00qVRo9F0r"
        );

    }

    public static MyApplication getContext() {
        return mContext;
    }
}
