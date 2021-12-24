package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityData {
    @SerializedName("districtList")
    @Expose
    private ArrayList<CityList> cityList = null;

    public ArrayList<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityList> cityList) {
        this.cityList = cityList;
    }


}
