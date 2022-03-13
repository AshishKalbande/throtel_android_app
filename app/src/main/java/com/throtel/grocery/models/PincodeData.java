package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

public class PincodeData {
    @SerializedName("tahsilList")
    @Expose
    private ArrayList<com.throtel.grocery.models.PincodeList> pincodeList = null;

    public Collection<? extends com.throtel.grocery.models.PincodeList> getPincodeList() {
        return pincodeList;
    }

    public void setPincodeList(ArrayList<PincodeList> pincodeList) {
        this.pincodeList = pincodeList;
    }


}
