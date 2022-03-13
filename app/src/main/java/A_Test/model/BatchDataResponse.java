package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchDataResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private BatchData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BatchData getData() {
        return data;
    }

    public void setData(BatchData data) {
        this.data = data;
    }
}
