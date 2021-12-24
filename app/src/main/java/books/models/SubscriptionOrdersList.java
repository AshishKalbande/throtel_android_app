package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriptionOrdersList implements Serializable {

    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("orderType")
    @Expose
    private String orderType;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("orderTotal")
    @Expose
    private Double orderTotal;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
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

}
