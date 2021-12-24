package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupWiseData {
    @SerializedName("categoryList")
    @Expose
    private ArrayList<CategoryList> categoryList = null;

    public ArrayList<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

}
