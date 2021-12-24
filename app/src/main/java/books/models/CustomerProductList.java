package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerProductList implements Serializable {


    private final static long serialVersionUID = 6174418579437296076L;
    @SerializedName("cartId")
    @Expose
    private int cartId;
    @SerializedName("productId")
    @Expose
    private int productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
//    @SerializedName("orderReturn")
//    @Expose
//    private String orderReturn;
    @SerializedName("quantity")
    @Expose
    private int quantity;
//    @SerializedName("productType")
//    @Expose
//    private String productType;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

//    public String getOrderReturn() {
//        return orderReturn;
//    }
//
//    public void setOrderReturn(String orderReturn) {
//        this.orderReturn = orderReturn;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    public String getProductType() {
//        return productType;
//    }
//
//    public void setProductType(String productType) {
//        this.productType = productType;
//    }
}
