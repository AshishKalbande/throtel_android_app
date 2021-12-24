package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryList implements Serializable {
    @SerializedName("mainCategoryId")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("mainCategoryName")
    @Expose
    private String mainCategoryName;
    @SerializedName("mainCategoryImage")
    @Expose
    private String mainCategoryImage;

    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getMainCategoryImage() {
        return mainCategoryImage;
    }

    public void setMainCategoryImage(String mainCategoryImage) {
        this.mainCategoryImage = mainCategoryImage;
    }
}
