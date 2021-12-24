package com.throtel.grocery.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.MyDailySUbOrderDetailListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.SubscriptionOrderDetail;
import com.throtel.grocery.models.SubscriptionOrderDetailDataResponse;
import com.throtel.grocery.models.SubscriptionOrderList;
import com.throtel.grocery.models.SubscriptionOrdersList;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySubscriptionDailyOrderDetail extends BaseActivity implements MyDailySUbOrderDetailListAdapter.StatusUpdateListener {
    private static final String TAG = MySubscriptionDailyOrderDetail.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    private TextView tvOrderNo, tvName, tvMobile, tvAddress, tvCityPincode;
    private Button btnTotal;
    private RecyclerView rvOrderList;
    private TextView tvItemNo;
    private int noOfDays;
    private SubscriptionOrdersList order = null;
    private SubscriptionOrderDetail orderDetail = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscription_details);
        initViews();

        if (getIntent() != null)
            order = (SubscriptionOrdersList) getIntent().getSerializableExtra("SOrderList");
        setUpToolbarBackButton("Daily Subscription Details");

        if (order != null) {
            //getOrderDetails();
        }
    }

    private void initViews() {

        tvOrderNo = findViewById(R.id.tv_order_id);
        tvName = findViewById(R.id.tv_customer_name);
        tvMobile = findViewById(R.id.tv_mobile);
        tvAddress = findViewById(R.id.tv_address);
        tvCityPincode = findViewById(R.id.tv_city_pincode);
        btnTotal = findViewById(R.id.btn_total);

        rvOrderList = findViewById(R.id.rv_order_list);
        tvItemNo = findViewById(R.id.tv_item_no);
    }


    private void setData() {

        tvOrderNo.setText(order.getOrderCode());
        tvName.setText(orderDetail.getCustomerName());
        tvMobile.setText("Contact No : " + orderDetail.getCustomerPhone());
        tvAddress.setText(orderDetail.getCustomerAddress());
        tvCityPincode.setText(localData.getSignIn().getCityName() + "-" + localData.getSignIn().getPincodeNumber());

    }

//    private void getOrderDetails() {
//        MyProgress.start(MySubscriptionDailyOrderDetail.this);
//        Call<SubscriptionOrderDetailDataResponse> call = RetrofitClient.getRetrofitClient().getDailySubscriptionOrderDetails(localData.getCustomerId(), order.getOrderId().toString());
//        call.enqueue(new Callback<SubscriptionOrderDetailDataResponse>() {
//            @Override
//            public void onResponse(Call<SubscriptionOrderDetailDataResponse> call, Response<SubscriptionOrderDetailDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    orderDetail = response.body().getData().getSubscriptionOrderDetail();
//                    setData();
//                    setUpOrdersList(response.body().getData().getSubscriptionOrderDetail().getSubscriptionOrderList());
//                } else {
//                    Toast.makeText(MySubscriptionDailyOrderDetail.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SubscriptionOrderDetailDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(MySubscriptionDailyOrderDetail.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void setUpOrdersList(ArrayList<SubscriptionOrderList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(MySubscriptionDailyOrderDetail.this);
        rvOrderList.setLayoutManager(layoutManager);
        rvOrderList.setAdapter(new MyDailySUbOrderDetailListAdapter(MySubscriptionDailyOrderDetail.this, list, this));
        tvItemNo.setText("No Of Orders : " + list.size());
     //   noOfDays =  list.size();

       /* Double prodactAmount = orderDetail.getOrderTotal();
        Double finalTotle = prodactAmount * noOfDays;*/
        btnTotal.setText("₹ " +orderDetail.getOrderTotal());
    }


    @Override
    public void onStatusUpdate(int index, SubscriptionOrderList order) {
        //getOrderDetails();
    }
}
