package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DeliveryDatesData implements Serializable {

    private final static long serialVersionUID = -8969462041389614939L;
    @SerializedName("dateList")
    @Expose
    private ArrayList<DateList> dateList = null;

    public ArrayList<DateList> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<DateList> dateList) {
        this.dateList = dateList;
    }

}