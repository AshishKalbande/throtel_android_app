package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReasonData {
    @SerializedName("reasonList")
    @Expose
    private ArrayList<ReasonList> reasonList = null;

    public ArrayList<ReasonList> getReasonList() {
        return reasonList;
    }

    public void setReasonList(ArrayList<ReasonList> reasonList) {
        this.reasonList = reasonList;
    }
}
