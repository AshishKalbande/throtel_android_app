package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryChargeData {
    @SerializedName("deliveryChargeDetail")
    @Expose
    private DeliveryChargeDetail deliveryChargeDetail;

    public DeliveryChargeDetail getDeliveryChargeDetail() {
        return deliveryChargeDetail;
    }

    public void setDeliveryChargeDetail(DeliveryChargeDetail deliveryChargeDetail) {
        this.deliveryChargeDetail = deliveryChargeDetail;
    }
}
