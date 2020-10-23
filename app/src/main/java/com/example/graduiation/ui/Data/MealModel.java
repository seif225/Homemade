
package com.example.graduiation.ui.Data;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class MealModel {
    @NonNull
    @SerializedName("category")
    private String mCategory;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @NonNull
    @SerializedName("description")
    private String mDescription;

    @SerializedName("id")
    private String mId;
    @NonNull
    @SerializedName("max")
    private Long mMax;
    @NonNull
    @SerializedName("min")
    private Long mMin;
    @SerializedName("orderCount")
    private Long mOrderCount;
    @NonNull
    @SerializedName("postTime")
    private Long mPostTime;
    @NonNull
    @SerializedName("prepareTime")
    private Long mPrepareTime;
    @NonNull
    @SerializedName("price")
    private Long mPrice;
    @NonNull
    @SerializedName("quantity")
    private Long mQuantity;
    @NonNull
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @NonNull
    @SerializedName("UserId")
    private String mUserId;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getMax() {
        return mMax;
    }

    public void setMax(Long max) {
        mMax = max;
    }

    public Long getMin() {
        return mMin;
    }

    public void setMin(Long min) {
        mMin = min;
    }

    public Long getOrderCount() {
        return mOrderCount;
    }

    public void setOrderCount(Long orderCount) {
        mOrderCount = orderCount;
    }

    public Long getPostTime() {
        return mPostTime;
    }

    public void setPostTime(Long postTime) {
        mPostTime = postTime;
    }

    public Long getPrepareTime() {
        return mPrepareTime;
    }

    public void setPrepareTime(Long prepareTime) {
        mPrepareTime = prepareTime;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    public Long getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Long quantity) {
        mQuantity = quantity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
