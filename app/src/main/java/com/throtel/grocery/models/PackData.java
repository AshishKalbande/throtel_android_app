package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PackData {

    @SerializedName("packList")
    @Expose
    private ArrayList<PackList> packList = null;

    public ArrayList<PackList> getPackList() {
        return packList;
    }

    public void setPackList(ArrayList<PackList> packList) {
        this.packList = packList;
    }
}
