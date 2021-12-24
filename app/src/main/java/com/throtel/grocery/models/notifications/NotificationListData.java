package com.throtel.grocery.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationListData implements Serializable {

    private final static long serialVersionUID = -5280407218479473800L;
    @SerializedName("notificationList")
    @Expose
    private ArrayList<NotificationList> notificationList = null;

    public ArrayList<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<NotificationList> notificationList) {
        this.notificationList = notificationList;
    }

}