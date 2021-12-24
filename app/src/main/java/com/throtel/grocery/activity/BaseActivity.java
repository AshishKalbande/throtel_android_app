package com.throtel.grocery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.models.ViewCartDataResponse;
import com.throtel.grocery.utils.LocalData;
import com.throtel.grocery.views.MyProgress;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {

    //public static Double TotalAmount=0.0;
    // public static int ItemCount=0;
    public static String AddressId;
    public static String SlotTime = "";
    public static Double DeliveryCharge = 0.0;
    public static Double CouponApplied = 0.0;
    public static String DeliveryDate;
    public static String Reason = "";
    protected LocalData localData;
    private int count = 0;
    private Double TotalPrice = 0.0, TotalSaved = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localData = LocalData.getInstance(this);
    }

    protected void setUpToolbarBackButton(String name) {
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(name);

    }

    protected String convertDate_dd_MM_yyyy_To_yyyy_dd_MM(String strDate) {
        DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        // DateFormat targetFormat = new SimpleDateFormat("dd MMM yy");
        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertDate_dd_MMM_yyyy_To_yyyy_dd_MM(String strDate) {
        // DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat originalFormat = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(String strDate) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("");

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertServerDate_dd_MMM_yyyy(String strDate) {
        if (strDate != null) {
            // DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            //  DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy , hh:mm");
            DateFormat targetFormat = new SimpleDateFormat("dd MMM yy , hh:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'


            try {
                Date date = originalFormat.parse(strDate);
                return targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }

    protected boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    protected boolean isValidName(String name) {
        return name.matches("^[.A-Za-z ]+$");
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void getViewCartList() {

        final FrameLayout frameCheckOut = findViewById(R.id.frame_checkout);
        final TextView tvItemNo = findViewById(R.id.tv_item_no);
        final TextView tvTotalAmnt = findViewById(R.id.tv_total_amnt);
        final TextView tvTotalSaved = findViewById(R.id.tv_total_saved);

        frameCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, ViewCartTabsActivity.class));
                finishAndRemoveTask();
            }
        });

        count = 0;

        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // setUpViewCartList(response.body().getData().getCartProductList());
                    ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
                    frameCheckOut.setVisibility(View.VISIBLE);

                    TotalPrice = 0.0;
                    TotalSaved = 0.0;
                    for (CartProductList i : cartList) {
                        TotalPrice += i.getProductSellingPrice() * i.getSelectedQuantity();
                        TotalSaved += (i.getProductNetPrice() - i.getProductSellingPrice()) * i.getSelectedQuantity();
                    }
                  //  count = count + cartList.size();
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                    }
                } else {
                    //  showToast(response.body().getMessage());
                    //frameCheckOut.setVisibility(View.GONE);
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });

        Call<ViewCartDataResponse> call1 = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call1.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // setUpViewCartList(response.body().getData().getCartProductList());
                    ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
                    frameCheckOut.setVisibility(View.VISIBLE);
//
//                    TotalPriceBulk = 0.0;
//                    TotalSavedBulk = 0.0;
                    for (CartProductList i : cartList) {
                        //TotalPriceBulk += i.getProductPricePerGramOrMl() * 1000 * i.getSelectedQuantity();
                        //TotalSavedBulk += (i.getProductNetPrice() - i.getProductSellingPrice()) * i.getSelectedQuantity();
                    }
                   count = count + cartList.size();
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved ));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                    }
                } else {
                    //  showToast(response.body().getMessage());
                    //frameCheckOut.setVisibility(View.GONE);
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved ));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });

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
}
