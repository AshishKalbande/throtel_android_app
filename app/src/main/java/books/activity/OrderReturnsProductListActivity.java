package books.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.adapter.OrderReturnProductListAdapter;
import books.api.RetrofitClient;
import books.models.CustomerOrderListDataResponse;
import books.models.CustomerProductList;
import books.models.OrderList;
import books.utils.NetworkUtil;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderReturnsProductListActivity extends BaseActivity implements OrderReturnProductListAdapter.OnItemClickListener {
    private static final String TAG = OrderReturnsProductListActivity.class.getSimpleName();


    private RecyclerView rvMyOrdersList;
    public static int REQUEST_CODE = 102;
    private OrderList order = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_subscriptions);


        if (getIntent() != null)
            order = (OrderList) getIntent().getSerializableExtra("OrderList");
            setUpToolbarBackButton("Return Product");


        rvMyOrdersList = findViewById(R.id.rv_my_list);



        if (NetworkUtil.getConnectivityStatus(OrderReturnsProductListActivity.this))
            getOrdersList();
        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }


    //Product List
    private void getOrdersList() {
        MyProgress.start(OrderReturnsProductListActivity.this);
        Call<CustomerOrderListDataResponse> call = RetrofitClient.getRetrofitClient().getCustomerOrdersListData(localData.getCustomerId(), order.getOrderId().toString());
        call.enqueue(new Callback<CustomerOrderListDataResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderListDataResponse> call, Response<CustomerOrderListDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    setUpOrdersList(response.body().getData().getProductList());
                } else {
                    Toast.makeText(OrderReturnsProductListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerOrderListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(OrderReturnsProductListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpOrdersList(ArrayList<CustomerProductList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderReturnsProductListActivity.this);
        rvMyOrdersList.setLayoutManager(layoutManager);
        rvMyOrdersList.setAdapter(new OrderReturnProductListAdapter(OrderReturnsProductListActivity.this, list, this));
    }



    @Override
    public void onItemClick(CustomerProductList product) {

        Intent intent = new Intent(OrderReturnsProductListActivity.this, ReturnProductActivity.class);
        intent.putExtra("productList", product);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getOrdersList();

    }



}
