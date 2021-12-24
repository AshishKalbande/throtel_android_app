package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartProductList implements Serializable {
    @SerializedName("cartId")
    @Expose
    private Integer cartId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
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
    @SerializedName("selectedQuantity")
    @Expose
    private Integer selectedQuantity;
//    @SerializedName("productPricePerGramOrMl")
//    @Expose
//    private Double productPricePerGramOrMl;
//    @SerializedName("availableQuantityInKgOrLtr")
//    @Expose
//    private Double availableQuantityInKgOrLtr;
//    @SerializedName("minQuantityInKgOrLtr")
//    @Expose
//    private Double minQuantityInKgOrLtr;
//    @SerializedName("productType")
//    @Expose
//    private String productType;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
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

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

//    public Double getProductPricePerGramOrMl() {
//        return productPricePerGramOrMl;
//    }
//
//    public void setProductPricePerGramOrMl(Double productPricePerGramOrMl) {
//        this.productPricePerGramOrMl = productPricePerGramOrMl;
//    }
//
//    public Double getAvailableQuantityInKgOrLtr() {
//        return availableQuantityInKgOrLtr;
//    }
//
//    public void setAvailableQuantityInKgOrLtr(Double availableQuantityInKgOrLtr) {
//        this.availableQuantityInKgOrLtr = availableQuantityInKgOrLtr;
//    }
//
//    public Double getMinQuantityInKgOrLtr() {
//        return minQuantityInKgOrLtr;
//    }
//
//    public void setMinQuantityInKgOrLtr(Double minQuantityInKgOrLtr) {
//        this.minQuantityInKgOrLtr = minQuantityInKgOrLtr;
//    }
//
//    public String getProductType() {
//        return productType;
//    }
//
//    public void setProductType(String productType) {
//        this.productType = productType;
//    }

}
