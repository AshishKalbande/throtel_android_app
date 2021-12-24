package com.throtel.grocery.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CoupneCodeDataResponse;
import com.throtel.grocery.models.CouponCodeDataResponse;
import com.throtel.grocery.models.CouponDetail;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.OrderDataResponse;
import com.throtel.grocery.models.TransactionDataResponse;
import com.throtel.grocery.utils.Utils;
import com.throtel.grocery.views.MyProgress;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.throtel.grocery.fragments.BaseFragment.BulkTotalAmount;
//import static com.throtel.grocery.fragments.BaseFragment.OrderType;
import static com.throtel.grocery.fragments.BaseFragment.RegularTotalAmount;
import static com.throtel.grocery.fragments.BaseFragment.TotalAmount;

public class PaymentOptionsActivity extends BaseActivity implements PaymentResultListener {
    private static final String TAG = PaymentOptionsActivity.class.getSimpleName();
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    Double serviceCharge = 0.0;
    Double finalAmnt = 0.0;
    Double OrderId = 0.0;
    String paymentMethod;
    String orderPaymentId;
    String cupId;
    private TextView tvNetPrice, tvDeliveryCharge, tvServiceCharge, tvCouponApplyPrice, tvAmountPayable;
    private RadioButton rbUPI, rbWallets, rbCreditCard, rbNetBanking, rbCOD, rbDebitCard;
    private Button btnAmount, btnContinue;
    private LinearLayout llCouponCode;
    private EditText edtCouponCode;
    private TextView tvHavePromocode;
    private Button btnCodeApply;
    private RelativeLayout rlCouponCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        CouponApplied = 0.0;
        setUpToolbarBackButton("Payments");
        initViews();
        setData();
        /**
         * Preload payment resources
         */
        Checkout.preload(getApplicationContext());

    }

    private void initViews() {

        llCouponCode = findViewById(R.id.layout_coupon);
        rlCouponCode = findViewById(R.id.rvcode_apply);
        tvHavePromocode = findViewById(R.id.tv_have_promo_code);
        edtCouponCode = findViewById(R.id.edt_coupon_code);
        btnCodeApply = findViewById(R.id.btn_apply);

        radioGroup = findViewById(R.id.radioGroup);
        tvNetPrice = findViewById(R.id.tv_net_price);
        tvDeliveryCharge = findViewById(R.id.tv_delivey_charge);
        tvServiceCharge = findViewById(R.id.tv_service_charge);
        tvCouponApplyPrice = findViewById(R.id.tv_coupon_charge);
        tvAmountPayable = findViewById(R.id.tv_amount_payable);

        rbCreditCard = findViewById(R.id.rb_select_creditCard);
        rbDebitCard=findViewById(R.id.rb_select_debitCard);
        rbCOD = findViewById(R.id.rb_select_cashOnDelivery);
        rbNetBanking = findViewById(R.id.rb_select_internetBanking);
        rbUPI = findViewById(R.id.rb_select_upi);
        rbWallets = findViewById(R.id.rb_select_wallet);




        edtCouponCode = findViewById(R.id.edt_coupon_code);
        btnCodeApply = findViewById(R.id.btn_apply);
//        btnAmount = findViewById(R.id.btn_amount);
        btnContinue = findViewById(R.id.btn_continue);
        TotalAmount = RegularTotalAmount;
//        if (OrderType == 0) {
//            TotalAmount = RegularTotalAmount;
//        }
//        else if (OrderType == 1) {
//            TotalAmount = BulkTotalAmount;
//        }
        Double amt = TotalAmount + DeliveryCharge - CouponApplied;
        finalAmnt = amt + ((amt * serviceCharge) / 100);
        tvAmountPayable.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmnt)));
//        btnAmount.setText(String.valueOf(new DecimalFormat("##.##").format(finalAmnt)));

        llCouponCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rlCouponCode.setVisibility(View.VISIBLE);
                llCouponCode.setVisibility(View.GONE);
            }
        });

        btnCodeApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtCouponCode.getText().toString())) {
                    showToast("Please Enter Code");
                } else {
                    MyProgress.start(PaymentOptionsActivity.this);
                    Call<CouponCodeDataResponse> call = RetrofitClient.getRetrofitClient().getCouponApplyData(edtCouponCode.getText().toString(), localData.getCustomerId());
                    call.enqueue(new Callback<CouponCodeDataResponse>() {
                        @Override
                        public void onResponse(Call<CouponCodeDataResponse> call, Response<CouponCodeDataResponse> response) {
                            MyProgress.stop();

                            if (response.body() != null) {
                                if (response.body().getStatus().equalsIgnoreCase("true")) {
                                    //showToast(response.body().getMessage());
//                                    if (OrderType == 0) {
//                                        TotalAmount = RegularTotalAmount;
//                                    }
//                                    else if (OrderType == 1) {
//                                        TotalAmount = BulkTotalAmount;
//                                    }
                                    if (TotalAmount > response.body().getData().getCouponDetail().getMaxPrice()) {
                                        cupId = response.body().getData().getCouponDetail().getCouponId().toString();
                                        getCoupneIdMethod();

                                        tvHavePromocode.setText("Code Applied Successfully...");
                                        CouponApplied = response.body().getData().getCouponDetail().getCouponPrice().doubleValue();
                                   /*     rlCouponCode.setVisibility(View.GONE);
                                        llCouponCode.setVisibility(View.VISIBLE);
                                        tvHavePromocode.setText("Code Applied Successfully...");
                                        CouponApplied = response.body().getData().getCouponDetail().getCouponPrice().doubleValue();*/
                                        //tvCouponApplyPrice.setText("- ₹ " + new DecimalFormat("##.##").format(response.body().getData().getCouponDetail().getCouponPrice()));

                                      /*Double amt = TotalAmount + DeliveryCharge - CouponApplied;
                                        finalAmnt = amt + ((amt * serviceCharge) / 100);


                                        tvServiceCharge.setText("+ ₹ " + new DecimalFormat("##.##").format((amt * serviceCharge) / 100));
                                        tvAmountPayable.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                                        btnAmount.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                                        */
                                    } else {
                                        tvCouponApplyPrice.setText("- ₹ " + 0.00);
                                        rlCouponCode.setVisibility(View.VISIBLE);
                                        llCouponCode.setVisibility(View.GONE);
                                        showToast("Order amount is minimum than Coupon amount  ");
                                    }
                                    //startActivity(new Intent(PaymentOptionsActivity.this,PaymentOptionsActivity.class));
                                } else {
                                    showToast(response.body().getMessage());
                                }
                            } else {
                                showToast("Something went wrong!!");
                            }
                        }

                        @Override
                        public void onFailure(Call<CouponCodeDataResponse> call, Throwable t) {
                            MyProgress.stop();
                            Log.d(TAG, t.getMessage());
                            Toast.makeText(PaymentOptionsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(PaymentOptionsActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(PaymentOptionsActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                    getTransactionServiceCharge();
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
//                String TYPE = "";
//                if (OrderType == 0) {
//                    TotalAmount = RegularTotalAmount;
//                    TYPE = "Regular / Subscription";
//
//                }
//                else if (OrderType == 1) {
//                    TotalAmount = BulkTotalAmount;
//                    TYPE = "Bulk";
//                }
                //Log.d("BTAG", "ORDER TYPE...." + OrderType);
                Double totalPriceAfterExcludingCoupanAmount = TotalAmount - CouponApplied;
                Double totalPriceAfterIncludingDeliveryAmount = totalPriceAfterExcludingCoupanAmount + DeliveryCharge;
                Double totalPriceAfterIncludingServiceCharge = totalPriceAfterIncludingDeliveryAmount + (TotalAmount * serviceCharge) / 100;
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                String[] separated = SlotTime.split("-");
                String strTime = separated[0];
                String endTime = separated[1];
                String cnvrdate = Utils.convertDate(DeliveryDate, "dd MMM yy", "yyyy-MM-dd");
                String formatDate;
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                Date d = null;
                try {
                    d = sdf.parse(strTime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                formatDate = sdf1.format(d);

                String formatDate2;
                Date d2 = null;
                try {
                    d2 = sdf.parse(endTime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                formatDate2 = sdf1.format(d2);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(PaymentOptionsActivity.this, "Nothing selected....", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(PaymentOptionsActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                    MyProgress.start(PaymentOptionsActivity.this);

                    Call<OrderDataResponse> call = RetrofitClient.getRetrofitClient().getOrderDetails(
                            localData.getCustomerId(),
                            AddressId,
                            cnvrdate,
                            formatDate,
                            formatDate2,
                            String.valueOf(TotalAmount),
                            String.valueOf(CouponApplied),
                            String.valueOf(totalPriceAfterExcludingCoupanAmount),
                            //String.valueOf(DeliveryCharge),
                            //String.valueOf(totalPriceAfterIncludingDeliveryAmount),
                            String.valueOf((TotalAmount * serviceCharge) / 100),
                            String.valueOf(numberFormat.format(totalPriceAfterIncludingServiceCharge))
                            // TYPE
                    );

                    call.enqueue(new Callback<OrderDataResponse>() {
                        @Override
                        public void onResponse(Call<OrderDataResponse> call, Response<OrderDataResponse> response) {
                            MyProgress.stop();
                            if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                                // showToast(response.body().getMessage());
                                if (response.body().getData().getOrderDetail().getOrderId() != null) {
                                    //Log.d("BTAG", "ORDER ID...." + response.body().getData().getOrderDetail().getOrderCode());
                                    OrderId = Double.valueOf(response.body().getData().getOrderDetail().getOrderId());
                                    // OrderId = response.body().getData().getOrderDetail().getOrderId();
                                    orderPaymentId = response.body().getData().getOrderDetail().getOrderPaymentId();
                                    Log.d("BTAG", "ORDER ID...." + Math.round(OrderId));
                                    Log.d("PTAG", "ORDERPAY ID...." + orderPaymentId);
                                    // rezorpayCall(localData.getSignIn().getName(),localData.getSignIn().getEmail(),localData.getSignIn().getPhone(),convertedAmount);
                                    if (paymentMethod.contains("CashOnDelivery")) {
                                        getCheckOrderPayment(orderPaymentId);
                                    } else {
                                        startPayment();
                                    }
                                } else {
                                    showToast("Plz Try Again Later...");
                                    startActivity(new Intent(PaymentOptionsActivity.this, HomeActivity.class));
                                    finishAffinity();
                                }
                            } else {
                               // showToast(getString(R.string.error_general));
                                Toast.makeText(PaymentOptionsActivity.this, "errors", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDataResponse> call, Throwable t) {
                            MyProgress.stop();
                            Log.d(TAG, t.getMessage());
                            Toast.makeText(PaymentOptionsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private void getCoupneIdMethod() {
        RetrofitClient.getRetrofitClient().getCoupneId(
                cupId,
                localData.getCustomerId()
        ).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equalsIgnoreCase("true")){

                        CouponCodeDataResponse couponCodeDataResponse = new CouponCodeDataResponse();
                        showToast(response.body().getMessage());
                        Double amt = TotalAmount + DeliveryCharge - CouponApplied;
                        finalAmnt = amt + ((amt * serviceCharge) / 100);

/*
                        tvServiceCharge.setText("+ ₹ " + new DecimalFormat("##.##").format((amt * serviceCharge) / 100));
                        tvAmountPayable.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                        btnAmount.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));*/

                        rlCouponCode.setVisibility(View.GONE);
                        llCouponCode.setVisibility(View.VISIBLE);
                        tvCouponApplyPrice.setText("- ₹ " + CouponApplied);
                        tvAmountPayable.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                        btnAmount.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                    }else {
                        showToast(response.body().getMessage());
                        tvCouponApplyPrice.setText("- ₹ " + 0.00);
                        tvAmountPayable.setText(" ₹ " + new DecimalFormat("##.##").format(TotalAmount));
                        btnAmount.setText(" ₹ " + new DecimalFormat("##.##").format(TotalAmount));
                        rlCouponCode.setVisibility(View.VISIBLE);
                        llCouponCode.setVisibility(View.GONE);

                    }
                }
            }
            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(PaymentOptionsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setData() {

//        if (OrderType == 0) {
//            TotalAmount = RegularTotalAmount;
//        }
//        else if (OrderType == 1) {
//            TotalAmount = BulkTotalAmount;
//        }
        tvNetPrice.setText(" ₹ " + new DecimalFormat("##.##").format(TotalAmount));
        tvDeliveryCharge.setText("+ ₹ " + new DecimalFormat("##.##").format(DeliveryCharge));

//        btnAmount.setText(" ₹ " + 0.00);

    }

    private void getTransactionServiceCharge() {
        MyProgress.start(PaymentOptionsActivity.this);

        Call<TransactionDataResponse> call = RetrofitClient.getRetrofitClient().getTransactionData();

        call.enqueue(new Callback<TransactionDataResponse>() {
            @Override
            public void onResponse(Call<TransactionDataResponse> call, Response<TransactionDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // showToast(response.body().getMessage());
//                    if (OrderType == 0) {
//                        TotalAmount = RegularTotalAmount;
//                    }
//                    else if (OrderType == 1) {
//                        TotalAmount = BulkTotalAmount;
//                    }

                    if (genderradioButton.getText().toString().trim().contains("CreditCard")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getCreditCard();
                        paymentMethod = "CreditCard";
                    } else if (genderradioButton.getText().toString().trim().contains("DebitCard")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getDebitCard();
                        paymentMethod = "DebitCard";
                    } else if (genderradioButton.getText().toString().trim().contains("CashOnDelivery")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getCashOnDelivery();
                        paymentMethod = "CashOnDelivery";
                    } else if (genderradioButton.getText().toString().trim().contains("InternetBanking")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getInternetBanking();
                        paymentMethod = "InternetBanking";
                    } else if (genderradioButton.getText().toString().contains("Upi")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getUpi();
                        paymentMethod = "Upi";
                    }else if (genderradioButton.getText().toString().contains("Wallet")) {
                        serviceCharge = response.body().getData().getTransactionChargeDetail().getWallet();
                        paymentMethod = "Wallet";
                    }


                    Double amt = TotalAmount + DeliveryCharge - CouponApplied;
                    finalAmnt = amt + ((amt * serviceCharge) / 100);

                    tvDeliveryCharge.setText("+ ₹ " + new DecimalFormat("##.##").format(DeliveryCharge));
                    tvServiceCharge.setText("+ ₹ " + new DecimalFormat("##.##").format((amt * serviceCharge) / 100));
                    tvAmountPayable.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
//                    btnAmount.setText(" ₹ " + new DecimalFormat("##.##").format(finalAmnt));
                    //startActivity(new Intent(PaymentOptionsActivity.this,PaymentOptionsActivity.class));

                } else {
                    showToast(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<TransactionDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(PaymentOptionsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Razor Pay api call
    public void startPayment() {

        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_BJhpLshlVxMi9A");
        checkout.setKeyID("rzp_test_s1BD5vvactc4hv");

        /**
         * Set your logo here
         */
       // checkout.setImage(R.drawable.final_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        Log.d("BTAG", "TOTAL AMOUNT..." + Math.round(finalAmnt));
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", localData.getSignIn().getName());
            options.put("description", "Throtel Grocery");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", Math.round(OrderId));//from response of order placed api.
            options.put("order_id", orderPaymentId);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Math.round(finalAmnt) * 100);//pass amount in currency subunits
            //options.put("prefill.email", localData.getSignIn().getEmail());
            options.put("prefill.contact", localData.getSignIn().getPhone());
            options.put("prefill.method", paymentMethod);

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // After successful payment Razorpay send back a unique id
        // Toast.makeText(PaymentOptionsActivity.this, "Transaction Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();
        getCheckOrderPayment(razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int i, String error) {
        // Error message
        Toast.makeText(PaymentOptionsActivity.this, "Transaction unsuccessful Plz Try Again Later... " + error, Toast.LENGTH_LONG).show();
        startActivity(new Intent(PaymentOptionsActivity.this, HomeActivity.class));
        finishAffinity();
    }

    //Order Payment.....
    private void getCheckOrderPayment(String paymentID) {
        MyProgress.start(PaymentOptionsActivity.this);
        Log.d("BTAG", "PARAM ORDER PAYMENT..." + localData.getCustomerId() +
                paymentID + Math.round(OrderId) + paymentMethod + "Success");
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getOrderPayment(localData.getCustomerId(), paymentID,
                String.valueOf(Math.round(OrderId)), paymentMethod, "Success");

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    showToast(response.body().getMessage());
                    Log.d("BTAG", "Order Status..." + response.body().getMessage());
                    startActivity(new Intent(PaymentOptionsActivity.this, HomeActivity.class));
                    finishAffinity();

                } else {
                    showToast(getString(R.string.error_general));

                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(PaymentOptionsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
