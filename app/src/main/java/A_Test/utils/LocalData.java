package A_Test.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.throtel.grocery.utils.Constants;

import books.models.UserBookDetail;

public class LocalData {

    private static com.throtel.grocery.utils.LocalData instance;

    private static Context appContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public LocalData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        editor = preferences.edit();
        gson = new Gson();
    }

    public static synchronized com.throtel.grocery.utils.LocalData getInstance(Context context) {
        appContext = context;
        if (instance == null) {
            instance = new com.throtel.grocery.utils.LocalData();
        }
        return instance;
    }

    public void setTestLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(Constants.IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }
    public void setBookSignIn(UserBookDetail userDetails) {
        editor.putString(Constants.BOOKSIGN_IN_DATA, gson.toJson(userDetails));
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
}
