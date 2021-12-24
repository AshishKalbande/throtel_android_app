package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffersProductListResponse implements Serializable {

    private final static long serialVersionUID = -8913842921418551837L;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pageCount")
    @Expose
    private String pageCount;
    @SerializedName("data")
    @Expose
    private OfferProductListData data;

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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public OfferProductListData getData() {
        return data;
    }

    public void setData(OfferProductListData data) {
        this.data = data;
    }

}