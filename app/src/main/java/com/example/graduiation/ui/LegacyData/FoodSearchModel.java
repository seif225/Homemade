package com.example.graduiation.ui.LegacyData;

public class FoodSearchModel {
    private String kitchenName , mealName , mealImage , price , id ,mealId;

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public String getMealName() {
        return mealName;
    }


}
