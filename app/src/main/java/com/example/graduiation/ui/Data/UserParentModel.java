package com.example.graduiation.ui.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class UserParentModel implements Parcelable {

    private String name, id, phone,
            image, numberOfFollowing,numberOfFollower, subType,
            email, password , membership,bio ,token , numberOfOrders;

    private long dueDate;
    private WalletModel wallet;
    private float rate;
    private long registrationTime;
    private HashMap<String ,String> follower , following;


    public UserParentModel(){}


    protected UserParentModel(Parcel in) {
        name = in.readString();
        id = in.readString();
        phone = in.readString();
        image = in.readString();
        numberOfFollowing = in.readString();
        numberOfFollower = in.readString();
        subType = in.readString();
        email = in.readString();
        password = in.readString();
        membership = in.readString();
        bio = in.readString();
        token = in.readString();
        numberOfOrders = in.readString();
        dueDate = in.readLong();
        rate = in.readFloat();
        registrationTime = in.readLong();
    }

    public static final Creator<UserParentModel> CREATOR = new Creator<UserParentModel>() {
        @Override
        public UserParentModel createFromParcel(Parcel in) {
            return new UserParentModel(in);
        }

        @Override
        public UserParentModel[] newArray(int size) {
            return new UserParentModel[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNumberOfFollowing(String numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
    }

    public void setNumberOfFollower(String numberOfFollower) {
        this.numberOfFollower = numberOfFollower;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNumberOfOrders(String numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setFollower(HashMap<String, String> follower) {
        this.follower = follower;
    }

    public void setFollowing(HashMap<String, String> following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getNumberOfFollowing() {
        return numberOfFollowing;
    }

    public String getNumberOfFollower() {
        return numberOfFollower;
    }

    public String getSubType() {
        return subType;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMembership() {
        return membership;
    }

    public String getBio() {
        return bio;
    }

    public String getToken() {
        return token;
    }

    public String getNumberOfOrders() {
        return numberOfOrders;
    }

    public long getDueDate() {
        return dueDate;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public float getRate() {
        return rate;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }

    public HashMap<String, String> getFollower() {
        return follower;
    }

    public HashMap<String, String> getFollowing() {
        return following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(phone);
        dest.writeString(image);
        dest.writeString(numberOfFollowing);
        dest.writeString(numberOfFollower);
        dest.writeString(subType);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(membership);
        dest.writeString(bio);
        dest.writeString(token);
        dest.writeString(numberOfOrders);
        dest.writeLong(dueDate);
        dest.writeFloat(rate);
        dest.writeLong(registrationTime);
    }
}
