package com.example.graduiation.ui.Data;

public class UserParentModel {
    private String name, id, phone, image, rate, following, subType, email, password , membership,bio;
    private WalletModel wallet;

    UserParentModel() {

    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio() {
        return bio;
    }
    public void setMembership(String membership) {
        this.membership = membership;
    }
    public String getMembership() {
        return membership;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
    }

    public String getPhone() {
        return phone;
    }

    public String getSubType() {
        return subType;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPhine() {
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
}
