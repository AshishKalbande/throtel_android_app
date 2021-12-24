package books.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.adapter.ReasonListAdapter;
import books.models.AddressList;
import books.models.ReasonList;

public class CancelOrderActivity extends BaseActivity implements ReasonListAdapter.OnItemClickListener {
    private static final String TAG = CancelOrderActivity.class.getSimpleName();


    private RecyclerView rvReasonList;

    public static int REQUEST_CODE = 102;

    private AddressList listAddress;
    private ArrayList<String> listAdd;
    private TextView tvChangeAddress;
    private EditText edtMessage;
    private Button btnCancel;
    private String msg;
    private int  orderId,subOrderId ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);


        if (getIntent() != null)
            orderId = getIntent().getIntExtra("OrderList",0);
            subOrderId = getIntent().getIntExtra("SubOrderList",0);
        setUpToolbarBackButton("Cancel Order / Subscription");


        rvReasonList = findViewById(R.id.rv_reasons_list);
        btnCancel=findViewById(R.id.btn_cancel);
        edtMessage=findViewById(R.id.edt_msg);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Reason.equals(""))
                {
                    showToast("Please Select Reason");
                }
                else  if (TextUtils.isEmpty(edtMessage.getText().toString())) {
                    showToast("Please Enter Message");
                }
                else
                {
                    msg = edtMessage.getText().toString();
                    if(orderId == 0 && subOrderId != 0)
                    {
                       // getSubscriptionCancel();
                    }
                    else if(subOrderId == 0 && orderId != 0) {
                       // getCancelOrder();
                    }
                }


            }
        });


//        if (NetworkUtil.getConnectivityStatus(CancelOrderActivity.this))
//            getReasonsList();
//
//        else
//            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }


    //Reasons List
//    private void getReasonsList() {
//        MyProgress.start(CancelOrderActivity.this);
//
//        Call<ReasonListDataResponse> call = RetrofitClient.getRetrofitClient().getReasonsList();
//
//        call.enqueue(new Callback<ReasonListDataResponse>() {
//            @Override
//            public void onResponse(Call<ReasonListDataResponse> call, Response<ReasonListDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpReasonList(response.body().getData().getReasonList());
//
//                } else {
//                    showToast(response.body().getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReasonListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(CancelOrderActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void setUpReasonList(ArrayList<ReasonList> reasonList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(CancelOrderActivity.this);
        rvReasonList.setLayoutManager(layoutManager);
        rvReasonList.setAdapter(new ReasonListAdapter(this, reasonList, CancelOrderActivity.this));
    }

        //Cancel Order
//    private void getCancelOrder() {
//        MyProgress.start(CancelOrderActivity.this);
//
//        Log.d("BTAG","PARAM IS..."+localData.getCustomerId()+orderId+Reason+msg);
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getCancelOrder(localData.getCustomerId(), String.valueOf(orderId),Reason,msg);
//
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    showToast(response.body().getMessage());
//                    Intent intent = new Intent();
//                    setResult(RESULT_OK, intent);
//                    finishAndRemoveTask();
//                } else {
//                    showToast(response.body().getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(CancelOrderActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    //Cancel Order
//    private void getSubscriptionCancel() {
//        MyProgress.start(CancelOrderActivity.this);
//
//        Log.d("BTAG","PARAM IS..."+localData.getCustomerId()+subOrderId+Reason+msg);
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getSubscriptionCancel(localData.getCustomerId(), String.valueOf(subOrderId),Reason,msg);
//
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    showToast(response.body().getMessage());
//                    Intent intent = new Intent();
//                    setResult(RESULT_OK, intent);
//                    finishAndRemoveTask();
//                } else {
//                    showToast(response.body().getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(CancelOrderActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    @Override
    public void onItemClick(ReasonList slot) {

    }
}
