package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PackDetailsData {
    @SerializedName("packProductList")
    @Expose
    private ArrayList<PackProductList> packProductList = null;

    public ArrayList<PackProductList> getPackProductList() {
        return packProductList;
    }

    public void setPackProductList(ArrayList<PackProductList> packProductList) {
        this.packProductList = packProductList;
    }
}
