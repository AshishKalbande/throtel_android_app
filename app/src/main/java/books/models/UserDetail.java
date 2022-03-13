package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("userId")
    @Expose
    private Object userId;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("stateName")
    @Expose
    private Object stateName;
    @SerializedName("districtName")
    @Expose
    private Object districtName;
    @SerializedName("tahsilName")
    @Expose
    private Object tahsilName;
    @SerializedName("profileImage")
    @Expose
    private Object profileImage;
    @SerializedName("status")
    @Expose
    private Object status;

    public UserDetail(Object userId, Object name, Object email, Object phone, Object stateName, Object districtName, Object tahsilName, Object profileImage, Object status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.stateName = stateName;
        this.districtName = districtName;
        this.tahsilName = tahsilName;
        this.profileImage = profileImage;
        this.status = status;
    }

    public UserDetail() {
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getStateName() {
        return stateName;
    }

    public void setStateName(Object stateName) {
        this.stateName = stateName;
    }

    public Object getDistrictName() {
        return districtName;
    }

    public void setDistrictName(Object districtName) {
        this.districtName = districtName;
    }

    public Object getTahsilName() {
        return tahsilName;
    }

    public void setTahsilName(Object tahsilName) {
        this.tahsilName = tahsilName;
    }

    public Object getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Object profileImage) {
        this.profileImage = profileImage;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}
