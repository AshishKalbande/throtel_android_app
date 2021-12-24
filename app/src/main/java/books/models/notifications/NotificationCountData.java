package books.models.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationCountData implements Serializable {

    private final static long serialVersionUID = 9082996246088065874L;
    @SerializedName("notificationDetail")
    @Expose
    private NotificationDetail notificationDetail;

    public NotificationDetail getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(NotificationDetail notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

}