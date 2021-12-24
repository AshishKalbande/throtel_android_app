package com.throtel.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;

import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.LoginDataResponse;
import com.throtel.grocery.models.UserDetail;
import com.throtel.grocery.utils.Constants;
import com.throtel.grocery.utils.Utils;
import com.throtel.grocery.views.MyProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPVerifyActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private String strMobile, strLocalOtp, strSocialMobile;
    private EditText etOtp1;
    private TextView tvResendOtp;

    String idToken, idAuth, signUpBy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        //tvResendOtp.setVisibility(View.VISIBLE);

        if (getIntent() != null) {
            strMobile = getIntent().getStringExtra(Constants.MOBILE);
            strSocialMobile = getIntent().getStringExtra(Constants.SOCIALMOBILE);
            idToken = getIntent().getStringExtra("isToken");
            signUpBy = getIntent().getStringExtra("signUpBy");
            idAuth = getIntent().getStringExtra("idAuth");
            Log.d("BTAG", "MOBILE NUMBER SOCIAL...." + strSocialMobile);
            Log.d("BTAG", "MOBILE NUMBER ...." + strMobile);
            if (!strMobile.equals("")) {
                strSocialMobile = "";
            }
            if (!strSocialMobile.equals("")) {
                strMobile = "";
            }
        }


        etOtp1 = findViewById(R.id.et_otp);

        tvResendOtp = findViewById(R.id.tv_resend_otp);

        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reSendOtp();
                showToast("OTP Resend successfully");
            }
        });

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!strSocialMobile.equals("")) {
                    verifySocialOtp();
                } else {
                    verifyOtp();
                }
            }
        });

    }

    private void verifyOtp() {
        strLocalOtp = etOtp1.getText().toString().trim();

        if (strLocalOtp.equals("") || strLocalOtp.length() != 4) {
            Toast.makeText(OTPVerifyActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
        } else {

            MyProgress.start(OTPVerifyActivity.this);
            Call<DataResponse> call = RetrofitClient.getRetrofitClient().verifyOTP(strMobile, strLocalOtp);
            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    MyProgress.stop();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("true")) {
                            localData.setMobileNumber(strMobile);
                            localData.setMobileVerified(true);
                            startActivity(new Intent(OTPVerifyActivity.this, LoginActivity.class));
                            finish();
                        }
                        Toast.makeText(OTPVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void reSendOtp() {
        MyProgress.start(this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().forgotGenerateOTP(strMobile);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();

                if (response.body() != null) {
                    Toast.makeText(OTPVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    etOtp1.setText("");
                } else {
                    Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifySocialOtp() {
        strLocalOtp = etOtp1.getText().toString().trim();

        if (strLocalOtp.equals("") || strLocalOtp.length() != 4) {
            Toast.makeText(OTPVerifyActivity.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();
        } else {

            MyProgress.start(OTPVerifyActivity.this);
            Call<DataResponse> call = RetrofitClient.getRetrofitClient().verifyOTP(strSocialMobile, strLocalOtp);
            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    MyProgress.stop();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("true")) {
                            localData.setMobileNumber(strMobile);
                            localData.setMobileVerified(true);
                            //callSocialLoginApi();
                        }
                        Toast.makeText(OTPVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    private void callSocialLoginApi() {
//
//        Log.d("BTAG", "PARAM SOCIAL LOGIN..." + idAuth + " signup by :" + signUpBy + " Id Token " + idToken);
//        MyProgress.start(this);
//        Call<LoginDataResponse> call = RetrofitClient.getRetrofitClient().getSocialLogin(idAuth, signUpBy, idToken);
//        call.enqueue(new Callback<LoginDataResponse>() {
//            @Override
//            public void onResponse(Call<LoginDataResponse> call, Response<LoginDataResponse> response) {
//                Utils.hideKeyboard(OTPVerifyActivity.this);
//                MyProgress.stop();
//
//                if (response.body() != null) {
//                    if (response.body().getStatus().equals("true")) {
//                        Toast.makeText(OTPVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        UserDetail userDetail = response.body().getData().getUserDetail();
//                        // save user data
//                        localData.setLoggedIn(true);
//                        localData.setSignIn(userDetail);
//                        localData.setCustomerId(String.valueOf(userDetail.getUserId()));
//                        Log.d("BTAG", "GET USER LOGIN DETAIL...." + userDetail.getUserId());
//                        startActivity(new Intent(OTPVerifyActivity.this, HomeActivity.class));
//                        finishAffinity();
//                    } else {
//
//                        Toast.makeText(OTPVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        //callCustomDialog();
//                    }
//                } else {
//                    Toast.makeText(OTPVerifyActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, "" + t.getMessage());
//                Toast.makeText(OTPVerifyActivity.this, "Invalid Mobile Number..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
