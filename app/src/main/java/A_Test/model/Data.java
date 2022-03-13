package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.throtel.grocery.models.UserDetail;


public class Data {
    @SerializedName("userDetail")
    @Expose
    private UserDetail userDetail;

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

}
