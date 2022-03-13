package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import books.models.UserBookDetail;

public class LoginData {

    @SerializedName("userDetail")
    @Expose
    private UserTestDetails userDetail;

    public UserTestDetails getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserTestDetails userDetail) {
        this.userDetail = userDetail;
    }
}
