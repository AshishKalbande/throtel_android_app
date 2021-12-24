package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OffersListData implements Serializable {

    private final static long serialVersionUID = -3348927940773864414L;
    @SerializedName("globalOfferList")
    @Expose
    private ArrayList<GlobalOfferList> globalOfferList = null;
    @SerializedName("categoryOfferList")
    @Expose
    private ArrayList<CategoryOfferList> categoryOfferList = null;

    public ArrayList<GlobalOfferList> getGlobalOfferList() {
        return globalOfferList;
    }

    public void setGlobalOfferList(ArrayList<GlobalOfferList> globalOfferList) {
        this.globalOfferList = globalOfferList;
    }

    public ArrayList<CategoryOfferList> getCategoryOfferList() {
        return categoryOfferList;
    }

    public void setCategoryOfferList(ArrayList<CategoryOfferList> categoryOfferList) {
        this.categoryOfferList = categoryOfferList;
    }

}