package A_Test.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.throtel.grocery.R;

import A_Test.Api.RetrofitClient;
import books.activity.BaseActivity;
import books.activity.LoginbookActivity;
import books.activity.OTPVerifyActivity;
import books.models.DataResponse;
import books.utils.Constants;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpTestActivity extends BaseActivity {
    private static final String TAG = LoginTestActivity.class.getSimpleName();

    private String strMobile, strLocalOtp, strSocialMobile;
    private EditText etOtp1;
    private TextView tvResendOtp;

    String idToken, idAuth, signUpBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_test);

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
            Toast.makeText(VerifyOtpTestActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
        } else {

            MyProgress.start(VerifyOtpTestActivity.this);
            Call<DataResponse> call = RetrofitClient.getRetrofitClient().verifyOTP(strMobile, strLocalOtp);
            call.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    MyProgress.stop();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("true")) {
                            localData.setMobileNumber(strMobile);
                            localData.setMobileVerified(true);
                            startActivity(new Intent(VerifyOtpTestActivity.this, LoginTestActivity.class));
                            finish();
                        }
                        Toast.makeText(VerifyOtpTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerifyOtpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(VerifyOtpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void verifySocialOtp() {
        strLocalOtp = etOtp1.getText().toString().trim();

        if (strLocalOtp.equals("") || strLocalOtp.length() != 4) {
            Toast.makeText(VerifyOtpTestActivity.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();
        } else {

            MyProgress.start(VerifyOtpTestActivity.this);
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
                        Toast.makeText(
                                VerifyOtpTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VerifyOtpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(VerifyOtpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}