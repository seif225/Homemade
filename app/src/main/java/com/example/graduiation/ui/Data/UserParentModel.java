package com.example.graduiation.ui.Data;

public class UserParentModel {
    private String name , id , phone , image , rate , following , subType ;
    private WalletModel wallet;

    UserParentModel(){

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

    public void setPhine(String phine) {
        this.phone = phine;
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
