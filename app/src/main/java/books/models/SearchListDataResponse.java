package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import books.models.SearchData;

public class SearchListDataResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pageCount")
    @Expose
    private Object pageCount;
    @SerializedName("data")
    @Expose
    private books.models.SearchData data;

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

    public Object getPageCount() {
        return pageCount;
    }

    public void setPageCount(Object pageCount) {
        this.pageCount = pageCount;
    }

    public books.models.SearchData getData() {
        return data;
    }

    public void setData(SearchData data) {
        this.data = data;
    }

}