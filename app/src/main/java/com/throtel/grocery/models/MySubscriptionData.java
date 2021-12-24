package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MySubscriptionData {
    @SerializedName("subscriptionOrderList")
    @Expose
    private ArrayList<SubscriptionOrdersList> subscriptionOrderList = null;

    public ArrayList<SubscriptionOrdersList> getSubscriptionOrderList() {
        return subscriptionOrderList;
    }

    public void setSubscriptionOrderList(ArrayList<SubscriptionOrdersList> subscriptionOrderList) {
        this.subscriptionOrderList = subscriptionOrderList;
    }
}
