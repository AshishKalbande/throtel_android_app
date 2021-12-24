package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderList implements Serializable {
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("orderTotal")
    @Expose
    private Double orderTotal;
    @SerializedName("orderOtp")
    @Expose
    private Integer orderOtp;
    @SerializedName("customerOrderDeliverDate")
    @Expose
    private String customerOrderDeliverDate;
    @SerializedName("customerOrderDeliverStartTime")
    @Expose
    private String customerOrderDeliverStartTime;
    @SerializedName("customerOrderDeliverEndTime")
    @Expose
    private String customerOrderDeliverEndTime;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
//    @SerializedName("reviewStatus")
//    @Expose
//    private String reviewStatus;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getOrderOtp() {
        return orderOtp;
    }

    public void setOrderOtp(Integer orderOtp) {
        this.orderOtp = orderOtp;
    }

    public String getCustomerOrderDeliverDate() {
        return customerOrderDeliverDate;
    }

    public void setCustomerOrderDeliverDate(String customerOrderDeliverDate) {
        this.customerOrderDeliverDate = customerOrderDeliverDate;
    }

    public String getCustomerOrderDeliverStartTime() {
        return customerOrderDeliverStartTime;
    }

    public void setCustomerOrderDeliverStartTime(String customerOrderDeliverStartTime) {
        this.customerOrderDeliverStartTime = customerOrderDeliverStartTime;
    }

    public String getCustomerOrderDeliverEndTime() {
        return customerOrderDeliverEndTime;
    }

    public void setCustomerOrderDeliverEndTime(String customerOrderDeliverEndTime) {
        this.customerOrderDeliverEndTime = customerOrderDeliverEndTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

//    public String getReviewStatus() {
//        return reviewStatus;
//    }
//
//    public void setReviewStatus(String reviewStatus) {
//        this.reviewStatus = reviewStatus;
//    }
}
