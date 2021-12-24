package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReturnProductList implements Serializable {

    @SerializedName("returnId")
    @Expose
    private Integer returnId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("quntity")
    @Expose
    private String quntity;
    @SerializedName("productType")
    @Expose
    private String productType;

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
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

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
