package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryChargeDetail implements Serializable {

    @SerializedName("pincodeId")
    @Expose
    private Integer pincodeId;
    @SerializedName("pincodeNumber")
    @Expose
    private String pincodeNumber;
    @SerializedName("maxPrice")
    @Expose
    private Double maxPrice;
    @SerializedName("deliveryPrice")
    @Expose
    private Double deliveryPrice;

    public Integer getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(Integer pincodeId) {
        this.pincodeId = pincodeId;
    }

    public String getPincodeNumber() {
        return pincodeNumber;
    }

    public void setPincodeNumber(String pincodeNumber) {
        this.pincodeNumber = pincodeNumber;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
