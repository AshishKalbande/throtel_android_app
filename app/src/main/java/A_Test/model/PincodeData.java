package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

public class PincodeData {
    @SerializedName("tahsilList")
    @Expose
    private ArrayList<A_Test.model.PincodeList> pincodeList = null;

    public ArrayList<PincodeList> getPincodeList() {
        return pincodeList;
    }

    public void setPincodeList(ArrayList<PincodeList> pincodeList) {
        this.pincodeList = pincodeList;
    }
}
