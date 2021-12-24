package com.throtel.grocery.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.SlotListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.AddressList;
import com.throtel.grocery.models.AddressListDataResponse;
import com.throtel.grocery.models.CouponCodeDataResponse;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.DeliveryChargeDataResponse;
import com.throtel.grocery.models.ProductList;
import com.throtel.grocery.models.SlotList;
import com.throtel.grocery.models.SlotListDataResponse;
import com.throtel.grocery.models.WalletDetailDataResponse;
import com.throtel.grocery.utils.Constants;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.utils.Utils;
import com.throtel.grocery.views.MyProgress;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.throtel.grocery.fragments.BaseFragment.WalletAmount;

public class DailySubscribeProductActivity extends BaseActivity implements SlotListAdapter.OnItemClickListener, PaymentResultListener {

    private static final String TAG = DailySubscribeProductActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    static int dateoffset = 0;
    TextView tvDaily, tvEvery3Day, tvAlternateDay, tvEvery7Day;
    Date fDate, cDate;
    int count;
    private RecyclerView rvSlotList;
    private TextView tvSelectedDate, tvProductName, tvProductWeight, tvSellingPrice, tvProductPrice;
    private TextView tvCName, tvType, tvFlatNo, tvCMobile, tvPincode, tvChangeAddress;
    private ImageView ivProductImg;
    private LinearLayout btnSubscribe, llSelectDate, llCouponCode;
    private EditText edtCouponCode;
    private TextView tvHavePromocode;
    private Button btnCodeApply;
    private RelativeLayout rlCouponCode;
    private ProductList product = null;
    private int subProduct = 0;
    private String cupId;
    private String Qty;

    //private CalendarView calendarView;
    private final String flag = "1";
    //private  Calendar calendar;
    private CalendarPickerView calendar;
    private Double SubscribeDeliveryCharge = 0.0;
    private Double SubscribeDailyTotalAmount = 0.0;
    private Double productSellingPrice = 0.0;
    private Double SubscribeCouponApplied = 0.0;
    private Double SubscribefinalAmnt = 0.0;
    private String SelectedDates;
    private boolean isAddressSelected = false;
    Double totalIncludingCouponAmount = 0.0;
    private double newWalletAmt = 0.0;
    Double totleProductprice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_subscribe_product);
        setUpToolbarBackButton("Subscribe");

        initView();

        if (getIntent() != null)
            product = (ProductList) getIntent().getSerializableExtra("ProductDetails");
        subProduct = getIntent().getIntExtra("SubproductId", 0);
        Qty = getIntent().getStringExtra(Constants.DAILYQTY);
        SlotTime = "";

        if (product != null) {
            setData();
        }

        if (NetworkUtil.getConnectivityStatus(DailySubscribeProductActivity.this)) {
            getSlotList();
            getActiveAddress();
            //getWalletDetails();
        } else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        btnSubscribe = findViewById(R.id.ll_subscribe);
        llSelectDate = findViewById(R.id.ll_date_select);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvProductName = findViewById(R.id.tv_pname);
        tvProductWeight = findViewById(R.id.tv_weight);
        tvSellingPrice = findViewById(R.id.tv_sell_price);
        tvProductPrice = findViewById(R.id.tv_price);
        ivProductImg = findViewById(R.id.iv_img);
        llCouponCode = findViewById(R.id.layout_coupon);
        rlCouponCode = findViewById(R.id.rvcode_apply);
        tvCName = findViewById(R.id.tv_name);
        tvCMobile = findViewById(R.id.tv_mobile);
        tvType = findViewById(R.id.tv_type);
        tvFlatNo = findViewById(R.id.tv_flat_no);
        tvPincode = findViewById(R.id.tv_pincode);
        tvChangeAddress = findViewById(R.id.tv_change);

        tvHavePromocode = findViewById(R.id.tv_have_promo_code);
        edtCouponCode = findViewById(R.id.edt_coupon_code);
        btnCodeApply = findViewById(R.id.btn_apply);
        rvSlotList = findViewById(R.id.rv_slot_list);

        tvDaily = findViewById(R.id.tv_daily);
        tvEvery3Day = findViewById(R.id.tv_every3day);
        tvAlternateDay = findViewById(R.id.tv_alternate_day);
        tvEvery7Day = findViewById(R.id.tv_every7day);

        calendar = findViewById(R.id.calendar_view);

        calendar.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        Date c = Utils.addOneDayToDate(Calendar.getInstance().getTime());
        System.out.println("Current time => " + c);

        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fd = dff.format(c);
        tvSelectedDate.setText(Utils.convertDate(fd, "yyyy-MM-dd", "dd MMM yy"));
    }


    private void setData() {
        //total of product
        productSellingPrice = Double.parseDouble(product.getSubProductList().get(subProduct).getProductSellingPrice().toString());
        SubscribeDailyTotalAmount = Double.parseDouble(product.getSubProductList().get(subProduct).getProductSellingPrice().toString()) * Double.parseDouble(Qty);

        tvProductName.setText(product.getProductName());
        tvProductWeight.setText(product.getSubProductList().get(subProduct).getProductWeight());
        // tvSellingPrice.setText(SubscribeDailyTotalAmount.toString());
        tvProductPrice.setText("₹ " + product.getSubProductList().get(subProduct).getProductSellingPrice().toString());
        tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + product.getSubProductList().get(subProduct).getProductNetPrice().toString() + "</strike>"));
        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getSubProductList().get(subProduct).getProductImage1();
        Picasso.with(DailySubscribeProductActivity.this)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(ivProductImg);

        llSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DailySubscribeProductActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = format.format(calendar.getTime());
                                tvSelectedDate.setText(Utils.convertDate(strDate, "yyyy-MM-dd", "dd MMM yy"));
                                setData();


                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar calendar = Calendar.getInstance();  // this is default system date
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + Utils.oneDay);  //set min date                 // set today's date as min date
                // calendar.add(Calendar.DAY_OF_MONTH, 30); // add date to 30 days later
                // datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()); //set max date
                datePickerDialog.show();

            }
        });

        tvChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ca = new Intent(DailySubscribeProductActivity.this, AddressListActivity.class);
                ca.putExtra("changefor", "Subscribe");
                startActivityForResult(ca, REQUEST_CODE);
                //finish();
            }
        });

        llCouponCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rlCouponCode.setVisibility(View.VISIBLE);
                llCouponCode.setVisibility(View.GONE);
            }
        });

        //Coupon code Apply
//        btnCodeApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (TextUtils.isEmpty(edtCouponCode.getText().toString())) {
//                    showToast("Please Enter Code");
//                } else {
//                    MyProgress.start(DailySubscribeProductActivity.this);
//                    Call<CouponCodeDataResponse> call = RetrofitClient.getRetrofitClient().getCouponApplyData(edtCouponCode.getText().toString(), localData.getCustomerId());
//                    call.enqueue(new Callback<CouponCodeDataResponse>() {
//                        @Override
//                        public void onResponse(Call<CouponCodeDataResponse> call, Response<CouponCodeDataResponse> response) {
//                            MyProgress.stop();
//
//                            if (response.body() != null) {
//                                if (response.body().getStatus().equalsIgnoreCase("true")) {
//                                    // showToast(response.body().getMessage());
//
//                                    if (SubscribeDailyTotalAmount > response.body().getData().getCouponDetail().getMaxPrice()) {
//                                        cupId = response.body().getData().getCouponDetail().getCouponId().toString();
//                                        getCoupneIdMethod();
//
//                                        SubscribeCouponApplied = response.body().getData().getCouponDetail().getCouponPrice().doubleValue();
//                                       /* rlCouponCode.setVisibility(View.GONE);
//                                        llCouponCode.setVisibility(View.VISIBLE);*/
//
//                                        Log.d("BATG", "Coupon Code Applied..." + SubscribeCouponApplied);
//                                        //Final Amount after applied coupon code
//                                        // SubscribefinalAmnt = SubscribeDailyTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
//                                    } else {
//                                        rlCouponCode.setVisibility(View.VISIBLE);
//                                        llCouponCode.setVisibility(View.GONE);
//                                        showToast("Order amount is minimum than Coupon amount  ");
//                                    }
//                                } else {
//                                    showToast(response.body().getMessage());
//                                }
//                            } else {
//                                showToast(getString(R.string.error_general));
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CouponCodeDataResponse> call, Throwable t) {
//                            MyProgress.stop();
//                            Log.d(TAG, t.getMessage());
//                            Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        });


        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribe();
            }
        });

        //====== =======CALENDAR CODE=======
        tvDaily.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
        tvDaily.setTextColor(getResources().getColor(R.color.orange));
        tvEvery3Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tvEvery3Day.setTextColor(getResources().getColor(R.color.black_alpha));
        tvAlternateDay.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tvAlternateDay.setTextColor(getResources().getColor(R.color.black_alpha));
        tvEvery7Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tvEvery7Day.setTextColor(getResources().getColor(R.color.black_alpha));


        DateFormat df = new SimpleDateFormat("dd MMM yy", Locale.US);
        try {
            fDate = df.parse(tvSelectedDate.getText().toString());

            final Calendar nextYear = Calendar.getInstance();
            String nextDate = new SimpleDateFormat("dd MMM yy").format(nextYear.getTime());
            cDate = df.parse(nextDate);

            //get date diff
            long difference = Math.abs(cDate.getTime() - fDate.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);
            dateoffset = Integer.parseInt(dayDifference);
            Log.e("BTAG", "fDate: " + fDate);
            Log.e("BTAG", "cDate: " + cDate);

            if (dateoffset > 0) {
                dateoffset -= 1;
            } else {
                fDate = cDate;
            }
            Log.e("BTAG", "dayDifference: " + dayDifference);

            //calendar view set
            nextYear.add(Calendar.DATE, 31 + dateoffset);

            final Calendar lastYear = Calendar.getInstance();
            lastYear.add(Calendar.DATE, 1 + dateoffset);

            calendar.setCustomDayView(new DefaultDayViewAdapter());
            Calendar today = Calendar.getInstance();
            ArrayList<Date> dates = new ArrayList<Date>();
            today.add(Calendar.DATE, 1 + dateoffset);
            dates.add(today.getTime());
            for (int i = 0; i < 29; i++) {
                today.add(Calendar.DATE, 1);
                dates.add(today.getTime());
                count = dates.size();
            }
            calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
            calendar.init(lastYear.getTime(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                    .withHighlightedDates(dates).displayOnly();

            StringBuilder selectedDated = new StringBuilder();
            for (Date date : dates) {
                String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                selectedDated.append(formateDate + ",");
            }
            Log.d("BTAG", "SELECTED DATES...." + selectedDated);
            SelectedDates = String.valueOf(selectedDated);

            tvDaily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDaily.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
                    tvDaily.setTextColor(getResources().getColor(R.color.orange));
                    tvEvery3Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery3Day.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvAlternateDay.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvAlternateDay.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery7Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery7Day.setTextColor(getResources().getColor(R.color.black_alpha));


                    calendar.setCustomDayView(new DefaultDayViewAdapter());
                    Calendar today = Calendar.getInstance();
                    ArrayList<Date> dates = new ArrayList<Date>();
                    today.add(Calendar.DATE, 1 + dateoffset);
                    dates.add(today.getTime());
                    for (int i = 0; i < 29; i++) {
                        today.add(Calendar.DATE, 1);
                        dates.add(today.getTime());
                    }
                    calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                    calendar.init(lastYear.getTime(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                            .withHighlightedDates(dates).displayOnly();

                    StringBuilder selectedDated = new StringBuilder();
                    for (Date date : dates) {
                        String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        selectedDated.append(formateDate + ",");
                        count = dates.size();
                    }
                    Log.d("BTAG", "SELECTED DATES...." + selectedDated);
                    SelectedDates = String.valueOf(selectedDated);

                }
            });

            tvEvery3Day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDaily.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvDaily.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery3Day.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
                    tvEvery3Day.setTextColor(getResources().getColor(R.color.orange));
                    tvAlternateDay.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvAlternateDay.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery7Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery7Day.setTextColor(getResources().getColor(R.color.black_alpha));

                    calendar.setCustomDayView(new DefaultDayViewAdapter());
                    Calendar today = Calendar.getInstance();
                    ArrayList<Date> dates = new ArrayList<Date>();
                    today.add(Calendar.DATE, 1 + dateoffset);
                    dates.add(today.getTime());
                    for (int i = 0; i < 9; i++) {
                        today.add(Calendar.DATE, 3);
                        dates.add(today.getTime());
                        count = dates.size();
                    }

                    calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                    calendar.init(lastYear.getTime(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                            .withHighlightedDates(dates).displayOnly();

                    StringBuilder selectedDated = new StringBuilder();
                    for (Date date : dates) {
                        String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        selectedDated.append(formateDate + ",");
                    }
                    Log.d("BTAG", "SELECTED DATES...." + selectedDated);
                    SelectedDates = String.valueOf(selectedDated);

                }
            });
            tvAlternateDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDaily.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvDaily.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery3Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery3Day.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvAlternateDay.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
                    tvAlternateDay.setTextColor(getResources().getColor(R.color.orange));
                    tvEvery7Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery7Day.setTextColor(getResources().getColor(R.color.black_alpha));

                    calendar.setCustomDayView(new DefaultDayViewAdapter());
                    Calendar today = Calendar.getInstance();
                    ArrayList<Date> dates = new ArrayList<Date>();
                    today.add(Calendar.DATE, 1 + dateoffset);
                    dates.add(today.getTime());
                    for (int i = 0; i < 14; i++) {
                        today.add(Calendar.DATE, 2);
                        dates.add(today.getTime());
                        count = dates.size();
                    }

                    calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                    calendar.init(lastYear.getTime(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                            .withHighlightedDates(dates).displayOnly();

                    StringBuilder selectedDated = new StringBuilder();
                    for (Date date : dates) {
                        String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        selectedDated.append(formateDate + ",");
                    }
                    Log.d("BTAG", "SELECTED DATES...." + selectedDated);
                    SelectedDates = String.valueOf(selectedDated);
                }
            });

            tvEvery7Day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDaily.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvDaily.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery3Day.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvEvery3Day.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvAlternateDay.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
                    tvAlternateDay.setTextColor(getResources().getColor(R.color.black_alpha));
                    tvEvery7Day.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
                    tvEvery7Day.setTextColor(getResources().getColor(R.color.orange));

                    calendar.setCustomDayView(new DefaultDayViewAdapter());
                    Calendar today = Calendar.getInstance();
                    ArrayList<Date> dates = new ArrayList<Date>();
                    today.add(Calendar.DATE, 1 + dateoffset);
                    dates.add(today.getTime());

                    for (int i = 0; i < 4; i++) {
                        today.add(Calendar.DATE, 7);
                        dates.add(today.getTime());
                        count = dates.size();
                    }

                    calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                    calendar.init(lastYear.getTime(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                            .withHighlightedDates(dates).displayOnly();

                    StringBuilder selectedDated = new StringBuilder();
                    for (Date date : dates) {
                        String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        selectedDated.append(formateDate + ",");
                    }
                    Log.d("BTAG", "SELECTED DATES...." + selectedDated);
                    SelectedDates = String.valueOf(selectedDated);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

//    private void getCoupneIdMethod() {
//        RetrofitClient.getRetrofitClient().getCoupneId(
//                cupId,
//                localData.getCustomerId()
//        ).enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//
//                        SubscribefinalAmnt = SubscribeDailyTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
//                        tvHavePromocode.setText("Code Applied Successfully...");
//
//                        showToast(response.body().getMessage());
//                        rlCouponCode.setVisibility(View.GONE);
//                        llCouponCode.setVisibility(View.VISIBLE);
//                    } else {
//                      //  SubscribefinalAmnt = SubscribeDailyTotalAmount + SubscribeDeliveryCharge + SubscribeCouponApplied;
//                        showToast(response.body().getMessage());
//                        rlCouponCode.setVisibility(View.VISIBLE);
//                        llCouponCode.setVisibility(View.GONE);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void subscribe() {
        if (isAddressSelected)
            new AlertDialog.Builder(DailySubscribeProductActivity.this)
                    .setMessage("Are you sure you want to Subscribe?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (SlotTime.equals("")) {
                                showToast("Please Select Delivery Slot Time");
                            } else {
                                Log.d("TAGD","SubscribeDeliveryCharge");
                                Double deleveryCharge = SubscribeDeliveryCharge * count;

                                Double totalAmountProductQutMulti = (SubscribeDailyTotalAmount * count);
                                totleProductprice = deleveryCharge + totalAmountProductQutMulti;
                                totalIncludingCouponAmount = totleProductprice - SubscribeCouponApplied;

                                //Time formate change
                                String[] separated = SlotTime.split("-");
                                String strTime = separated[0];
                                String endTime = separated[1];
                                String formatTime;//start time
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                                Date d = null;
                                try {
                                    d = sdf.parse(strTime);
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                formatTime = sdf1.format(d);
                                String formatTime2;//end time

                                Date d2 = null;
                                try {
                                    d2 = sdf.parse(endTime);
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                formatTime2 = sdf1.format(d2);
                                //Final Amount after applied coupon code
                                SubscribefinalAmnt = (SubscribeDailyTotalAmount + SubscribeDeliveryCharge);
                                Log.d("BTAG", "TOTAL PAYABLE AMOUNT...." + SubscribefinalAmnt);
                               // Log.d("BTAG", "Wallet amount...." + WalletAmount);

//                                if (SubscribefinalAmnt < WalletAmount) {
//                                    Log.d("BTAG", "ORDER PARAM...." + localData.getCustomerId() + "," +
//                                            AddressId + "," + SelectedDates + "," + formatTime + "," + formatTime2 + "," + SubscribeDailyTotalAmount + "," +
//                                            SubscribeCouponApplied + "," + totalAmountProductQutMulti + "," +
//                                            SubscribeDeliveryCharge + "," + totalIncludingCouponAmount + "," +
//                                            product.getSubProductList().get(subProduct).getProductId().toString() + "," + Qty);
//                                    //====Call APi FOR PLACE ORDER
//                                    MyProgress.start(DailySubscribeProductActivity.this);
//                                   // Call<DataResponse> call = RetrofitClient.getRetrofitClient().getPlacedDailySubscription(localData.getCustomerId(),
//                                            //AddressId, SelectedDates, formatTime, formatTime2, String.valueOf(SubscribeDailyTotalAmount),
//                                            //String.valueOf(SubscribeCouponApplied), String.valueOf(totalAmountProductQutMulti),
//                                            //String.valueOf(SubscribeDeliveryCharge), String.valueOf(totalIncludingCouponAmount),
//                                            //product.getSubProductList().get(subProduct).getProductId().toString(), Qty);
//
////                                    call.enqueue(new Callback<DataResponse>() {
////                                        @Override
////                                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
////                                            MyProgress.stop();
////
////                                            if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
////                                                showToast(response.body().getMessage());
////                                                finish();
////
////                                            } else {
////                                                showToast(getString(R.string.error_general));
////                                                startActivity(new Intent(DailySubscribeProductActivity.this, HomeActivity.class));
////                                                finishAffinity();
////
////                                            }
////                                        }
////
////                                        @Override
////                                        public void onFailure(Call<DataResponse> call, Throwable t) {
////                                            MyProgress.stop();
////                                            Log.d(TAG, t.getMessage());
////                                            Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
////                                        }
////                                    });
//                                } else {
//                                    //open wallet screen for adding amount
//                                    showToast("You need to add wallet balance");
//                                    showWalletAmountDialog(totalIncludingCouponAmount - WalletAmount);
//                                }
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        else
            showToast("Please enter select Address");
    }


    //Product List
    private void getSlotList() {
        MyProgress.start(DailySubscribeProductActivity.this);
        Call<SlotListDataResponse> call = RetrofitClient.getRetrofitClient().getSlotList(localData.getCustomerId());
        call.enqueue(new Callback<SlotListDataResponse>() {
            @Override
            public void onResponse(Call<SlotListDataResponse> call, Response<SlotListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    setUpSlotList(response.body().getData().getSlotList());
                } else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SlotListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSlotList(ArrayList<SlotList> slotList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DailySubscribeProductActivity.this);
        rvSlotList.setLayoutManager(layoutManager);
        rvSlotList.setAdapter(new SlotListAdapter(this, slotList, DailySubscribeProductActivity.this));
    }

    @Override
    public void onItemClick(SlotList slot) {

    }

    private void showWalletAmountDialog(final double minAmt) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_wallet_amount);

        Button btnAdd = dialog.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        final EditText edtOtp = dialog.findViewById(R.id.edt_add_wallet_amount);
        if (llCouponCode.getVisibility() == View.VISIBLE) {
            edtOtp.setHint("Enter minimum " + new DecimalFormat("##.##").format(minAmt));
        } else {
           // Double am = totleProductprice - WalletAmount;
           // edtOtp.setHint("Enter minimum " + new DecimalFormat("##.##").format(am));
        }
        Button btnProceed = dialog.findViewById(R.id.btn_add);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = edtOtp.getText().toString();
                if (TextUtils.isEmpty(amount)) {
                    showToast("Please enter Amount");
                } else {

                    newWalletAmt = Double.parseDouble(edtOtp.getText().toString());

                    if (newWalletAmt >= minAmt){
                        startPayment(newWalletAmt);
                    }else {
                        showToast("Add Minimum Amount" + minAmt);
                    }

                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }

    public void startPayment(double amount) {

        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_BJhpLshlVxMi9A");
        checkout.setKeyID("rzp_live_0NoVzbVRnK4EUy");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.final_logo);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", localData.getSignIn().getName());
            options.put("description", "throtel Grocery");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", Math.round(OrderId));//from response of order placed api.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount * 100);//pass amount in currency subunits
            options.put("prefill.email", localData.getSignIn().getEmail());
            options.put("prefill.contact", localData.getSignIn().getPhone());
            checkout.open(this, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    private void callAddWalletAmountAPI(String paymentID) {
        //WalletAmount;

        MyProgress.start(this);

//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().AddWalletAmount(localData.getCustomerId(), String.valueOf(newWalletAmt), paymentID,
//                "UPI", "Success", "For Daily Subscription");
//
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//                if (response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        showToast(response.body().getMessage());
//                        WalletAmount = Double.parseDouble(localData.getWalletAmount()) + newWalletAmt;
//                        localData.setWalletAmount(String.valueOf(WalletAmount));
//                        subscribe();
//                    } else {
//                        showToast(getString(R.string.error_general));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    //======get Active Address of customer=====
    private void getActiveAddress() {
        // MyProgress.start(DailySubscribeProductActivity.this);

        Call<AddressListDataResponse> call = RetrofitClient.getRetrofitClient().getAddressList(localData.getCustomerId());

        call.enqueue(new Callback<AddressListDataResponse>() {
            @Override
            public void onResponse(Call<AddressListDataResponse> call, Response<AddressListDataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        for (AddressList addressData : response.body().getData().getAddressList()) {
                            if (addressData.getStatus().equals("Active")) {
                                AddressId = addressData.getAddressId().toString();
                                tvCName.setText(addressData.getName());
                                tvCMobile.setText(addressData.getPhone());
                                tvFlatNo.setText(addressData.getFlatOrHouseNumber() + "," + addressData.getStreetOrSocietyName() + "," + addressData.getLandmark());
//                                tvPincode.setText(addressData.getCityName() + " - " + addressData.getPincodeNumber());
//                                getCheckDeliveryCharge(addressData.getPincodeNumber());
                                isAddressSelected = true;
                            }
                        }

                    } else {
                        showToast(response.body().getMessage());
                        isAddressSelected = false;
                        Intent ca = new Intent(DailySubscribeProductActivity.this, AddressListActivity.class);
                        ca.putExtra("changefor", "Subscribe");
                        startActivityForResult(ca, REQUEST_CODE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("TAG", t.getMessage());
                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Check Delivery CHarge...
//    private void getCheckDeliveryCharge(String pinCode) {
//        // MyProgress.start(DailySubscribeProductActivity.this);
//        Call<DeliveryChargeDataResponse> call = RetrofitClient.getRetrofitClient().getDeliveryCharge(pinCode, localData.getCustomerId());
//        call.enqueue(new Callback<DeliveryChargeDataResponse>() {
//            @Override
//            public void onResponse(Call<DeliveryChargeDataResponse> call, Response<DeliveryChargeDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    //showToast(response.body().getMessage());
//                    if (SubscribeDailyTotalAmount < response.body().getData().getDeliveryChargeDetail().getMaxPrice()) {
//                        SubscribeDeliveryCharge = response.body().getData().getDeliveryChargeDetail().getDeliveryPrice();
//                        Log.d("BATG", "Delivery Charge Applied..." + SubscribeDeliveryCharge);
//                    } else {
//                        SubscribeDeliveryCharge = 0.0;
//                    }
//
//                } else {
//                    showToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DeliveryChargeDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void getWalletDetails() {
//        //MyProgress.start(DailySubscribeProductActivity.this);
//        Call<WalletDetailDataResponse> call = RetrofitClient.getRetrofitClient().getWalletDetailsData(localData.getCustomerId());
//        call.enqueue(new Callback<WalletDetailDataResponse>() {
//            @Override
//            public void onResponse(Call<WalletDetailDataResponse> call, Response<WalletDetailDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    WalletAmount = response.body().getData().getWalletDetail().getWalletAmount();
//                } else {
//                    Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WalletDetailDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(DailySubscribeProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getActiveAddress();

    }

    @Override
    public void onPaymentSuccess(String s) {
        callAddWalletAmountAPI(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        showToast(s);
    }
}
