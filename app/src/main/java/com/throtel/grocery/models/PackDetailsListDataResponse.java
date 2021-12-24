package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackDetailsListDataResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private PackDetailsData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PackDetailsData getData() {
        return data;
    }

    public void setData(PackDetailsData data) {
        this.data = data;
    }

}
