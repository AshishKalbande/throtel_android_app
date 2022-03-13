package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PincodeList implements Serializable {
    @SerializedName("tahsilId")
    @Expose
    private Integer pincodeId;
    @SerializedName("tahsilName")
    @Expose
    private String pincodeNumber;

    public Integer getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(Integer pincodeId) {
        this.pincodeId = pincodeId;
    }

    public String getPincodeNumber() {
        return pincodeNumber;
    }

    public void setPincodeNumber(String pincodeNumber) {
        this.pincodeNumber = pincodeNumber;
    }
}
