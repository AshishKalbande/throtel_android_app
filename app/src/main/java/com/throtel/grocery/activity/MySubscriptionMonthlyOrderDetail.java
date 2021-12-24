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
import com.throtel.grocery.adapter.MyMonthlySUbOrderDetailListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.MyMonthlyDetailsDataResponse;
import com.throtel.grocery.models.SubscriptionMonthlyOrderDetail;
import com.throtel.grocery.models.SubscriptionMonthlyOrderList;
import com.throtel.grocery.models.SubscriptionOrderList;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySubscriptionMonthlyOrderDetail extends BaseActivity {
    private static final String TAG = MySubscriptionMonthlyOrderDetail.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    private TextView tvOrderNo, tvName, tvMobile, tvAddress, tvCityPincode;
    private Button btnTotal;
    private RecyclerView rvOrderList;
    private TextView tvItemNo;
    private SubscriptionMonthlyOrderList order = null;
    private SubscriptionMonthlyOrderDetail orderDetail = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscription_details);
        initViews();


        if (getIntent() != null)
            order = (SubscriptionMonthlyOrderList) getIntent().getSerializableExtra("SOrderList");
        setUpToolbarBackButton("Monthly Subscription Details");

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


        btnTotal.setText("₹ " + orderDetail.getOrderTotal());


    }

//    private void getOrderDetails() {
//        MyProgress.start(MySubscriptionMonthlyOrderDetail.this);
//        Call<MyMonthlyDetailsDataResponse> call = RetrofitClient.getRetrofitClient().getMonthlySubscriptionOrderDetails(localData.getCustomerId(), order.getOrderId().toString());
//        call.enqueue(new Callback<MyMonthlyDetailsDataResponse>() {
//            @Override
//            public void onResponse(Call<MyMonthlyDetailsDataResponse> call, Response<MyMonthlyDetailsDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    orderDetail = response.body().getData().getSubscriptionOrderDetail();
//                    setData();
//                    setUpOrdersList(response.body().getData().getSubscriptionOrderDetail().getSubscriptionOrderList());
//                } else {
//                    Toast.makeText(MySubscriptionMonthlyOrderDetail.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyMonthlyDetailsDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(MySubscriptionMonthlyOrderDetail.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void setUpOrdersList(ArrayList<SubscriptionOrderList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(MySubscriptionMonthlyOrderDetail.this);
        rvOrderList.setLayoutManager(layoutManager);
        rvOrderList.setAdapter(new MyMonthlySUbOrderDetailListAdapter(MySubscriptionMonthlyOrderDetail.this, list));
        tvItemNo.setText("No Of Orders : " + list.size());
    }

}
