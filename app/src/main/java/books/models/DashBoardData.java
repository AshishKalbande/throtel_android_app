package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import books.models.BannerList;
import books.models.CategoryList;
import books.models.ProductList;

import java.io.Serializable;
import java.util.ArrayList;

public class DashBoardData implements Serializable {

    @SerializedName("bannerList")
    @Expose
    private ArrayList<books.models.BannerList> bannerList = null;
    @SerializedName("productList")
    @Expose
    private ArrayList<books.models.ProductList> productList = null;
    @SerializedName("groupList")
    @Expose
    private ArrayList<books.models.CategoryList> groupList = null;

    public ArrayList<books.models.BannerList> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerList> bannerList) {
        this.bannerList = bannerList;
    }

    public ArrayList<books.models.ProductList> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductList> productList) {
        this.productList = productList;
    }

    public ArrayList<books.models.CategoryList> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<CategoryList> groupList) {
        this.groupList = groupList;
    }


}