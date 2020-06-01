package com.example.graduiation.ui.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class FoodModel implements Parcelable {
    private String title, describtion, id, category, cookId, price, orderCount, thumbnail, quantity, cookToken, overAllRating;
    private String min, max;
    private long PreparingTime, postTime;
    private HashMap<String, Object> reviewsMap, picsMap, rateMap;

    public FoodModel() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCookToken(String cookToken) {
        this.cookToken = cookToken;
    }

    public void setOverAllRating(String overAllRating) {
        this.overAllRating = overAllRating;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setPreparingTime(long preparingTime) {
        PreparingTime = preparingTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public void setReviewsMap(HashMap<String, Object> reviewsMap) {
        this.reviewsMap = reviewsMap;
    }

    public void setPicsMap(HashMap<String, Object> picsMap) {
        this.picsMap = picsMap;
    }


    public String getTitle() {
        return title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCookId() {
        return cookId;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCookToken() {
        return cookToken;
    }

    public String getOverAllRating() {
        return overAllRating;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public long getPreparingTime() {
        return PreparingTime;
    }

    public long getPostTime() {
        return postTime;
    }

    public HashMap<String, Object> getReviewsMap() {
        return reviewsMap;
    }

    public HashMap<String, Object> getPicsMap() {
        return picsMap;
    }

    public void setRateMap(HashMap<String, Object> rateMap) {
        this.rateMap = rateMap;
    }

    public HashMap<String, Object> getRateMap() {
        return rateMap;
    }

    public static Creator<FoodModel> getCREATOR() {
        return CREATOR;
    }

    protected FoodModel(Parcel in) {
        title = in.readString();
        describtion = in.readString();
        id = in.readString();
        category = in.readString();
        cookId = in.readString();
        price = in.readString();
        orderCount = in.readString();
        thumbnail = in.readString();
        quantity = in.readString();
        cookToken = in.readString();
        overAllRating = in.readString();
        min = in.readString();
        max = in.readString();
        PreparingTime = in.readLong();
        postTime = in.readLong();
    }

    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(describtion);
        dest.writeString(id);
        dest.writeString(category);
        dest.writeString(cookId);
        dest.writeString(price);
        dest.writeString(orderCount);
        dest.writeString(thumbnail);
        dest.writeString(quantity);
        dest.writeString(cookToken);
        dest.writeString(overAllRating);
        dest.writeString(min);
        dest.writeString(max);
        dest.writeLong(PreparingTime);
        dest.writeLong(postTime);
    }
}