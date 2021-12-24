package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryData {
    @SerializedName("subCategoryList")
    @Expose
    private ArrayList<SubCategoryList> subCategoryList = null;

    public ArrayList<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
