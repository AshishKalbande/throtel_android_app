package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnProductDetail {
    @SerializedName("returnId")
    @Expose
    private Integer returnId;
    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("productPrice")
    @Expose
    private Double productPrice;
    @SerializedName("productReturnImage")
    @Expose
    private String productReturnImage;
    @SerializedName("productReturnReview")
    @Expose
    private String productReturnReview;
    @SerializedName("orderRaise")
    @Expose
    private String orderRaise;
    @SerializedName("orderRaiseDate")
    @Expose
    private String orderRaiseDate;
    @SerializedName("vendorOrderConfirmation")
    @Expose
    private String vendorOrderConfirmation;
    @SerializedName("vendorOrderConfirmationDate")
    @Expose
    private String vendorOrderConfirmationDate;
    @SerializedName("supervisorOrderConfirmation")
    @Expose
    private String supervisorOrderConfirmation;
    @SerializedName("supervisorOrderConfirmationDate")
    @Expose
    private String supervisorOrderConfirmationDate;
    @SerializedName("orderAssigned")
    @Expose
    private String orderAssigned;
    @SerializedName("orderAssignedDate")
    @Expose
    private String orderAssignedDate;
    @SerializedName("deliveryBoyOrderConfirmation")
    @Expose
    private String deliveryBoyOrderConfirmation;
    @SerializedName("deliveryBoyOrderConfirmationDate")
    @Expose
    private String deliveryBoyOrderConfirmationDate;
    @SerializedName("orderPicked")
    @Expose
    private String orderPicked;
    @SerializedName("orderPickedDate")
    @Expose
    private String orderPickedDate;
    @SerializedName("orderRecievedBySupervisor")
    @Expose
    private String orderRecievedBySupervisor;
    @SerializedName("orderRecievedBySupervisorDate")
    @Expose
    private String orderRecievedBySupervisorDate;
    @SerializedName("orderRecievedByVendor")
    @Expose
    private String orderRecievedByVendor;
    @SerializedName("orderRecievedByVendorDate")
    @Expose
    private String orderRecievedByVendorDate;
    @SerializedName("returnOrderStatus")
    @Expose
    private String returnOrderStatus;

    public String getReturnOrderStatus() {
        return returnOrderStatus;
    }

    public void setReturnOrderStatus(String returnOrderStatus) {
        this.returnOrderStatus = returnOrderStatus;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductReturnImage() {
        return productReturnImage;
    }

    public void setProductReturnImage(String productReturnImage) {
        this.productReturnImage = productReturnImage;
    }

    public String getProductReturnReview() {
        return productReturnReview;
    }

    public void setProductReturnReview(String productReturnReview) {
        this.productReturnReview = productReturnReview;
    }

    public String getOrderRaise() {
        return orderRaise;
    }

    public void setOrderRaise(String orderRaise) {
        this.orderRaise = orderRaise;
    }

    public String getOrderRaiseDate() {
        return orderRaiseDate;
    }

    public void setOrderRaiseDate(String orderRaiseDate) {
        this.orderRaiseDate = orderRaiseDate;
    }

    public String getVendorOrderConfirmation() {
        return vendorOrderConfirmation;
    }

    public void setVendorOrderConfirmation(String vendorOrderConfirmation) {
        this.vendorOrderConfirmation = vendorOrderConfirmation;
    }

    public String getVendorOrderConfirmationDate() {
        return vendorOrderConfirmationDate;
    }

    public void setVendorOrderConfirmationDate(String vendorOrderConfirmationDate) {
        this.vendorOrderConfirmationDate = vendorOrderConfirmationDate;
    }

    public String getSupervisorOrderConfirmation() {
        return supervisorOrderConfirmation;
    }

    public void setSupervisorOrderConfirmation(String supervisorOrderConfirmation) {
        this.supervisorOrderConfirmation = supervisorOrderConfirmation;
    }

    public String getSupervisorOrderConfirmationDate() {
        return supervisorOrderConfirmationDate;
    }

    public void setSupervisorOrderConfirmationDate(String supervisorOrderConfirmationDate) {
        this.supervisorOrderConfirmationDate = supervisorOrderConfirmationDate;
    }

    public String getOrderAssigned() {
        return orderAssigned;
    }

    public void setOrderAssigned(String orderAssigned) {
        this.orderAssigned = orderAssigned;
    }

    public String getOrderAssignedDate() {
        return orderAssignedDate;
    }

    public void setOrderAssignedDate(String orderAssignedDate) {
        this.orderAssignedDate = orderAssignedDate;
    }

    public String getDeliveryBoyOrderConfirmation() {
        return deliveryBoyOrderConfirmation;
    }

    public void setDeliveryBoyOrderConfirmation(String deliveryBoyOrderConfirmation) {
        this.deliveryBoyOrderConfirmation = deliveryBoyOrderConfirmation;
    }

    public String getDeliveryBoyOrderConfirmationDate() {
        return deliveryBoyOrderConfirmationDate;
    }

    public void setDeliveryBoyOrderConfirmationDate(String deliveryBoyOrderConfirmationDate) {
        this.deliveryBoyOrderConfirmationDate = deliveryBoyOrderConfirmationDate;
    }

    public String getOrderPicked() {
        return orderPicked;
    }

    public void setOrderPicked(String orderPicked) {
        this.orderPicked = orderPicked;
    }

    public String getOrderPickedDate() {
        return orderPickedDate;
    }

    public void setOrderPickedDate(String orderPickedDate) {
        this.orderPickedDate = orderPickedDate;
    }

    public String getOrderRecievedBySupervisor() {
        return orderRecievedBySupervisor;
    }

    public void setOrderRecievedBySupervisor(String orderRecievedBySupervisor) {
        this.orderRecievedBySupervisor = orderRecievedBySupervisor;
    }

    public String getOrderRecievedBySupervisorDate() {
        return orderRecievedBySupervisorDate;
    }

    public void setOrderRecievedBySupervisorDate(String orderRecievedBySupervisorDate) {
        this.orderRecievedBySupervisorDate = orderRecievedBySupervisorDate;
    }

    public String getOrderRecievedByVendor() {
        return orderRecievedByVendor;
    }

    public void setOrderRecievedByVendor(String orderRecievedByVendor) {
        this.orderRecievedByVendor = orderRecievedByVendor;
    }

    public String getOrderRecievedByVendorDate() {
        return orderRecievedByVendorDate;
    }

    public void setOrderRecievedByVendorDate(String orderRecievedByVendorDate) {
        this.orderRecievedByVendorDate = orderRecievedByVendorDate;
    }
}
