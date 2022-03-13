package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateListDataResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private StateData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public A_Test.model.StateData getData() {
        return data;
    }

    public void setData(A_Test.model.StateData data) {
        this.data = data;
    }

}
