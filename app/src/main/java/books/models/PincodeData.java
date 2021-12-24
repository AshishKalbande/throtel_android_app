package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PincodeData {
    @SerializedName("tahsilList")
    @Expose
    private ArrayList<PincodeList> pincodeList = null;

    public ArrayList<PincodeList> getPincodeList() {
        return pincodeList;
    }

    public void setPincodeList(ArrayList<PincodeList> pincodeList) {
        this.pincodeList = pincodeList;
    }


}
