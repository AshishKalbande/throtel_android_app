package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerOrderData {
    @SerializedName("productList")
    @Expose
    private ArrayList<CustomerProductList> productList = null;

    public ArrayList<CustomerProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<CustomerProductList> productList) {
        this.productList = productList;
    }
}
