package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderListData {
    @SerializedName("orderList")
    @Expose
    private ArrayList<OrderList> orderList = null;

    public ArrayList<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderList> orderList) {
        this.orderList = orderList;
    }
}
