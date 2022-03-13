package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BatchData {

    @SerializedName("batchList")
    @Expose
    private ArrayList<BatchList> batchList;

    public ArrayList<BatchList> getBatchList() {

        return batchList;
    }

    public void setBatchList(ArrayList<BatchList> batchList) {
        this.batchList = batchList;
    }
}
