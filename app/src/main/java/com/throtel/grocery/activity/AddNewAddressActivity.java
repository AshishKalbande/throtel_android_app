package com.throtel.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.AddressList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.views.MyProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAddressActivity extends BaseActivity {
    private static final String TAG = AddNewAddressActivity.class.getSimpleName();

    private EditText etFullName, etMobile, etFlatNo, etSocietyname, etLandmark, etPincode;
    private RadioGroup radioGroupGender;

    private Button btnAdd, btnUpdate;
    private AddressList member = null;
    private RadioButton radioButtonHome, radioButtonOffice;
    private String Type = "Home";
    private AddressList address;
    private ImageView imgHome, imgOffice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_address);
        initViews();


        if (getIntent() != null) {
            address = (AddressList) getIntent().getSerializableExtra("AddressList");
            //setUpToolbarBackButton("Add Address");
        }


        if (address != null) {
            setData();
            setUpToolbarBackButton("Update Address");
            btnUpdate.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
        } else
            setUpToolbarBackButton("Add Address");


    }

    private void initViews() {


        etFullName = findViewById(R.id.et_full_name);
        etMobile = findViewById(R.id.et_mobile);
        etFlatNo = findViewById(R.id.et_flat_no);
        etSocietyname = findViewById(R.id.et_society_name);
        etLandmark = findViewById(R.id.et_landmark);
        radioGroupGender = findViewById(R.id.radio_group_gender);

        btnAdd = findViewById(R.id.btn_add);
        radioButtonHome = findViewById(R.id.radio_home);
        radioButtonOffice = findViewById(R.id.radio_office);
        imgHome = findViewById(R.id.img_home);
        imgOffice = findViewById(R.id.img_office);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        btnUpdate = findViewById(R.id.btn_update);
    }

    private void setData() {

        etFullName.setText(address.getName());
        etMobile.setText(address.getPhone());
        etFlatNo.setText(address.getFlatOrHouseNumber());
        etSocietyname.setText(address.getStreetOrSocietyName());
        etLandmark.setText(address.getLandmark());

        if (address.getAddressType().equals("Home")) {
            radioButtonHome.setChecked(true);
            radioButtonOffice.setChecked(false);
        } else {
            radioButtonHome.setChecked(false);
            radioButtonOffice.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateUpdate();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonHome.setChecked(true);
                radioButtonOffice.setChecked(false);
            }
        });

        imgOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonHome.setChecked(false);
                radioButtonOffice.setChecked(true);
            }
        });
    }


    private void validate() {

        if (radioGroupGender.getCheckedRadioButtonId() == R.id.radio_office)
            Type = "Office";

        String fullName = etFullName.getText().toString();
        String phone = etMobile.getText().toString();
        String flatno = etFlatNo.getText().toString();
        String societyname = etSocietyname.getText().toString();
        String landmark = etLandmark.getText().toString();
        RadioButton radioButton = findViewById(radioGroupGender.getCheckedRadioButtonId());

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        } else if (!isValidMobile(phone) || phone.length() != 10) {
            showToast("Please enter valid mobile number");
        } else if (TextUtils.isEmpty(flatno)) {
            Toast.makeText(this, "Please enter flat no", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(societyname)) {
            Toast.makeText(this, "Please enter society name", Toast.LENGTH_SHORT).show();
        } else {


            // Add Member
            MyProgress.start(this);
            Call<DataResponse> call = RetrofitClient.getRetrofitClient().addNewAddress(localData.getCustomerId(),
                    fullName, phone, flatno, societyname, landmark, Type);
            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    MyProgress.stop();
                    Log.d(TAG, "" + response.body());
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(AddNewAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(AddNewAddressActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(AddNewAddressActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void validateUpdate() {

        if (radioGroupGender.getCheckedRadioButtonId() == R.id.radio_office)
            Type = "Office";

        String fullName = etFullName.getText().toString();
        String phone = etMobile.getText().toString();
        String flatno = etFlatNo.getText().toString();
        String societyname = etSocietyname.getText().toString();
        String landmark = etLandmark.getText().toString();
        RadioButton radioButton = findViewById(radioGroupGender.getCheckedRadioButtonId());

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        } else if (!isValidMobile(phone) || phone.length() != 10) {
            showToast("Please enter valid mobile number");
        } else if (TextUtils.isEmpty(flatno)) {
            Toast.makeText(this, "Please enter flat no", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(societyname)) {
            Toast.makeText(this, "Please enter society name", Toast.LENGTH_SHORT).show();
        } else {


            // Add Member
            MyProgress.start(this);
            Call<DataResponse> call = RetrofitClient.getRetrofitClient().getUpdateAddress(String.valueOf(address.getAddressId()), localData.getCustomerId(),
                    fullName, phone, flatno, societyname, landmark, Type);
            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    MyProgress.stop();
                    Log.d(TAG, "" + response.body());
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(AddNewAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(AddNewAddressActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(AddNewAddressActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
