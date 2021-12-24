package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderPaymentId implements Serializable {

    @SerializedName("orderPaymentId")
    @Expose
    private String orderPaymentId;
    private final static long serialVersionUID = -5638431599319502616L;

    public String getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(String orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

}
