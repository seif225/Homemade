
package com.example.graduiation.ui.LegacyData;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PaymentModel {

    @SerializedName("amount")
    private int amount;
    @SerializedName("stripeToken")
    private String stripeToken ;
    @SerializedName("description")
    private String description;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStripeToken() {
        return stripeToken;
    }
}
