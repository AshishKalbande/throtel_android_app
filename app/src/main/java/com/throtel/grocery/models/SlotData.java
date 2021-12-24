package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SlotData {
    @SerializedName("slotList")
    @Expose
    private ArrayList<SlotList> slotList = null;

    public ArrayList<SlotList> getSlotList() {
        return slotList;
    }

    public void setSlotList(ArrayList<SlotList> slotList) {
        this.slotList = slotList;
    }
}
