package books.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationDetail implements Serializable {

    private final static long serialVersionUID = -7410996910190781845L;
    @SerializedName("notificationCount")
    @Expose
    private int notificationCount;

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

}