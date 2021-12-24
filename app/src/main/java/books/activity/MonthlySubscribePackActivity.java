package books.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.throtel.grocery.R;

import books.adapter.SlotListAdapter;
import books.api.RetrofitClient;
import books.models.AddressList;
import books.models.AddressListDataResponse;
import books.models.SlotList;
import books.models.SlotListDataResponse;
import books.models.TransactionChargeDetail;
import books.utils.NetworkUtil;
import books.utils.Utils;
import books.views.MyProgress;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static books.fragments.BaseFragment.OrderType;


public class MonthlySubscribePackActivity extends BaseActivity implements SlotListAdapter.OnItemClickListener, PaymentResultListener {

    private static final String TAG = MonthlySubscribePackActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    static int dateoffset = 0;
    final DecimalFormat numberFormat = new DecimalFormat("#.00");
    TextView tv1Month, tv2Month, tv3Month;
    Date fDate, cDate;
    String PackId, OrderId;
    String orderPaymentId;
    Double num;
    private String cupId;
    private RecyclerView rvSlotList;
    private TextView tvSelectedDate;
    private TextView tvSellingPrice, tvNetAmount, tvSavedAmount, tvDeliveryCharge, tvTransactionCharge, tvTotalAmount;
    private TextView tvCName, tvType, tvFlatNo, tvCMobile, tvPincode, tvChangeAddress, tvViewPack;
    private LinearLayout btnSubscribe, llSelectDate, llCouponCode;
    private EditText edtCouponCode;
    private TextView tvHavePromocode;
    private Button btnCodeApply;
    private RelativeLayout rlCouponCode;
    private Double SubscribeDeliveryCharge = 0.0;
    private Double SubscribeTotalAmount = 0.0;
    private Double SubscribeNetAmount = 0.0;
    private Double SubscribeCouponApplied = 0.0;
    private Double SubscribefinalAmnt = 0.0;
    public  Double rayzerpayFinalAmount = 0.0;
    private Double twoMonthFinalAmount = 0.0;
    private final Double oneMonthFinalAmount = 0.0;
    private Double threeMonthFinalAmount = 0.0;
    private String SelectedDates, OrderType;
    private boolean isAddressSelected = false;
    private String flag = "1";
    Double serviceCharge = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_monthly_pack);
        setUpToolbarBackButton("Subscribe");
        Checkout.preload(getApplicationContext());
        SlotTime = "";
        initView();

        if (getIntent() != null) {

            SubscribeTotalAmount = getIntent().getDoubleExtra("total", 0.0);
            SubscribeNetAmount = getIntent().getDoubleExtra("totalnet", 0.0);
            OrderType = getIntent().getStringExtra("type");
            //PackId = getIntent().getIntExtra("packid",0);
            PackId = getIntent().getStringExtra("packid");
            Log.d("BTAG", "PACK ID...." + PackId);
            setData();
        }

        if (NetworkUtil.getConnectivityStatus(MonthlySubscribePackActivity.this)) {
            getSlotList();
            getActiveAddress();

        } else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        btnSubscribe = findViewById(R.id.ll_subscribe);
        llSelectDate = findViewById(R.id.ll_date_select);
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        tvNetAmount = findViewById(R.id.tv_net_price);
        tvSellingPrice = findViewById(R.id.tv_sell_price);
        tvSavedAmount = findViewById(R.id.tv_saved_price);
        tvDeliveryCharge = findViewById(R.id.tv_delivery_charge);
        tvTransactionCharge = findViewById(R.id.tv_ss_charge);

        llCouponCode = findViewById(R.id.layout_coupon);
        rlCouponCode = findViewById(R.id.rvcode_apply);
        tvCName = findViewById(R.id.tv_name);
        tvCMobile = findViewById(R.id.tv_mobile);
        tvType = findViewById(R.id.tv_type);
        tvFlatNo = findViewById(R.id.tv_flat_no);
        tvPincode = findViewById(R.id.tv_pincode);
        tvChangeAddress = findViewById(R.id.tv_change);
        tvTotalAmount = findViewById(R.id.tv_total_amt);

        tvHavePromocode = findViewById(R.id.tv_have_promo_code);
        edtCouponCode = findViewById(R.id.edt_coupon_code);
        btnCodeApply = findViewById(R.id.btn_apply);
        rvSlotList = findViewById(R.id.rv_slot_list);

        tv1Month = findViewById(R.id.tv_1month);
        tv2Month = findViewById(R.id.tv_2month);
        tv3Month = findViewById(R.id.tv_3month);

        tvViewPack = findViewById(R.id.tv_view_pack);

        getTransactionServiceCharge();

        tvViewPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    /*    tvSellingPrice.setText(String.format("₹ %.2f", SubscribeTotalAmount));

        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", SubscribeTotalAmount));

        String val = String.format("%.2f", SubscribeNetAmount);
        tvNetAmount.setText(Html.fromHtml("<strike> ₹ " + val + "</strike>"));
        tvSavedAmount.setText(String.format("₹ %.2f", (SubscribeNetAmount - SubscribeTotalAmount)));
        tvTransactionCharge.setText(String.format("₹ %.2f", (SubscribeTotalAmount * 2) / 100));
        // tvTotalAmount.setText( " ₹ " + new DecimalFormat("##.##").format(SubscribefinalAmnt));

        //numberFormat.format(SubscribefinalAmnt = SubscribeTotalAmount);
        SubscribefinalAmnt = SubscribeTotalAmount;
*/
        tvSellingPrice.setText(String.format("₹ %.2f", SubscribeTotalAmount));
        String val = String.format("%.2f", SubscribeNetAmount);
        tvNetAmount.setText(Html.fromHtml("<strike> ₹ " + val + "</strike>"));
        tvSavedAmount.setText(String.format("₹ %.2f", (SubscribeNetAmount - SubscribeTotalAmount)));

        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", SubscribeTotalAmount));
        tvDeliveryCharge.setText("Delivery Charge: + ₹ " + SubscribeDeliveryCharge);
        tvTransactionCharge.setText(String.format("₹ %.2f", ((SubscribeDeliveryCharge + SubscribeTotalAmount - SubscribeCouponApplied) * serviceCharge) / 100));
        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", SubscribefinalAmnt));
        rayzerpayFinalAmount = SubscribefinalAmnt;

        llSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MonthlySubscribePackActivity.this,
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

                Intent ca = new Intent(MonthlySubscribePackActivity.this, books.activity.AddressListActivity.class);
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
        btnCodeApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv2Month.isSelected()) {
                    tv1Month.setSelected(true);
                }

                if (TextUtils.isEmpty(edtCouponCode.getText().toString())) {
                    showToast("Please Enter Code");

                } else {
                    MyProgress.start(MonthlySubscribePackActivity.this);
//                    Call<CouponCodeDataResponse> call = RetrofitClient.getRetrofitClient().getCouponApplyData(edtCouponCode.getText().toString(), localData.getCustomerId());
//                    call.enqueue(new Callback<CouponCodeDataResponse>() {
//                        @Override
//                        public void onResponse(Call<CouponCodeDataResponse> call, Response<CouponCodeDataResponse> response) {
//                            MyProgress.stop();
//                            if (response.body() != null) {
//                                if (response.body().getStatus().equalsIgnoreCase("true")) {
//                                    //        showToast(response.body().getMessage());
//
//                                    if (SubscribeTotalAmount > response.body().getData().getCouponDetail().getMaxPrice()) {
//                                        cupId = response.body().getData().getCouponDetail().getCouponId().toString();
//                                        getCoupneIdMethod();
//
//                                    //    tvHavePromocode.setText("Code Applied Successfully...");
//                                      //  SubscribeCouponApplied = response.body().getData().getCouponDetail().getCouponPrice().doubleValue();
//                                          //  rlCouponCode.setVisibility(View.GONE);
//                                          //  llCouponCode.setVisibility(View.VISIBLE);
//                                            tvHavePromocode.setText("Code Applied Successfully...");
//                                            SubscribeCouponApplied = response.body().getData().getCouponDetail().getCouponPrice().doubleValue();
//
//
//                                        Log.d("TAG", "CupId" + cupId);
//                                        Log.d("BATG", "Coupon Code Applied..." + SubscribeCouponApplied);
//                                        //Final Amount after applied coupon code
////                                        SubscribefinalAmnt = SubscribeTotalAmount + SubscribeDeliveryCharge;
////                                        SubscribefinalAmnt = SubscribefinalAmnt + (SubscribeTotalAmount * 2) / 100;
////                                      tvTransactionCharge.setText(String.format("₹ %.2f", (SubscribefinalAmnt * 2) / 100));
//
//                                        //
////                                        rayzerpayFinalAmount = SubscribeTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
////                                        tvNetAmount.setText(String.valueOf(rayzerpayFinalAmount));
//                                     /*   if (flag == "1") {
//                                            applyOneMonthPrice();
//                                        } else if (flag == "2") {
//                                            applyTwoMonthPrice();
//                                        } else if (flag == "3") {
//                                            applyThreeMonthPrice();
//                                        }*/
//
//                                        Log.i("TAG", "SubscribefinalAmnt");
//                                        // getActiveAddress();
//
//
//                                    } else {
//                                        // tv.setText("- ₹ " + 0.00);
//                                        rlCouponCode.setVisibility(View.VISIBLE);
//                                        llCouponCode.setVisibility(View.GONE);
//                                        showToast("Order amount is minimum than Coupon amount");
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
//                            Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAddressSelected)

                    new AlertDialog.Builder(MonthlySubscribePackActivity.this)
                            .setMessage("Are you sure you want to Subscribe?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (SlotTime.equals("")) {
                                        showToast("Please Select Delivery Slot Time");
                                    } else {
                                        Double totalPriceAfterExcludingCoupanAmount = SubscribeTotalAmount - SubscribeCouponApplied;
                                        Double totalPriceAfterIncludingDeliveryAmount = totalPriceAfterExcludingCoupanAmount + SubscribeDeliveryCharge;
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
                                        //   SubscribefinalAmnt = SubscribeTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
                                        Log.d("BTAGS", "TOTAL PAYABLE AMOUNTT...." + SubscribefinalAmnt);
                                        //  SubscribefinalAmnt = SubscribefinalAmnt + (SubscribefinalAmnt * 2) / 100;
                                        //  num = numberFormat.format(SubscribefinalAmnt);
                                        Log.d("BTAG", "TOTAL PAYABLE AMOUNT...." + SubscribefinalAmnt);

                                        rayzerpayFinalAmount = SubscribefinalAmnt;
                                        Log.d("BTAG", "ORDER PARAM...." + PackId + "," + OrderType + "," + localData.getCustomerId() + "," +
                                                AddressId + "," + SelectedDates + "," + formatTime + "," + formatTime2 + "," + SubscribeTotalAmount + "," +
                                                SubscribeCouponApplied + "," + totalPriceAfterExcludingCoupanAmount + "," +
                                                SubscribeDeliveryCharge + "," + totalPriceAfterIncludingDeliveryAmount + "," + rayzerpayFinalAmount);
                                        //====Call APi FOR PLACE ORDER====
                                        MyProgress.start(MonthlySubscribePackActivity.this);
//                                        Call<SubscribeOrderPlaceDataResponse> call = RetrofitClient.getRetrofitClient().getPlacedMonthlySubscription(
//                                                PackId,
//                                                OrderType,
//                                                localData.getCustomerId(),
//                                                AddressId,
//                                                SelectedDates,
//                                                formatTime,
//                                                formatTime2,
//                                                String.valueOf(SubscribeTotalAmount),
//                                                String.valueOf(SubscribeCouponApplied),
//                                                String.valueOf(totalPriceAfterExcludingCoupanAmount),
//                                                String.valueOf(SubscribeDeliveryCharge),
//                                                String.valueOf(totalPriceAfterIncludingDeliveryAmount),
//                                                "",
//                                                String.valueOf(rayzerpayFinalAmount));


//                                        call.enqueue(new Callback<SubscribeOrderPlaceDataResponse>() {
//                                            @Override
//                                            public void onResponse(Call<SubscribeOrderPlaceDataResponse> call, Response<SubscribeOrderPlaceDataResponse> response) {
//                                                MyProgress.stop();
//
//                                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                                                    showToast(response.body().getMessage());
//                                                    Log.d("BTAG", "Order ID..." + response.body().getData().getOrderDetail().getOrderId());
//                                                    OrderId = String.valueOf(response.body().getData().getOrderDetail().getOrderId());
//                                                    Log.d("PTAG", "ORDERPAY ID...." + orderPaymentId);
//                                                    orderPaymentId = response.body().getData().getOrderDetail().getOrderPaymentId();
//
//                                                    startPayment();
//
//                                                } else {
//                                                    showToast(getString(R.string.error_general));
//                                                    startActivity(new Intent(MonthlySubscribePackActivity.this, HomeActivity.class));
//                                                    finishAffinity();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call<SubscribeOrderPlaceDataResponse> call, Throwable t) {
//                                                MyProgress.stop();
//                                                Log.d(TAG, t.getMessage());
//                                                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
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
        });
        getThreeMonth();
        getTwoMothnlyPrice();
        getMothnlyPrice();
    }

     //  payment Transaction Service Charge
    private void getTransactionServiceCharge() {
      //  MyProgress.start(MonthlySubscribePackActivity.this);

//        Call<TransactionDataResponse> call = RetrofitClient.getRetrofitClient().getTransactionData();
//
//        call.enqueue(new Callback<TransactionDataResponse>() {
//            @Override
//            public void onResponse(Call<TransactionDataResponse> call, Response<TransactionDataResponse> response) {
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                   // tvTransactionCharge.getText().toString().trim().contains("Credit / Debit / ATM Card");
//                        serviceCharge = response.body().getData().getTransactionChargeDetail().getDebitCard();
//                       // paymentMethod = "DC";
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TransactionDataResponse> call, Throwable t) {
//
//            }
//        });
    }
    private void getMothnlyPrice() {
        //====== =======DEFAULT 1 Month CODE=======
        tv1Month.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
        tv1Month.setTextColor(getResources().getColor(R.color.orange));
        tv2Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv2Month.setTextColor(getResources().getColor(R.color.black_alpha));
        tv3Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv3Month.setTextColor(getResources().getColor(R.color.black_alpha));

        SelectedDates = Utils.convertDate(tvSelectedDate.getText().toString(), "dd MMM yy", "yyyy-MM-dd");
        Log.d("BTAG", "1Months:SELECTED DATES...." + SelectedDates);

        tv1Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTransactionServiceCharge();
                applyOneMonthPrice();
            }
        });
    }

    private void applyOneMonthPrice() {

        TransactionChargeDetail transactionChargeDetail = new TransactionChargeDetail();

        tv1Month.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
        tv1Month.setTextColor(getResources().getColor(R.color.orange));
        tv2Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv2Month.setTextColor(getResources().getColor(R.color.black_alpha));
        tv3Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv3Month.setTextColor(getResources().getColor(R.color.black_alpha));
        SelectedDates = Utils.convertDate(tvSelectedDate.getText().toString(), "dd MMM yy", "yyyy-MM-dd");
        Log.d("BTAG", "1Months:SELECTED DATES...." + SelectedDates);
        flag = "1";

        //  Double amt = SubscribeTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
        //  Double amt = SubscribeTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
        tvDeliveryCharge.setText("Delivery Charge: + ₹ " + SubscribeDeliveryCharge);
        //tvTransactionCharge.setText(serviceCharge.toString());
        tvTransactionCharge.setText(String.format("₹ %.2f", ((SubscribeDeliveryCharge + SubscribeTotalAmount - SubscribeCouponApplied) * serviceCharge) / 100));
        rayzerpayFinalAmount = SubscribefinalAmnt;
        Double finalTransAmount = ((((SubscribeDeliveryCharge + SubscribeTotalAmount)) - SubscribeCouponApplied) * serviceCharge) / 100;
        Log.d("TAGD","finalTransAmount......"+finalTransAmount);
        Double FinalAmountWithCoupon = ((SubscribeDeliveryCharge + SubscribeTotalAmount)) - SubscribeCouponApplied;
        Log.d("TAGD","FinalAmountWithCoupon......"+FinalAmountWithCoupon);
        rayzerpayFinalAmount = finalTransAmount + FinalAmountWithCoupon;
        Log.d("TAGD","rayzerpayFinalAmount......"+rayzerpayFinalAmount);
        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", finalTransAmount + FinalAmountWithCoupon));

        //   Log.d("BTAG", "One Month Total Amount ₹ ........" + SubscribefinalAmnt);
        Log.d("BTAG", "One Month Total Amount ₹ ........" + SubscribefinalAmnt);
    }


    private void getTwoMothnlyPrice() {

        tv2Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyTwoMonthPrice();
            }
        });
    }

    private void applyTwoMonthPrice() {
        tv1Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv1Month.setTextColor(getResources().getColor(R.color.black_alpha));
        tv2Month.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
        tv2Month.setTextColor(getResources().getColor(R.color.orange));
        tv3Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv3Month.setTextColor(getResources().getColor(R.color.black_alpha));

        // for 2 month.....................
        tvDeliveryCharge.setText("Delivery Charge: + ₹ " + SubscribeDeliveryCharge * 2);
        tvTransactionCharge.setText(String.format("₹ %.2f", ((((SubscribeDeliveryCharge + SubscribeTotalAmount) * 2) - SubscribeCouponApplied) * serviceCharge) / 100));
        Double finalTransAmount = ((((SubscribeDeliveryCharge + SubscribeTotalAmount) * 2) - SubscribeCouponApplied) * serviceCharge) / 100;
        Double FinalAmountWithCoupon = ((SubscribeDeliveryCharge + SubscribeTotalAmount) * 2) - SubscribeCouponApplied;
        flag = "2";

        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", finalTransAmount + FinalAmountWithCoupon));
        rayzerpayFinalAmount = finalTransAmount + FinalAmountWithCoupon;
        twoMonthFinalAmount = rayzerpayFinalAmount;
        Log.d("BTAG", "Two Month Total Amount ₹ ........" + SubscribefinalAmnt);
        // tvTotalAmount = SubscribefinalAmnt*2;

        try {
            DateFormat df = new SimpleDateFormat("dd MMM yy", Locale.US);
            fDate = df.parse(tvSelectedDate.getText().toString());

            final Calendar nextYear = Calendar.getInstance();
            String curDate = new SimpleDateFormat("dd MMM yy").format(nextYear.getTime());
            cDate = df.parse(curDate);

            //get date diff
            long difference = Math.abs(cDate.getTime() - fDate.getTime());
            dateoffset = (int) (difference / (24 * 60 * 60 * 1000));

            Calendar today = Calendar.getInstance();
            ArrayList<Date> dates = new ArrayList<Date>();
            today.add(Calendar.DATE, dateoffset);
            dates.add(today.getTime());
            today.add(Calendar.MONTH, 1);
            dates.add(today.getTime());

            StringBuilder selectedDated = new StringBuilder();
            for (Date date : dates) {
                String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                selectedDated.append(formateDate + ",");
            }
            Log.d("BTAG", "2Months:SELECTED DATES...." + selectedDated);
            SelectedDates = String.valueOf(selectedDated);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getThreeMonth() {
        tv3Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyThreeMonthPrice();
            }
        });
    }

    private void applyThreeMonthPrice() {
        tv1Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv1Month.setTextColor(getResources().getColor(R.color.black_alpha));
        tv2Month.setBackground(getResources().getDrawable(R.drawable.round_corner_grey_outline));
        tv2Month.setTextColor(getResources().getColor(R.color.black_alpha));
        tv3Month.setBackground(getResources().getDrawable(R.drawable.round_corner_oragnge_ouline));
        tv3Month.setTextColor(getResources().getColor(R.color.orange));

        // for 3 month.....................
        tvDeliveryCharge.setText("Delivery Charge: + ₹ " + SubscribeDeliveryCharge * 3);
        tvTransactionCharge.setText(String.format("₹ %.2f", ((((SubscribeDeliveryCharge + SubscribeTotalAmount) * 3) - SubscribeCouponApplied) * serviceCharge) / 100));
        //    tvTotalAmount.setText("Total Amount ₹ " + new DecimalFormat("##.##").format(amthree*3 + SubscribeCouponApplied));
        Double finalTransAmount = ((((SubscribeDeliveryCharge + SubscribeTotalAmount) * 3) - SubscribeCouponApplied) * serviceCharge) / 100;
        Double FinalAmountWithCoupon = ((SubscribeDeliveryCharge + SubscribeTotalAmount) * 3) - SubscribeCouponApplied;
        rayzerpayFinalAmount = finalTransAmount + FinalAmountWithCoupon;
        tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", finalTransAmount + FinalAmountWithCoupon));
        flag = "3";
        threeMonthFinalAmount = rayzerpayFinalAmount;
        Log.d("BTAG", "Three Month Total Amount ₹ ..." + SubscribefinalAmnt * 3 + SubscribeCouponApplied + SubscribeCouponApplied);
        try {
            DateFormat df = new SimpleDateFormat("dd MMM yy", Locale.US);
            fDate = df.parse(tvSelectedDate.getText().toString());

            final Calendar nextYear = Calendar.getInstance();
            String curDate = new SimpleDateFormat("dd MMM yy").format(nextYear.getTime());
            cDate = df.parse(curDate);

            //get date diff
            long difference = Math.abs(cDate.getTime() - fDate.getTime());
            dateoffset = (int) (difference / (24 * 60 * 60 * 1000));

            Calendar today = Calendar.getInstance();
            ArrayList<Date> dates = new ArrayList<Date>();
            today.add(Calendar.DATE, dateoffset);
            dates.add(today.getTime());
            today.add(Calendar.MONTH, 1);
            dates.add(today.getTime());
            today.add(Calendar.MONTH, 1);
            dates.add(today.getTime());

            StringBuilder selectedDated = new StringBuilder();
            for (Date date : dates) {
                String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                selectedDated.append(formateDate + ",");
            }
            Log.d("BTAG", "3Months:SELECTED DATES...." + selectedDated);
            SelectedDates = String.valueOf(selectedDated);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    private void getCoupneIdMethod() {
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getCoupneId(
//                cupId,
//                localData.getCustomerId());
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                if (response.isSuccessful()) {
//
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                      /*  couponReponse = true;
//                        coupneMsg = response.body().getMessage();*/
//                        rlCouponCode.setVisibility(View.GONE);
//                        llCouponCode.setVisibility(View.VISIBLE);
//                       showToast(response.body().getMessage());
//                        if (flag == "1") {
//                            applyOneMonthPrice();
//                        } else if (flag == "2") {
//                            applyTwoMonthPrice();
//                        } else if (flag == "3") {
//                            applyThreeMonthPrice();
//                        }
//                    } else {
//                     /*   couponReponse = false;
//                        coupneMsg = response.body().getMessage();*/
//                       showToast(response.body().getMessage());
//                        rlCouponCode.setVisibility(View.VISIBLE);
//                        llCouponCode.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    //Product List
    private void getSlotList() {
        MyProgress.start(MonthlySubscribePackActivity.this);
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
                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSlotList(ArrayList<SlotList> slotList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MonthlySubscribePackActivity.this);
        rvSlotList.setLayoutManager(layoutManager);
        rvSlotList.setAdapter(new SlotListAdapter(this, slotList, MonthlySubscribePackActivity.this));
    }

    @Override
    public void onItemClick(SlotList slot) {

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
                                tv1Month.setSelected(true);
                                isAddressSelected = true;
                            }
                        }

                    } else {
                        showToast(response.body().getMessage());
                        isAddressSelected = false;
                        Intent ca = new Intent(MonthlySubscribePackActivity.this, AddressListActivity.class);
                        ca.putExtra("changefor", "Subscribe");
                        startActivityForResult(ca, REQUEST_CODE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("TAG", t.getMessage());
                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Check Delivery Charge...
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
//
//
//                    if (SubscribeTotalAmount < response.body().getData().getDeliveryChargeDetail().getMaxPrice()) {
//
//                        SubscribeDeliveryCharge = response.body().getData().getDeliveryChargeDetail().getDeliveryPrice();
//                        tvDeliveryCharge.setText("Delivery Charge: + ₹ " + SubscribeDeliveryCharge);
//                        Log.d("BATG", "Delivery Charge Applied..." + SubscribeDeliveryCharge);
//
//                    }else {
//                        SubscribeDeliveryCharge = 0.0;
//                    }
//                    SubscribefinalAmnt = SubscribeTotalAmount + SubscribeDeliveryCharge - SubscribeCouponApplied;
//
//                    tvTransactionCharge.setText(String.format("₹ %.2f", (SubscribefinalAmnt * serviceCharge) / 100));
//                    SubscribefinalAmnt = SubscribefinalAmnt + (SubscribefinalAmnt * serviceCharge) / 100;
//                    // tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", SubscribefinalAmnt));
//                    tvTotalAmount.setText(String.format("Total Amount ₹ %.2f", SubscribefinalAmnt));
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
//                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    //Razor Pay api call
    public void startPayment() {

        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_BJhpLshlVxMi9A");
        checkout.setKeyID("rzp_live_0NoVzbVRnK4EUy");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.final_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        Log.d("BTAG", "TOTAL AMOUNT..." + SubscribefinalAmnt);
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", localData.getSignIn().getName());
            options.put("description", "throtel Grocery");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", Math.round(OrderId));//from response of order placed api.
            options.put("order_id", orderPaymentId);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Math.round(rayzerpayFinalAmount) * 100);//pass amount in currency subunits
            options.put("prefill.email", localData.getSignIn().getEmail());
            options.put("prefill.contact", localData.getSignIn().getPhone());

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // After successful payment Razorpay send back a unique id
        // Toast.makeText(PaymentOptionsActivity.this, "Transaction Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();
        //getCheckOrderPayment(razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int i, String error) {
        // Error message
        Toast.makeText(MonthlySubscribePackActivity.this, "Transaction unsuccessful Plz Try Again Later... " + error, Toast.LENGTH_LONG).show();
        startActivity(new Intent(MonthlySubscribePackActivity.this, HomeBookActivity.class));
        finishAffinity();
    }

    //Order Payment.....
//    private void getCheckOrderPayment(String paymentID) {
//        MyProgress.start(MonthlySubscribePackActivity.this);
//        Log.d("BTAG", "PARAM ORDER PAYMENT..." + localData.getCustomerId() +
//                paymentID + OrderId + "UPI" + "Success");
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getMonthlyOrderPayment(localData.getCustomerId(), paymentID,
//                OrderId, "UPI", "Success");
//
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    showToast(response.body().getMessage());
//                    Log.d("BTAG", "Order Status..." + response.body().getMessage());
//                    startActivity(new Intent(MonthlySubscribePackActivity.this, HomeActivity.class));
//                    finishAffinity();
//
//                } else {
//                    showToast(getString(R.string.error_general));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(MonthlySubscribePackActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getActiveAddress();

    }
}
