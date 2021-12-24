package com.throtel.grocery.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationListResponse implements Serializable {

    private final static long serialVersionUID = 5650575085170283892L;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private NotificationListData data;

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

    public NotificationListData getData() {
        return data;
    }

    public void setData(NotificationListData data) {
        this.data = data;
    }

}