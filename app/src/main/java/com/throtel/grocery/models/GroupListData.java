package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupListData {
    @SerializedName("groupList")
    @Expose
    private ArrayList<GroupList> groupList = null;

    public ArrayList<GroupList> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<GroupList> groupList) {
        this.groupList = groupList;
    }

}
