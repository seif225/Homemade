package com.example.graduiation.ui.SubPayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.graduiation.R;
import com.example.graduiation.ui.LegacyData.ApiClientForPayment;
import com.example.graduiation.ui.LegacyData.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.LegacyData.PaymentModel;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
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