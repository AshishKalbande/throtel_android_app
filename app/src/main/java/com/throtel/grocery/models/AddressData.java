package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressData {
    @SerializedName("addressList")
    @Expose
    private ArrayList<AddressList> addressList = null;

    public ArrayList<AddressList> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<AddressList> addressList) {
        this.addressList = addressList;
    }

}
