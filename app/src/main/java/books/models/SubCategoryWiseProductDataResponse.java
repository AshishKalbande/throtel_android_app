package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import books.models.SubCategoryWiseData;

public class SubCategoryWiseProductDataResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("data")
    @Expose
    private books.models.SubCategoryWiseData data;

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

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public books.models.SubCategoryWiseData getData() {
        return data;
    }

    public void setData(SubCategoryWiseData data) {
        this.data = data;
    }

}