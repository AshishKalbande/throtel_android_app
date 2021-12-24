package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMonthlyDetailsData {

    @SerializedName("subscriptionOrderDetail")
    @Expose
    private SubscriptionMonthlyOrderDetail subscriptionOrderDetail;

    public SubscriptionMonthlyOrderDetail getSubscriptionOrderDetail() {
        return subscriptionOrderDetail;
    }

    public void setSubscriptionOrderDetail(SubscriptionMonthlyOrderDetail subscriptionOrderDetail) {
        this.subscriptionOrderDetail = subscriptionOrderDetail;
    }
}
