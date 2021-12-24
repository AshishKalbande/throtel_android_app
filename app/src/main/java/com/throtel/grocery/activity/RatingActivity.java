package com.throtel.grocery.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.views.MyProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends BaseActivity {

    private static final String TAG = RatingActivity.class.getSimpleName();
    private RatingBar ratingBarOrderBooking, ratingBarOrderDelivery, ratingBarOrderQuality;
    private TextView tvSubmitFeedback;
    private String orderId;
    private EditText edtMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        orderId = getIntent().getStringExtra("OrderId");

        setUpToolbarBackButton("Rate Order");

        initView();
    }

    private void initView() {
        ratingBarOrderBooking = findViewById(R.id.ratingBarOrderBooking);
        ratingBarOrderDelivery = findViewById(R.id.ratingBarOrderDelivery);
        ratingBarOrderQuality = findViewById(R.id.ratingBarOrderQuality);
        tvSubmitFeedback = findViewById(R.id.tv_submit_feedback);
        edtMessage = findViewById(R.id.edt_message);

        tvSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderBooking = (int) ratingBarOrderBooking.getRating();
                int orderDelivery = (int) ratingBarOrderDelivery.getRating();
                int orderQuality = (int) ratingBarOrderQuality.getRating();

                String message = edtMessage.getText().toString();
                if (orderBooking <= 0) {
                    showToast("Please rate for order booking");
                } else if (orderDelivery <= 0) {
                    showToast("Please rate for order delivery");
                } else if (orderQuality <= 0) {
                    showToast("Please rate for order quality");
                } else if (TextUtils.isEmpty(message)) {
                    showToast("Please enter message");
                } else {
                    //sendRatingReview(message, String.valueOf(orderDelivery), String.valueOf(orderQuality), String.valueOf(orderBooking));
                }
            }
        });
    }}

//    private void sendRatingReview(String message, String orderDelivery, String orderQuality, String orderBooking) {
//        MyProgress.start(this);
//        RetrofitClient.getRetrofitClient().saveRatings(
//                localData.getCustomerId(), message, orderDelivery, orderQuality, orderBooking, orderId
//        ).enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        showToast(response.body().getMessage());
//                        finish();
//                    } else {
//                        showToast(response.body().getMessage());
//                    }
//                } else {
//                    showToast(getString(R.string.error_general));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                showToast(getString(R.string.error_general));
//            }
//        });
//    }
//
//}
