package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponCodeData {
    @SerializedName("couponDetail")
    @Expose
    private CouponDetail couponDetail;

    public CouponDetail getCouponDetail() {
        return couponDetail;
    }

    public void setCouponDetail(CouponDetail couponDetail) {
        this.couponDetail = couponDetail;
    }
}
