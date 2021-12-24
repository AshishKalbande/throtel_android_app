package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnDetailsData {
    @SerializedName("returnProductDetail")
    @Expose
    private ReturnProductDetail returnProductDetail;

    public ReturnProductDetail getReturnProductDetail() {
        return returnProductDetail;
    }

    public void setReturnProductDetail(ReturnProductDetail returnProductDetail) {
        this.returnProductDetail = returnProductDetail;
    }
}
