package com.example.graduiation.ui.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class UserParentModel implements Parcelable {

    private String name, id, phone, image, rate, numberOfFollowing, subType, email, password , membership,bio ,token , numberOfOrders;
    private WalletModel wallet;
    private long registrationTime;
    private HashMap<String ,String> follower , following;

    public UserParentModel() {

    }

    protected UserParentModel(Parcel in) {
        name = in.readString();
        id = in.readString();
        phone = in.readString();
        image = in.readString();
        rate = in.readString();
        numberOfFollowing = in.readString();
        subType = in.readString();
        email = in.readString();
        password = in.readString();
        membership = in.readString();
        bio = in.readString();
        token = in.readString();
        numberOfOrders = in.readString();
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

    public void setFollower(HashMap<String, String> follower) {
        this.follower = follower;
    }

    public HashMap<String, String> getFollower() {
        return follower;
    }

    public void setFollowing(HashMap<String, String> following) {
        this.following = following;
    }

    public HashMap<String, String> getFollowing() {
        return following;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setNumberOfOrders(String numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public String getNumberOfOrders() {
        return numberOfOrders;
    }

    public long getRegistrationTime() {
        return registrationTime;
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

    public String getRate() {
        return rate;
    }

    public String getNumberOfFollowing() {
        return numberOfFollowing;
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

    public WalletModel getWallet() {
        return wallet;
    }

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

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setNumberOfFollowing(String numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
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

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
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
        dest.writeString(rate);
        dest.writeString(numberOfFollowing);
        dest.writeString(subType);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(membership);
        dest.writeString(bio);
        dest.writeString(token);
        dest.writeString(numberOfOrders);
        dest.writeLong(registrationTime);
    }
}
