package com.throtel.grocery.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.ItemsProductListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CustomerOrderListDataResponse;
import com.throtel.grocery.models.CustomerProductList;
import com.throtel.grocery.models.OrderList;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllOrderItemsActivity extends BaseActivity {
    private static final String TAG = ViewAllOrderItemsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private OrderList order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_order_items);
        order = (OrderList) getIntent().getSerializableExtra("DATA");

        setUpToolbarBackButton(order.getOrderCode());

        recyclerView = findViewById(R.id.rv_item_list);

        if (NetworkUtil.getConnectivityStatus(this)) {
            getOrdersList();
        } else
            showToast(getString(R.string.error_network));

    }

    private void getOrdersList() {
        MyProgress.start(this);
        Call<CustomerOrderListDataResponse> call = RetrofitClient.getRetrofitClient().getCustomerOrdersListData(localData.getCustomerId(), order.getOrderId().toString());
        call.enqueue(new Callback<CustomerOrderListDataResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderListDataResponse> call, Response<CustomerOrderListDataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        setUpOrdersList(response.body().getData().getProductList());
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(getString(R.string.error_general));
                }
            }

            @Override
            public void onFailure(Call<CustomerOrderListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

    private void setUpOrdersList(ArrayList<CustomerProductList> list) {

        LinearLayoutManager RecyclerViewLayoutManager = new LinearLayoutManager(this);
        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view

        recyclerView.setAdapter(new ItemsProductListAdapter(this, list));
        ((TextView) findViewById(R.id.tv_no_of_items)).setText("No Of Items : " + list.size());
    }
}
