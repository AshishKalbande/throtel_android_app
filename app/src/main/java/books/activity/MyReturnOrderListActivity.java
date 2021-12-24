package books.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.adapter.MyReturnOrderListAdapter;
import books.models.ReturnProductList;

public class MyReturnOrderListActivity extends BaseActivity implements MyReturnOrderListAdapter.OnItemClickListener {
    private static final String TAG = MyReturnOrderListActivity.class.getSimpleName();


    private RecyclerView rvMyReturnList;
    public static int REQUEST_CODE = 102;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_return_orderlist);


        setUpToolbarBackButton("My Return Orders");

        rvMyReturnList = findViewById(R.id.rv_my_list);


//        if (NetworkUtil.getConnectivityStatus(MyReturnOrderListActivity.this))
//            getReturnList();
//
//        else
//            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }


    //Product List

//    private void getReturnList() {
//        MyProgress.start(MyReturnOrderListActivity.this);
//        Call<ReturnProductListDataResponse> call = RetrofitClient.getRetrofitClient().getReturnListData(String.valueOf(localData.getCustomerId()));
//        call.enqueue(new Callback<ReturnProductListDataResponse>() {
//            @Override
//            public void onResponse(Call<ReturnProductListDataResponse> call, Response<ReturnProductListDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpReturnList(response.body().getData().getReturnProductList());
//                } else {
//                    showToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReturnProductListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(MyReturnOrderListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpReturnList(ArrayList<ReturnProductList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyReturnOrderListActivity.this);
        rvMyReturnList.setLayoutManager(layoutManager);
        rvMyReturnList.setAdapter(new MyReturnOrderListAdapter(MyReturnOrderListActivity.this, list, this));
    }

    @Override
    public void onItemClick(ReturnProductList order) {
        Intent intent = new Intent(MyReturnOrderListActivity.this, ReturnOrderListDetailsActivity.class);
        intent.putExtra("OrderList", order);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_CODE)
//            getReturnList();

    }

}
