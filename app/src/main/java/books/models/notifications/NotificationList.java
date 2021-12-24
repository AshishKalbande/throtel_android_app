package books.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationList implements Serializable {

    private final static long serialVersionUID = 47146036684688895L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("notificationDesc")
    @Expose
    private String notificationDesc;
    @SerializedName("notificationFor")
    @Expose
    private String notificationFor;
    @SerializedName("notificationDate")
    @Expose
    private String notificationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getNotificationFor() {
        return notificationFor;
    }

    public void setNotificationFor(String notificationFor) {
        this.notificationFor = notificationFor;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

}