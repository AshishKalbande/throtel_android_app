package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoutryList {


        @SerializedName("countires")
        @Expose
        public ArrayList<A_Test.model.CountryModel> countires;

    public CoutryList(ArrayList<A_Test.model.CountryModel> countires) {
        this.countires = countires;
    }

    public CoutryList() {
    }

    public ArrayList<A_Test.model.CountryModel> getCountires() {
        return countires;
    }

    public void setCountires(ArrayList<A_Test.model.CountryModel> countires) {
        this.countires = countires;
    }

    @Override
    public String toString() {
        return "CoutryList{" +
                "countires=" + countires +
                '}';
    }
}
