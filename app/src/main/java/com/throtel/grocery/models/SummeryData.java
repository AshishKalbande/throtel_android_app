package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SummeryData {
    @SerializedName("transactionList")
    @Expose
    private ArrayList<TransactionList> transactionList = null;

    public ArrayList<TransactionList> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(ArrayList<TransactionList> transactionList) {
        this.transactionList = transactionList;
    }
}
