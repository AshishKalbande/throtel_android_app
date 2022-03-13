package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityData {
    @SerializedName("districtList")
    @Expose
    private ArrayList<A_Test.model.CityList> cityList = null;

    public ArrayList<A_Test.model.CityList> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityList> cityList) {
        this.cityList = cityList;
    }


}
