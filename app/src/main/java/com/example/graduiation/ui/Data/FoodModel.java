package com.example.graduiation.ui.Data;

import java.util.HashMap;

public class FoodModel {
    private String title , describtion ,  id , category , cookId , price , orderCount , thumbnail ;
    private String min , max ;
    private HashMap<Object , Object> reviewsMap , picsMap , ratingMap;

    FoodModel(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setReviewsMap(HashMap<Object, Object> reviewsMap) {
        this.reviewsMap = reviewsMap;
    }

    public void setPicsMap(HashMap<Object, Object> picsMap) {
        this.picsMap = picsMap;
    }

    public void setRatingMap(HashMap<Object, Object> ratingMap) {
        this.ratingMap = ratingMap;
    }

    public String getTitle() {
        return title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCookId() {
        return cookId;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public HashMap<Object, Object> getReviewsMap() {
        return reviewsMap;
    }

    public HashMap<Object, Object> getPicsMap() {
        return picsMap;
    }

    public HashMap<Object, Object> getRatingMap() {
        return ratingMap;
    }
}
