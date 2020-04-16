package com.example.graduiation.ui.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel  {
    private String state, address, orderId , paymentMethod , cookId ,cookToken ,totalPrice;
    private ArrayList<FoodModel> listOfFood;
    private long durationInUnix, orderPostTimeInUnix;
    private LatLng userCoordinates;





    public OrderModel()
    {

    }


    public void setCookToken(String cookToken) {
        this.cookToken = cookToken;
    }

    public String getCookToken() {
        return cookToken;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public String getCookId() {
        return cookId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserCoordinates(LatLng userCoordinates) {
        this.userCoordinates = userCoordinates;
    }

    public LatLng getUserCoordinates() {
        return userCoordinates;
    }

    public String getOrderId() {
        return orderId;
    }


    public void setDurationInUnix(long durationInUnix) {
        this.durationInUnix = durationInUnix;
    }

    public void setListOfFood(ArrayList<FoodModel> listOfFood) {
        this.listOfFood = listOfFood;
    }

    public void setOrderPostTimeInUnix(long orderPostTimeInUnix) {
        this.orderPostTimeInUnix = orderPostTimeInUnix;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<FoodModel> getListOfFood() {
        return listOfFood;
    }

    public long getDurationInUnix() {
        return durationInUnix;
    }

    public long getOrderPostTimeInUnix() {
        return orderPostTimeInUnix;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }


}
