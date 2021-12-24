package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriptionOrderList implements Serializable {
    @SerializedName("orderCartId")
    @Expose
    private Integer orderCartId;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("orderPause")
    @Expose
    private String orderPause;
    @SerializedName("productPrice")
    @Expose
    private Double productPrice;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("customerOrderDeliverDate")
    @Expose
    private String customerOrderDeliverDate;
    @SerializedName("customerOrderDeliverStartTime")
    @Expose
    private String customerOrderDeliverStartTime;
    @SerializedName("customerOrderDeliverEndTime")
    @Expose
    private String customerOrderDeliverEndTime;
    @SerializedName("cartOrderStatus")
    @Expose
    private String cartOrderStatus;

    public Integer getOrderCartId() {
        return orderCartId;
    }

    public void setOrderCartId(Integer orderCartId) {
        this.orderCartId = orderCartId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderPause() {
        return orderPause;
    }

    public void setOrderPause(String orderPause) {
        this.orderPause = orderPause;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getCartOrderStatus() {
        return cartOrderStatus;
    }

    public void setCartOrderStatus(String cartOrderStatus) {
        this.cartOrderStatus = cartOrderStatus;
    }
}
