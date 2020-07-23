package com.example.graduiation.ui.SubPayment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.graduiation.R;
import com.example.graduiation.ui.Data.ApiClient;
import com.example.graduiation.ui.Data.ApiClientForPayment;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.PaymentModel;
import com.example.graduiation.ui.Data.PostModel;
import com.example.graduiation.ui.main.MainActivity;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.hbb20.CountryCodePicker;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import butterknife.internal.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubPaymentActivity extends AppCompatActivity {

    private Stripe stripe;
    private static final String TAG = "SubPaymentActivity";
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    int price=0;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_payment);
        // Hook up the pay button to the card widget and Stripe instance
        Button payButton = findViewById(R.id.payButton);
        WeakReference<SubPaymentActivity> weakActivity = new WeakReference<>(this);

        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioGroupOption = radioGroup.getCheckedRadioButtonId();
                if(radioGroupOption==-1) payButton.setVisibility(View.GONE);
                else {
                    if(radioGroupOption==R.id.option1) price = 250;
                    else if(radioGroupOption==R.id.option2) price = 650;
                    else if(radioGroupOption==R.id.option3) price = 1200;
                    else if(radioGroupOption==R.id.option4) price = 2100;
                   // else if (radioGroupOption==-1) payButton.setVisibility();


                }

            }
        });



                payButton.setOnClickListener((View view) -> {
                    ProgressDialog pd = new ProgressDialog(view.getContext());
                    pd.setTitle("your transaction is processing");
                    pd.setMessage("this will take a few seconds ...");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    // Get the card details from the card widget
                    Card card = cardInputWidget.getCard();
                    if (card != null) {
                        // Create a Stripe token from the card details

                        stripe = new Stripe(getApplicationContext(),
                                PaymentConfiguration.getInstance(getApplicationContext()).
                                        getPublishableKey());
                        stripe.createToken(card, new ApiResultCallback<Token>() {
                            @Override
                            public void onSuccess(@NonNull Token result) {
                                String tokenID = result.getId();
                                Log.e(TAG, "onSuccess: TokenId" + tokenID );
                                // Send the token identifier to the server...
                                PaymentModel paymentModel = new PaymentModel();
                                paymentModel.setAmount(price);
                                paymentModel.setDescription("this is a describtion from android studio for testing");

                                paymentModel.setStripeToken(tokenID);



                                Observable<PaymentModel> observable = ApiClientForPayment.getInstance().getApi(paymentModel).subscribeOn(Schedulers.computation());
                                Observer<PaymentModel> observer = new Observer<PaymentModel>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(PaymentModel paymentModel1) {

                                        Log.e(TAG, "onNext:" );

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "onError: "+e.getMessage() );
                                        pd.dismiss();
                                        flag = 0;

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.e(TAG, "onComplete: done" );
                                        flag = 1 ;
                                        FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();
                                        repo.updateSubscribion(price,getApplicationContext());
                                        pd.dismiss();


                                    }
                                };


                                observable.subscribe(observer);
                                Toast.makeText(SubPaymentActivity.this, "successful payment", Toast.LENGTH_LONG).show();

                            }


                            @Override
                            public void onError(@NonNull Exception e) {
                                // Handle error
                                Toast.makeText(SubPaymentActivity.this, "something went wrong , please try again later", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
            }


}