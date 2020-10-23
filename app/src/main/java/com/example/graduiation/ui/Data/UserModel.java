
package com.example.graduiation.ui.Data;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserModel {

    @SerializedName("bio")
    private Object mBio;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("subType")
    private Long mSubType;
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

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
