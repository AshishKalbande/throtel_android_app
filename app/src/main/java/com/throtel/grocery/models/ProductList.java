package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList  implements Serializable {
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("subProductList")
    @Expose
    private ArrayList<SubProductList> subProductList = null;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ArrayList<SubProductList> getSubProductList() {
        return subProductList;
    }

    public void setSubProductList(ArrayList<SubProductList> subProductList) {
        this.subProductList = subProductList;
    }
}
