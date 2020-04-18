package com.example.graduiation.ui.Data;

public class UserParentModel {

    private String name, id, phone, image, rate, following, subType, email, password , membership,bio ,token;
    private WalletModel wallet;

    public UserParentModel() {

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

    public String getFollowing() {
        return following;
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

    public void setFollowing(String following) {
        this.following = following;
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
}
