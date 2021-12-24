package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnProductListDataResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ReturnProductListData data;

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

    public ReturnProductListData getData() {
        return data;
    }

    public void setData(ReturnProductListData data) {
        this.data = data;
    }
}
