
package com.example.graduiation.ui.Data;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserModel {

    @SerializedName("bio")
    private Object mBio;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("Meals")
    private List<MealModel> mMeals;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("subType")
    private Long mSubType;
    @SerializedName("tokens")
    private String mTokens;
    @SerializedName("updatedAt")
    private String mUpdatedAt;

    public Object getBio() {
        return mBio;
    }

    public void setBio(Object bio) {
        mBio = bio;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<MealModel> getMeals() {
        return mMeals;
    }

    public void setMeals(List<MealModel> meals) {
        mMeals = meals;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Long getSubType() {
        return mSubType;
    }

    public void setSubType(Long subType) {
        mSubType = subType;
    }

    public String getTokens() {
        return mTokens;
    }

    public void setTokens(String tokens) {
        mTokens = tokens;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
