package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupList implements Serializable {
    @SerializedName("mainGroupId")
    @Expose
    private Integer mainGroupId;
    @SerializedName("mainGroupName")
    @Expose
    private String mainGroupName;
    @SerializedName("mainGroupImage")
    @Expose
    private String mainGroupImage;

    public Integer getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(Integer mainGroupId) {
        this.mainGroupId = mainGroupId;
    }

    public String getMainGroupName() {
        return mainGroupName;
    }

    public void setMainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
    }

    public String getMainGroupImage() {
        return mainGroupImage;
    }

    public void setMainGroupImage(String mainGroupImage) {
        this.mainGroupImage = mainGroupImage;
    }
}
