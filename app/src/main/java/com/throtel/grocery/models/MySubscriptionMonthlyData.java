package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MySubscriptionMonthlyData {

    @SerializedName("subscriptionOrderList")
    @Expose
    private ArrayList<SubscriptionMonthlyOrderList> subscriptionOrderList = null;

    public ArrayList<SubscriptionMonthlyOrderList> getSubscriptionOrderList() {
        return subscriptionOrderList;
    }

    public void setSubscriptionOrderList(ArrayList<SubscriptionMonthlyOrderList> subscriptionOrderList) {
        this.subscriptionOrderList = subscriptionOrderList;
    }
}
