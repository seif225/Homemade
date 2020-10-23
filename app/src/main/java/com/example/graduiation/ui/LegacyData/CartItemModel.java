package com.example.graduiation.ui.LegacyData;

import java.util.ArrayList;

public class CartItemModel {
    private String CookId;
    private ArrayList<FoodModel> listOfFood;

    public void setCookId(String cookId) {
        CookId = cookId;
    }

    public void setListOfFood(ArrayList<FoodModel> listOfFood) {
        this.listOfFood = listOfFood;
    }

    public ArrayList<FoodModel> getListOfFood() {
        return listOfFood;
    }

    public String getCookId() {
        return CookId;
    }
}
