package books.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Size {

    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("size")
    @Expose
    private String size;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}