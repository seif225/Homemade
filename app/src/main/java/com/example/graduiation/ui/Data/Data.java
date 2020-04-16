
package com.example.graduiation.ui.Data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {


    @SerializedName("message")
    private String mMessage;
    @SerializedName("title")
    private String mTitle;


    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
