package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerPhone")
    @Expose
    private String customerPhone;
    @SerializedName("customerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("paymentId")
    @Expose
    private String paymentId;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("netPrice")
    @Expose
    private Double netPrice;
    @SerializedName("coupanAmount")
    @Expose
    private Double coupanAmount;
    @SerializedName("totalPriceAfterExcludingCoupanAmount")
    @Expose
    private Double totalPriceAfterExcludingCoupanAmount;
    @SerializedName("deliveryAmount")
    @Expose
    private Double deliveryAmount;
    @SerializedName("totalPriceAfterIncludingDeliveryAmount")
    @Expose
    private Double totalPriceAfterIncludingDeliveryAmount;
    @SerializedName("serviceCharge")
    @Expose
    private Double serviceCharge;
    @SerializedName("totalPriceAfterIncludingServiceCharge")
    @Expose
    private Double totalPriceAfterIncludingServiceCharge;
    @SerializedName("customerOrderDeliverDate")
    @Expose
    private String customerOrderDeliverDate;
    @SerializedName("customerOrderDeliverStartTime")
    @Expose
    private String customerOrderDeliverStartTime;
    @SerializedName("customerOrderDeliverEndTime")
    @Expose
    private String customerOrderDeliverEndTime;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("orderPlaced")
    @Expose
    private String orderPlaced;
    @SerializedName("orderPlacedDate")
    @Expose
    private String orderPlacedDate;
    @SerializedName("orderPacked")
    @Expose
    private String orderPacked;
    @SerializedName("orderPackedDate")
    @Expose
    private Object orderPackedDate;
    @SerializedName("orderDispatched")
    @Expose
    private String orderDispatched;
    @SerializedName("orderDispatchedDate")
    @Expose
    private Object orderDispatchedDate;
    @SerializedName("orderDelivered")
    @Expose
    private String orderDelivered;
    @SerializedName("orderDeliverdDate")
    @Expose
    private Object orderDeliverdDate;
    @SerializedName("orderPaymentId")
    @Expose
    private String orderPaymentId;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    public Double getCoupanAmount() {
        return coupanAmount;
    }

    public void setCoupanAmount(Double coupanAmount) {
        this.coupanAmount = coupanAmount;
    }

    public Double getTotalPriceAfterExcludingCoupanAmount() {
        return totalPriceAfterExcludingCoupanAmount;
    }

    public void setTotalPriceAfterExcludingCoupanAmount(Double totalPriceAfterExcludingCoupanAmount) {
        this.totalPriceAfterExcludingCoupanAmount = totalPriceAfterExcludingCoupanAmount;
    }

    public Double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(Double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Double getTotalPriceAfterIncludingDeliveryAmount() {
        return totalPriceAfterIncludingDeliveryAmount;
    }

    public void setTotalPriceAfterIncludingDeliveryAmount(Double totalPriceAfterIncludingDeliveryAmount) {
        this.totalPriceAfterIncludingDeliveryAmount = totalPriceAfterIncludingDeliveryAmount;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getTotalPriceAfterIncludingServiceCharge() {
        return totalPriceAfterIncludingServiceCharge;
    }

    public void setTotalPriceAfterIncludingServiceCharge(Double totalPriceAfterIncludingServiceCharge) {
        this.totalPriceAfterIncludingServiceCharge = totalPriceAfterIncludingServiceCharge;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(String orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public String getOrderPacked() {
        return orderPacked;
    }

    public void setOrderPacked(String orderPacked) {
        this.orderPacked = orderPacked;
    }

    public Object getOrderPackedDate() {
        return orderPackedDate;
    }

    public void setOrderPackedDate(Object orderPackedDate) {
        this.orderPackedDate = orderPackedDate;
    }

    public String getOrderDispatched() {
        return orderDispatched;
    }

    public void setOrderDispatched(String orderDispatched) {
        this.orderDispatched = orderDispatched;
    }

    public Object getOrderDispatchedDate() {
        return orderDispatchedDate;
    }

    public void setOrderDispatchedDate(Object orderDispatchedDate) {
        this.orderDispatchedDate = orderDispatchedDate;
    }

    public String getOrderDelivered() {
        return orderDelivered;
    }

    public void setOrderDelivered(String orderDelivered) {
        this.orderDelivered = orderDelivered;
    }

    public Object getOrderDeliverdDate() {
        return orderDeliverdDate;
    }

    public void setOrderDeliverdDate(Object orderDeliverdDate) {
        this.orderDeliverdDate = orderDeliverdDate;
    }
    public String getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(String orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }
}
