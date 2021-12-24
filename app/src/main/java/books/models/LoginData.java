package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("userDetail")
    @Expose
    private UserBookDetail userBookDetail;

    public UserBookDetail getUserDetail() {
        return userBookDetail;
    }

    public void setUserDetail(UserBookDetail userBookDetail) {
        this.userBookDetail = userBookDetail;
    }
}
