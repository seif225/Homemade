
package com.example.graduiation.ui.Data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostModel {

    @SerializedName("data")
    private Data mData;
    @SerializedName("to")
    private String mTo;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        mTo = to;
    }

}
