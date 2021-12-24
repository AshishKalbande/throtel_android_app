package books.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Color {

    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("colorName")
    @Expose
    private String colorName;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

}