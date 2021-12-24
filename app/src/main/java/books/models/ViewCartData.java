package books.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ViewCartData {
    @SerializedName("cartProductList")
    @Expose
    private ArrayList<CartProductList> cartProductList = null;

    public ArrayList<CartProductList> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(ArrayList<CartProductList> cartProductList) {
        this.cartProductList = cartProductList;
    }
}
