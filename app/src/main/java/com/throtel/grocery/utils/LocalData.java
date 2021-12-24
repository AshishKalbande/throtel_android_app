package com.throtel.grocery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.throtel.grocery.models.UserDetail;

import com.google.gson.Gson;

import books.models.UserBookDetail;

public class LocalData {
    private static LocalData instance;

    private static Context appContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public LocalData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        editor = preferences.edit();
        gson = new Gson();
    }

    public static synchronized LocalData getInstance(Context context) {
        appContext = context;
        if (instance == null) {
            instance = new LocalData();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = preferences.getBoolean(Constants.IS_LOGGED_IN, false);
        return isLoggedIn;
    }
    public boolean isBookLoggedIn() {
        boolean isLoggedIn = preferences.getBoolean(Constants.IS_BookLOGGED_IN, false);
        return isLoggedIn;
    }
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(Constants.IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }
    public void setBookLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(Constants.IS_BookLOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public UserDetail getSignIn() {
        String signInResponse = preferences.getString(Constants.SIGN_IN_DATA, "");
        if (!TextUtils.isEmpty(signInResponse)) {
            UserDetail signIn = gson.fromJson(signInResponse, UserDetail.class);
            return signIn;
        } else
            return null;
    }
    public UserBookDetail getBookSignIn() {
        String signInResponse = preferences.getString(Constants.BOOKSIGN_IN_DATA, "");
        if (!TextUtils.isEmpty(signInResponse)) {
            UserBookDetail signIn = gson.fromJson(signInResponse, UserBookDetail.class);
            return signIn;
        } else
            return null;
    }
    public void setSignIn(UserDetail userDetail) {
        editor.putString(Constants.SIGN_IN_DATA, gson.toJson(userDetail));
        editor.commit();
    }

    public void setBookSignIn(UserBookDetail userDetails) {
        editor.putString(Constants.BOOKSIGN_IN_DATA, gson.toJson(userDetails));
        editor.commit();
    }
    public void setMobileNumber(String mobileNumber) {
        editor.putString(Constants.MOBILE_NO, mobileNumber);
        editor.commit();
    }

    public boolean isMobileVerified() {
        boolean isMobileVerified = preferences.getBoolean(Constants.IS_MOBILE_VERIFIED, false);
        return isMobileVerified;
    }

    public void setMobileVerified(boolean isMobileVerified) {
        editor.putBoolean(Constants.IS_MOBILE_VERIFIED, isMobileVerified);
        editor.commit();
    }


    public String getCustomerId() {
        String customerId = preferences.getString(Constants.CUSTOMER_ID, "");
        if (!TextUtils.isEmpty(customerId)) {
            return customerId;
        } else
            return null;
    }

    public void setCustomerId(String customerId) {
        editor.putString(Constants.CUSTOMER_ID, customerId);
        editor.commit();
    }

    public String getWalletAmount() {
        String walletAmnt = preferences.getString(Constants.WALLET_AMOUNT, "");
        if (!TextUtils.isEmpty(walletAmnt)) {
            return walletAmnt;
        } else
            return null;
    }

    public void setWalletAmount(String walletAmnt) {
        editor.putString(Constants.WALLET_AMOUNT, walletAmnt);
        editor.apply();
    }



    public void logout() {
        editor.clear();
        editor.commit();
    }

}
