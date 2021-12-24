package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionOrderData {
    @SerializedName("subscriptionOrderDetail")
    @Expose
    private SubscriptionOrderDetail subscriptionOrderDetail;

    public SubscriptionOrderDetail getSubscriptionOrderDetail() {
        return subscriptionOrderDetail;
    }

    public void setSubscriptionOrderDetail(SubscriptionOrderDetail subscriptionOrderDetail) {
        this.subscriptionOrderDetail = subscriptionOrderDetail;
    }

}
