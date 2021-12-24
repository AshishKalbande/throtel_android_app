package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubCategoryWiseData {
    @SerializedName("productList")
    @Expose
    private ArrayList<ProductList> productList = null;

    public ArrayList<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductList> productList) {
        this.productList = productList;
    }
}
