package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryOfferList implements Serializable {

    private final static long serialVersionUID = -925009904110312076L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("mainGroupId")
    @Expose
    private int mainGroupId;
    @SerializedName("offerName")
    @Expose
    private String offerName;
    @SerializedName("offerAmountOrPercentage")
    @Expose
    private double offerAmountOrPercentage;
    @SerializedName("offerBy")
    @Expose
    private String offerBy;
    @SerializedName("offerImage")
    @Expose
    private String offerImage;
    @SerializedName("offerType")
    @Expose
    private String offerType;
    @SerializedName("offerLimit")
    @Expose
    private String offerLimit;
    @SerializedName("mainGroupName")
    @Expose
    private String mainGroupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(int mainGroupId) {
        this.mainGroupId = mainGroupId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public double getOfferAmountOrPercentage() {
        return offerAmountOrPercentage;
    }

    public void setOfferAmountOrPercentage(double offerAmountOrPercentage) {
        this.offerAmountOrPercentage = offerAmountOrPercentage;
    }

    public String getOfferBy() {
        return offerBy;
    }

    public void setOfferBy(String offerBy) {
        this.offerBy = offerBy;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOfferLimit() {
        return offerLimit;
    }

    public void setOfferLimit(String offerLimit) {
        this.offerLimit = offerLimit;
    }

    public String getMainGroupName() {
        return mainGroupName;
    }

    public void setMainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
    }

}