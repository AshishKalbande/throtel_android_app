package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubProductList  implements Serializable {
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
    @SerializedName("productImage2")
    @Expose
    private String productImage2;
    @SerializedName("productImage3")
    @Expose
    private String productImage3;
    @SerializedName("productNetPrice")
    @Expose
    private Double productNetPrice;
    @SerializedName("productSellingPrice")
    @Expose
    private Double productSellingPrice;
    @SerializedName("discountAmount")
    @Expose
    private Double discountAmount;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("availableQuantity")
    @Expose
    private Integer availableQuantity;
    @SerializedName("productPricePerGramOrMl")
    @Expose
    private Double productPricePerGramOrMl;
    @SerializedName("availableQuantityInKgOrLtr")
    @Expose
    private Double availableQuantityInKgOrLtr;
    @SerializedName("minQuantityInKgOrLtr")
    @Expose
    private Double minQuantityInKgOrLtr;
    @SerializedName("productType")
    @Expose
    private String productType;
    @SerializedName("productDesc")
    @Expose
    private String productDesc;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

    public String getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(String productImage2) {
        this.productImage2 = productImage2;
    }

    public String getProductImage3() {
        return productImage3;
    }

    public void setProductImage3(String productImage3) {
        this.productImage3 = productImage3;
    }

    public Double getProductNetPrice() {
        return productNetPrice;
    }

    public void setProductNetPrice(Double productNetPrice) {
        this.productNetPrice = productNetPrice;
    }

    public Double getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(Double productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Double getProductPricePerGramOrMl() {
        return productPricePerGramOrMl;
    }

    public void setProductPricePerGramOrMl(Double productPricePerGramOrMl) {
        this.productPricePerGramOrMl = productPricePerGramOrMl;
    }

    public Double getAvailableQuantityInKgOrLtr() {
        return availableQuantityInKgOrLtr;
    }

    public void setAvailableQuantityInKgOrLtr(Double availableQuantityInKgOrLtr) {
        this.availableQuantityInKgOrLtr = availableQuantityInKgOrLtr;
    }

    public Double getMinQuantityInKgOrLtr() {
        return minQuantityInKgOrLtr;
    }

    public void setMinQuantityInKgOrLtr(Double minQuantityInKgOrLtr) {
        this.minQuantityInKgOrLtr = minQuantityInKgOrLtr;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
