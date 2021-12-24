package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OfferProductListData implements Serializable {

    private final static long serialVersionUID = -6104209681392728748L;
    @SerializedName("productList")
    @Expose
    private ArrayList<ProductList> productList = null;

    public ArrayList<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductList> productList) {
        this.productList = productList;
    }

}