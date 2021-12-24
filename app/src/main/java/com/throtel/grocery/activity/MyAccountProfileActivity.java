package com.throtel.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.AddressList;
import com.throtel.grocery.models.AddressListDataResponse;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.throtel.grocery.activity.AddressListActivity.REQUEST_CODE;

public class MyAccountProfileActivity extends BaseActivity {
    CircularImageView ivProfile;
    private TextView tvName, tvMobile;
    private TextView tvCName, tvType, tvFlatNo, tvCMobile, tvSocietyName, tvLandmark, tvPincode;
    private RelativeLayout rlAddress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setUpToolbarBackButton("Profile");

        initView();

        if (NetworkUtil.getConnectivityStatus(MyAccountProfileActivity.this))
            getActiveAddress();
        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        setView();
    }


    private void initView() {
        ivProfile = findViewById(R.id.iv_profile);
        tvName = findViewById(R.id.tv_name);
        tvMobile = findViewById(R.id.tv_mobile);

        tvCName = findViewById(R.id.tv_cname);
        tvCMobile = findViewById(R.id.tv_cmobile);
        tvType = findViewById(R.id.tv_type);
        tvFlatNo = findViewById(R.id.tv_flat_no);
        tvSocietyName = findViewById(R.id.tv_society_name);
        tvLandmark = findViewById(R.id.tv_landmark);
        tvPincode = findViewById(R.id.tv_pincode);
        rlAddress = findViewById(R.id.address);


//        findViewById(R.id.tv_my_subscription).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyAccountProfileActivity.this, MySubscriptionsTabsActivity.class));
//                finish();
//            }
//        });

        findViewById(R.id.tv_my_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("DATA", "Orders");
                setResult(1, intent);
                finish();
            }
        });

//        findViewById(R.id.tv_my_wallet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("DATA", "Wallet");
//                setResult(1, intent);
//                finish();
//            }
//        });

        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountProfileActivity.this, AddressListActivity.class);
                intent.putExtra("changefor", "MyAccount");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    private void setView() {

//        String url = RetrofitClient.BASE_CUSTOMER_IMAGE_URL + localData.getSignIn().getProfileImage();
//
//        Picasso.with(MyAccountProfileActivity.this)
//                .load(url) //Load the image
//                .fit()
//                .error(R.drawable.ic_user)
//                .into(ivProfile);
        tvName.setText(localData.getSignIn().getName());
        tvMobile.setText(localData.getSignIn().getPhone());
        tvPincode.setText(localData.getSignIn().getPincodeNumber() + "-" + localData.getSignIn().getCityName());
    }

    private void getActiveAddress() {
        MyProgress.start(MyAccountProfileActivity.this);

        Call<AddressListDataResponse> call = RetrofitClient.getRetrofitClient().getAddressList(localData.getCustomerId());

        call.enqueue(new Callback<AddressListDataResponse>() {
            @Override
            public void onResponse(Call<AddressListDataResponse> call, Response<AddressListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    for (AddressList addressData : response.body().getData().getAddressList()) {
                        if (addressData.getStatus().equals("Active")) {
                            tvCName.setText(addressData.getName());
                            tvCMobile.setText(addressData.getPhone());
                            tvFlatNo.setText(addressData.getFlatOrHouseNumber() + ", " + addressData.getStreetOrSocietyName() + ", " + addressData.getLandmark());
                            tvPincode.setText(addressData.getPincodeNumber() + ", " + addressData.getCityName());
                        }
                    }

                } else {
//                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddressListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("TAG", t.getMessage());
                Toast.makeText(MyAccountProfileActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            getActiveAddress();
        }
    }
}
