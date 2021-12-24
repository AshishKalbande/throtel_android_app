package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressList implements Serializable {

    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("flatOrHouseNumber")
    @Expose
    private String flatOrHouseNumber;
    @SerializedName("streetOrSocietyName")
    @Expose
    private String streetOrSocietyName;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("districtNameName")
    @Expose
    private String cityName;
    @SerializedName("tahsilName")
    @Expose
    private String pincodeNumber;
    @SerializedName("addressType")
    @Expose
    private String addressType;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFlatOrHouseNumber() {
        return flatOrHouseNumber;
    }

    public void setFlatOrHouseNumber(String flatOrHouseNumber) {
        this.flatOrHouseNumber = flatOrHouseNumber;
    }

    public String getStreetOrSocietyName() {
        return streetOrSocietyName;
    }

    public void setStreetOrSocietyName(String streetOrSocietyName) {
        this.streetOrSocietyName = streetOrSocietyName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPincodeNumber() {
        return pincodeNumber;
    }

    public void setPincodeNumber(String pincodeNumber) {
        this.pincodeNumber = pincodeNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
