package com.throtel.grocery.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.throtel.grocery.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static Context context;
    public static final long oneDay = 24 * 60 * 60 * 1000L;

    public static String convertDate_Server_TO_dd_MM_yyyy(String strDate, String format) {
        if (strDate == null || TextUtils.isEmpty(strDate))
            return "";
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(format);
        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertDate(String strDate, String fromFormat, String toFormat) {
        // DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        if (strDate == null || TextUtils.isEmpty(strDate))
            return "";
        DateFormat originalFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(toFormat);

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertTimeFrom24to12HoursFormat(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("hh:mm:ss a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void onNavigationItemSelected(Context context, String title) {
        Utils.context = context;
        if (title.equalsIgnoreCase(getString(R.string.nav_home))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_orders))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_category))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_wallet))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_subscribe))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_account))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_subscription))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_orders))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_b2b_purchase))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_refer_earn))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_about_us))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_policy))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_need_help))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_share))) {

        } else if (title.equalsIgnoreCase(getString(R.string.nav_logout))) {

        }

    }

    private static String getString(int id) {
        return context.getString(id);
    }

    public static Date addOneDayToDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static void downloadFile(Context context, String fileName, String url) {
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, context.getString(R.string.app_name));
        downloadmanager.enqueue(request);

    }

}
