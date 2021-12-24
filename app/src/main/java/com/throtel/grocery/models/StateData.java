package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StateData {
    @SerializedName("stateList")
    @Expose
    private ArrayList<StateList> stateList = null;

    public ArrayList<StateList> getStateList() {
        return stateList;
    }

    public void setStateList(ArrayList<StateList> stateList) {
        this.stateList = stateList;
    }
}
