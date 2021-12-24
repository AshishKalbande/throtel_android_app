package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductList  implements Serializable {
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage1")
    @Expose
    private String productImage1;
    @SerializedName("mainProductId")
    @Expose
    private Integer mainProductId;
    @SerializedName("productNetPrice")
    @Expose
    private Double productNetPrice;
    @SerializedName("productSellingPrice")
    @Expose
    private Double productSellingPrice;
    @SerializedName("discountPercentage")
    @Expose
    private Double discountPercentage;
    @SerializedName("productUnit")
    @Expose
    private String productUnit;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Integer getMainProductId() {
        return mainProductId;
    }

    public void setMainProductId(Integer mainProductId) {
        this.mainProductId = mainProductId;
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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

}