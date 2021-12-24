package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionData {
    @SerializedName("customerAppVersion")
    @Expose
    private CustomerVersionApi customerAppVersion;

    public CustomerVersionApi getCustomerAppVersion() {
        return customerAppVersion;
    }

    public void setCustomerAppVersion(CustomerVersionApi customerAppVersion) {
        this.customerAppVersion = customerAppVersion;
    }
}


