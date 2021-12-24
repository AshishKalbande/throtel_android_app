package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnProductListData {
    @SerializedName("returnProductList")
    @Expose
    private ArrayList<ReturnProductList> returnProductList = null;

    public ArrayList<ReturnProductList> getReturnProductList() {
        return returnProductList;
    }

    public void setReturnProductList(ArrayList<ReturnProductList> returnProductList) {
        this.returnProductList = returnProductList;
    }
}
