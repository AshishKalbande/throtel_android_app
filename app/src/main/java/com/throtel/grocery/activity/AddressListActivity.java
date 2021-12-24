package com.throtel.grocery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.AddressListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.AddressList;
import com.throtel.grocery.models.AddressListDataResponse;
import com.throtel.grocery.models.CategoryList;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListActivity extends BaseActivity implements AddressListAdapter.OnItemClickListener {
    private static final String TAG = AddressListActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    int addId = 0;
    ArrayList<String> addList;
    AddressList address;
    private RecyclerView rvAddressList;
    private CategoryList category;
    private LinearLayout addAddress, llContinue;
    private TextView tvContinue;
    private ArrayList<AddressList> addressList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);


        setUpToolbarBackButton("Choose Address");


        rvAddressList = findViewById(R.id.rv_address_list);
        addAddress = findViewById(R.id.lay1);
        tvContinue = findViewById(R.id.tv_continue);
        llContinue = findViewById(R.id.ll_continue);
        if (getIntent() != null) {
            if (getIntent().getStringExtra("changefor").equalsIgnoreCase("Subscribe") ||
                    getIntent().getStringExtra("changefor").equalsIgnoreCase("MyAccount")) {
                llContinue.setVisibility(View.GONE);
            } else
                llContinue.setVisibility(View.VISIBLE);
        }


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                address = null;
                Intent intent = new Intent(AddressListActivity.this, AddNewAddressActivity.class);
                intent.putExtra("AddressList", address);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressList.size() == 0) {
                    showToast("Please add Address");
                } else {
                    Intent intent = new Intent(AddressListActivity.this, CheckOutSelectSlotActivity.class);
                    intent.putStringArrayListExtra("active", addList);
                    startActivity(intent);
                }
            }
        });


        if (NetworkUtil.getConnectivityStatus(AddressListActivity.this))
            getViewAddressList();

        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }


    //Product List
    private void getViewAddressList() {
        MyProgress.start(AddressListActivity.this);

        Call<AddressListDataResponse> call = RetrofitClient.getRetrofitClient().getAddressList(localData.getCustomerId());

        call.enqueue(new Callback<AddressListDataResponse>() {
            @Override
            public void onResponse(Call<AddressListDataResponse> call, Response<AddressListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        addressList = new ArrayList<>();
                        addressList.addAll(response.body().getData().getAddressList());
                        setUpAddressList();
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(getString(R.string.error_general));
                }
            }

            @Override
            public void onFailure(Call<AddressListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(AddressListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setUpAddressList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddressListActivity.this);
        rvAddressList.setLayoutManager(layoutManager);
        rvAddressList.setAdapter(new AddressListAdapter(this, addressList, AddressListActivity.this));
        addList = new ArrayList<>();
        for (AddressList addressData : addressList) {
            if (addressData.getStatus().equals("Active")) {
                Log.d("BTAGd", "ACTIVE ADDRESS..." + addressData.getPincodeNumber());
                addList.add(addressData.getAddressType());
                addList.add(addressData.getName());
                addList.add(addressData.getPhone());
                addList.add(addressData.getFlatOrHouseNumber());
                addList.add(addressData.getStreetOrSocietyName());
                addList.add(addressData.getLandmark());
                addList.add(addressData.getCityName());
                addList.add(addressData.getPincodeNumber());
                addList.add(addressData.getAddressId().toString());

//                addList.add(addressData.getAddressId().toString());
            }
        }

    }


    @Override
    public void onItemClick(AddressList address) {

        Intent intent = new Intent(AddressListActivity.this, AddNewAddressActivity.class);
        intent.putExtra("AddressList", address);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getViewAddressList();

    }

    public void resetGraph(Context context) {

        getViewAddressList();

    }
}
