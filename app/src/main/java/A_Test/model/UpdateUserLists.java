package A_Test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateUserLists implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("stateId")
    @Expose
    private Integer stateName;
    @SerializedName("districtId")
    @Expose
    private Integer districtName;
    @SerializedName("tahsilId")
    @Expose
    private Integer tahsilName;
    @SerializedName("schoolName")
    @Expose
    private String schoolName;
    @SerializedName("className")
    @Expose
    private String className;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getStateName() {
        return stateName;
    }

    public void setStateName(Integer stateName) {
        this.stateName = stateName;
    }

    public Integer getDistrictName() {
        return districtName;
    }

    public void setDistrictName(Integer districtName) {
        this.districtName = districtName;
    }

    public Integer getTahsilName() {
        return tahsilName;
    }

    public void setTahsilName(Integer tahsilName) {
        this.tahsilName = tahsilName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
//
//    public Integer getStateId() {
//        return stateId;
//    }
//
//    public void setStateId(Integer stateId) {
//        this.stateId = stateId;
//    }
//
//    public Integer getDistrictId() {
//        return districtId;
//    }
//
//    public void setDistrictId(Integer districtId) {
//        this.districtId = districtId;
//    }
//
//    public Integer getTahsilId() {
//        return tahsilId;
//    }
//
//    public void setTahsilId(Integer tahsilId) {
//        this.tahsilId = tahsilId;
//    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
