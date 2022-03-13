package A_Test.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.BaseActivity;
import com.throtel.grocery.activity.ForgotPasswordActivity;

import A_Test.Api.RetrofitClient;
import A_Test.model.DataResponse;
import books.utils.Constants;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordTestActivity extends BaseActivity {

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    private String strMobile, strLocalOtp;
    private EditText etOtp1;
    private EditText edtPhone,edtPassword1,edtPassword2;
    private TextView tvResendOtp;
    private LinearLayout layoutOTP,layoutEnterPassword;
    private String password,cpassword;
    private Button btnContinue,btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_test);

        layoutOTP=findViewById(R.id.l1);
        layoutEnterPassword=findViewById(R.id.l2);
        strMobile = getIntent().getStringExtra(Constants.MOBILE);
        etOtp1 = findViewById(R.id.et_otp);
        tvResendOtp = findViewById(R.id.tv_resend_otp);
        btnContinue = findViewById(R.id.btnContinue);

        edtPhone=findViewById(R.id.edt_phone);
        edtPassword1=findViewById(R.id.edt_password);
        edtPassword2=findViewById(R.id.edt_confirm_password);
        btnSubmit=findViewById(R.id.btn_submit);

        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSendOtp();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                password=edtPassword1.getText().toString();
                cpassword=edtPassword2.getText().toString();

                if (TextUtils.isEmpty(password)) {
                    showToast("Please enter password");
                } else if (TextUtils.isEmpty(cpassword)) {
                    showToast("Please enter confirm password");
                }
                else {

                    getChangePassword();
                }
            }
        });

    }

    private void reSendOtp() {
        books.views.MyProgress.start(this);
        Call<A_Test.model.DataResponse> call = RetrofitClient.getRetrofitClient().forgotGenerateOTP(strMobile);
        call.enqueue(new Callback<A_Test.model.DataResponse>() {
            @Override
            public void onResponse(Call<A_Test.model.DataResponse> call, Response<A_Test.model.DataResponse> response) {
                books.views.MyProgress.stop();

                if (response.body() != null) {
                    Toast.makeText(ForgotPasswordTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    etOtp1.setText("");
                } else {
                    Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                books.views.MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void verifyOtp() {
        strLocalOtp = etOtp1.getText().toString().trim();

        if (strLocalOtp.equals("") || strLocalOtp.length() != 4) {
            Toast.makeText(ForgotPasswordTestActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
        } else {

            books.views.MyProgress.start(ForgotPasswordTestActivity.this);
            Call<A_Test.model.DataResponse> call = A_Test.Api.RetrofitClient.getRetrofitClient().verifyForgotOTP(strMobile, strLocalOtp);
            call.enqueue(new Callback<A_Test.model.DataResponse>() {
                @Override
                public void onResponse(Call<A_Test.model.DataResponse> call, Response<A_Test.model.DataResponse> response) {
                    books.views.MyProgress.stop();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("true")) {

                            layoutOTP.setVisibility(View.GONE);
                            layoutEnterPassword.setVisibility(View.VISIBLE);
                            edtPhone.setText(strMobile);

                        }
                        Toast.makeText(ForgotPasswordTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<A_Test.model.DataResponse> call, Throwable t) {
                    books.views.MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void getChangePassword() {


        books.views.MyProgress.start(this);
        Call<A_Test.model.DataResponse> call = A_Test.Api.RetrofitClient.getRetrofitClient().getChangePassword(strMobile,password,cpassword);
        call.enqueue(new Callback<A_Test.model.DataResponse>() {
            @Override
            public void onResponse(Call<A_Test.model.DataResponse> call, Response<A_Test.model.DataResponse> response) {
                books.views.MyProgress.stop();

                if (response.body() != null)
                {
                    if (response.body().getStatus().equals("true")) {
                        Toast.makeText(ForgotPasswordTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(ForgotPasswordTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<A_Test.model.DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(ForgotPasswordTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
}