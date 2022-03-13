package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BannerLists implements Serializable {
    @SerializedName("bannerList")
    @Expose
    private ArrayList<BannerData> bannerList = null;

    public ArrayList<BannerData> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerData> bannerList) {
        this.bannerList = bannerList;
    }
}
