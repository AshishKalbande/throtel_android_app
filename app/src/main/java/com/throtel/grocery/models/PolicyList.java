package com.throtel.grocery.models;

import java.io.Serializable;

public class PolicyList implements Serializable {

    private String policyCategory;
    private String policyDesc;

    public String getPolicyCategory() {
        return policyCategory;
    }

    public void setPolicyCategory(String policyCategory) {
        this.policyCategory = policyCategory;
    }

    public String getPolicyDesc() {
        return policyDesc;
    }

    public void setPolicyDesc(String policyDesc) {
        this.policyDesc = policyDesc;
    }

}