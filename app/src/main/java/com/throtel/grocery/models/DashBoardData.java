package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DashBoardData implements Serializable {

    @SerializedName("bannerList")
    @Expose
    private ArrayList<BannerList> bannerList = null;
    @SerializedName("productList")
    @Expose
    private ArrayList<ProductList> productList = null;
    @SerializedName("groupList")
    @Expose
    private ArrayList<GroupList> groupList = null;

    public ArrayList<BannerList> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerList> bannerList) {
        this.bannerList = bannerList;
    }

    public ArrayList<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductList> productList) {
        this.productList = productList;
    }

    public ArrayList<GroupList> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<GroupList> groupList) {
        this.groupList = groupList;
    }


}