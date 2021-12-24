package com.throtel.grocery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionChargeDetail {
    @SerializedName("creditCard")
    @Expose
    private Double creditCard;
    @SerializedName("debitCard")
    @Expose
    private Double debitCard;
    @SerializedName("cashOnDelivery")
    @Expose
    private Double cashOnDelivery;
    @SerializedName("internetBanking")
    @Expose
    private Double internetBanking;
    @SerializedName("upi")
    @Expose
    private Double upi;
    @SerializedName("wallet")
    @Expose
    private Double wallet;

    public Double getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Double creditCard) {
        this.creditCard = creditCard;
    }

    public Double getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(Double debitCard) {
        this.debitCard = debitCard;
    }

    public Double getCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(Double cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public Double getInternetBanking() {
        return internetBanking;
    }

    public void setInternetBanking(Double internetBanking) {
        this.internetBanking = internetBanking;
    }

    public Double getUpi() {
        return upi;
    }

    public void setUpi(Double upi) {
        this.upi = upi;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

}
