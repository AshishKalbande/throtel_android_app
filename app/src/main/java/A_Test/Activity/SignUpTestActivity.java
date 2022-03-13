package A_Test.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.throtel.grocery.R;

import java.util.ArrayList;

import A_Test.Api.RetrofitClient;
import A_Test.model.BatchDataResponse;
import A_Test.model.BatchList;
import A_Test.model.CityList;
import A_Test.model.DataResponse;
import A_Test.model.PincodeList;
import A_Test.model.StateList;
import books.activity.BaseActivity;
import books.activity.LoginbookActivity;
import books.activity.OTPVerifyActivity;
import books.activity.SignUpActivity;
import books.activity.SignUpBookActivity;
import books.models.CityDataResponse;
import books.models.PincodeListDataResponse;
import books.models.StateListDataResponse;
import books.utils.Constants;
import books.utils.NetworkUtil;
import books.utils.Utils;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpTestActivity extends BaseActivity {

    private static final String TAG = SignUpTestActivity.class.getSimpleName();
    private LinearLayout llLogin;
    private MaterialButton btnRegister;
    private TextInputEditText edtName, edtEmail, edtPhone, edtPassword, edtCPassword, edtSchoolName, edtClassName;

    private String name, email, phone, password, cpassword, schoolName, className;

    private ArrayList<BatchList> listBatch;
    private ArrayList<StateList> listState;
    private ArrayList<CityList> listCity;
    private ArrayList<PincodeList> listPincode;
    private Spinner spnCity, spnState, spnPincode, spnBatch;
    private String spnStateid, spnCityid, spnPinid, spnSelectedpin, spnBatchid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_test);

            initViews();

            if (NetworkUtil.getConnectivityStatus(this)) {
                getBatchList();
                getStateList();
            }else {
                Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }

        private void initViews() {

            listBatch = new ArrayList<>();
            listState = new ArrayList<>();
            listCity = new ArrayList<>();
            listPincode = new ArrayList<>();

            llLogin = (LinearLayout) findViewById(R.id.ll_login);
            btnRegister = (MaterialButton) findViewById(R.id.btn_register);
            edtName = (TextInputEditText) findViewById(R.id.edt_name);
            edtEmail = (TextInputEditText) findViewById(R.id.edt_email);
            edtPhone = (TextInputEditText) findViewById(R.id.edt_phone);
            edtSchoolName = (TextInputEditText) findViewById(R.id.edt_schoolName);
            edtClassName = (TextInputEditText) findViewById(R.id.edt_className);
            edtPassword = (TextInputEditText) findViewById(R.id.edt_password);
            edtCPassword = (TextInputEditText) findViewById(R.id.edt_confirm_password);
            spnState = findViewById(R.id.spn_state);
            spnCity = findViewById(R.id.spn_city);
            spnPincode = findViewById(R.id.spn_pincode);
            spnBatch = findViewById(R.id.spn_batch);


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validate();
                }
            });


            llLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SignUpTestActivity.this, LoginTestActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void validate() {
            name = edtName.getText().toString();
            email = edtEmail.getText().toString();
            phone = edtPhone.getText().toString();
            schoolName = edtSchoolName.getText().toString();
            className = edtClassName.getText().toString();
            password = edtPassword.getText().toString();
            cpassword = edtCPassword.getText().toString();


            if (TextUtils.isEmpty(name)) {
                showToast("Please enter Full Name");
            }  else if (TextUtils.isEmpty(email)) {
                showToast("Please enter Email Name");
            }else if (!isValidMail(email)) {
                showToast("Please enter valid Email");
            } else if (TextUtils.isEmpty(phone)) {
                showToast("Please enter Mobile Number");
            } else if (phone.length() != 10 || !isValidMobile(phone)) {
                showToast("Please enter valid Mobile Number");
            } else if (TextUtils.isEmpty(schoolName)) {
                showToast("Please enter School Name");
            } else if (TextUtils.isEmpty(className)) {
                showToast("Please enter Class Name");
            } else if (TextUtils.isEmpty(password)) {
                showToast("Please enter Password");
            } else if (TextUtils.isEmpty(cpassword)) {
                showToast("Please enter Confirm Password");
            } else if (!password.equals(cpassword)) {
                showToast("Password and Confirm Password does not match");
            } else if (spnStateid.contains("Select State")) {
                showToast("Please Select State");
            } else if (spnCityid.contains("Select District")) {
                showToast("Please Select District");
            } else if (spnSelectedpin.contains("Select Tahsil")) {
                showToast("Please Select Tahsil");
            }else if (spnBatchid.contains("Select Batch")) {
                showToast("Please Select Batch");
            } else {
                callSignUpApi();
            }
        }

        private void getBatchList(){
        MyProgress.start(this);
        Call<BatchDataResponse> call = RetrofitClient.getRetrofitClient().getBatchList();

        call.enqueue(new Callback<BatchDataResponse>() {
            @Override
            public void onResponse(Call<BatchDataResponse> call, Response<BatchDataResponse> response) {

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")){
                    listBatch.addAll(response.body().getData().getBatchList());
                    ArrayList<String> services = new ArrayList<>();
                    services.add("Select Batch");
                    for (int i=0; i<listBatch.size(); i++){
                        services.add(listBatch.get(i).getBatchName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnBatch.setAdapter(adapter);
                    spnBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spnBatchid = parent.getItemAtPosition(position).toString();

                            if (!spnBatchid.equals("Select Batch")) {
                                listCity.clear();
                                spnBatchid = String.valueOf(listBatch.get(position - 1).getBatchId());
                            } else {
                                //getCityList("1");

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BatchDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
        private void getStateList() {
            MyProgress.start(this);
            Call<A_Test.model.StateListDataResponse> call = RetrofitClient.getRetrofitClient().getStateList();
//            Call<StateListDataResponse> call = RetrofitClient.getRetrofitClient().getStateList();
            call.enqueue(new Callback<A_Test.model.StateListDataResponse>() {
                @Override
                public void onResponse(Call<A_Test.model.StateListDataResponse> call, Response<A_Test.model.StateListDataResponse> response) {
                    MyProgress.stop();
                    Log.d(TAG, "" + response.toString());
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                        listState.addAll(response.body().getData().getStateList());
                        //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());

                        ArrayList<String> services = new ArrayList<>();
                        services.add("Select State");
                        for (int i = 0; i < listState.size(); i++) {
                            services.add(listState.get(i).getStateName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
                        // spn_service_type.setPrompt("Service Type");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnState.setAdapter(adapter);
                        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                                spnStateid = parent.getItemAtPosition(position).toString();
                                if (position != 0) {
                                    //Log.d("BTAG","SELECTED STATE ID..."+spnStateid);
                                    if (!spnStateid.equals("Select State")) {
                                        listCity.clear();
                                        spnStateid = String.valueOf(listState.get(position - 1).getStateId());
                                        getCityList(spnStateid);
                                        spnCity.setVisibility(View.VISIBLE);
                                    } else {
                                        listCity.clear();
                                        //getCityList("1");
                                        spnCity.setAdapter(null);
                                        spnPincode.setAdapter(null);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                        spnCity.setAdapter(null);
                    }
                }
                @Override
                public void onFailure(Call<A_Test.model.StateListDataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void getCityList(String StateId) {
            //MyProgress.start(this);
//            Call<CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
            Call<A_Test.model.CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
            call.enqueue(new Callback<A_Test.model.CityDataResponse>() {
                @Override
                public void onResponse(Call<A_Test.model.CityDataResponse> call, Response<A_Test.model.CityDataResponse> response) {
                    MyProgress.stop();
                    Log.d(TAG, "" + response.toString());
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                        listCity.addAll(response.body().getData().getCityList());
                        //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());

                        ArrayList<String> services = new ArrayList<>();
                        services.add("Select City");
                        for (int i = 0; i < listCity.size(); i++) {
                            services.add(listCity.get(i).getCityName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
                        // spn_service_type.setPrompt("Service Type");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnCity.setAdapter(adapter);
                        spnPincode.setAdapter(null);
                        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                spnCityid = parent.getItemAtPosition(position).toString();
                                if (position != 0) {
                                    if (!spnCityid.equals("Select District")) {
                                        listPincode.clear();
                                        spnCityid = String.valueOf(listCity.get(position - 1).getCityId());
                                        getPincodeList(spnCityid);
                                        spnPincode.setVisibility(View.VISIBLE);
                                    } else {
                                        listPincode.clear();
                                        //getPincodeList("1");
                                        spnCity.setAdapter(null);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                        spnCity.setAdapter(null);
                    }
                }

                @Override
                public void onFailure(Call<A_Test.model.CityDataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void getPincodeList(String CityId) {
            // MyProgress.start(this);
//            Call<PincodeListDataResponse> call = RetrofitClient.getRetrofitClient().getTahsilList(CityId);
            Call<A_Test.model.PincodeListDataResponse> call = RetrofitClient.getRetrofitClient().getTahsilList(CityId);
            call.enqueue(new Callback<A_Test.model.PincodeListDataResponse>() {
                @Override
                public void onResponse(Call<A_Test.model.PincodeListDataResponse> call, Response<A_Test.model.PincodeListDataResponse> response) {
                    MyProgress.stop();
                    Log.d(TAG, "" + response.toString());
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                        listPincode.addAll(response.body().getData().getPincodeList());
                        //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());

                        ArrayList<String> services = new ArrayList<>();
                        services.add("Select Tahsil");
                        for (int i = 0; i < listPincode.size(); i++) {
                            services.add(listPincode.get(i).getPincodeNumber());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
                        // spn_service_type.setPrompt("Service Type");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnPincode.setAdapter(adapter);
                        spnPincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                spnSelectedpin = parent.getItemAtPosition(position).toString();
                                if (position != 0) {
                                    if (!spnSelectedpin.equals("Select Pincode")) {
                                        spnPinid = String.valueOf(listPincode.get(position - 1).getPincodeId());
                                    } else {
                                        spnPinid = String.valueOf(position);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                        spnPincode.setAdapter(null);
                    }
                }

                @Override
                public void onFailure(Call<A_Test.model.PincodeListDataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void callSignUpApi()
        {

            MyProgress.start(this);
            Call<A_Test.model.DataResponse> call = A_Test.Api.RetrofitClient.getRetrofitClient().getSignUpTest(name, email, phone,schoolName, className, password, cpassword, spnStateid, spnCityid, spnPinid,spnBatchid);
//            Call<DataResponse> call = RetrofitClient.getRetrofitClient().getSignUpBook(name, email, phone,schoolName, className, password, cpassword, spnStateid, spnCityid, spnPinid);
            call.enqueue(new Callback<A_Test.model.DataResponse>() {
                @Override

                public void onResponse(Call<A_Test.model.DataResponse> call, Response<DataResponse> response) {
                    Utils.hideKeyboard(SignUpTestActivity.this);
                    MyProgress.stop();

                    if (response.body() != null) {
                        if (response.body().getStatus().equals("true")) {
                            Utils.hideKeyboard(SignUpTestActivity.this);
                            Toast.makeText(SignUpTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpTestActivity.this, VerifyOtpTestActivity.class);
                            intent.putExtra(Constants.MOBILE, phone);
                            intent.putExtra(Constants.SOCIALMOBILE, "");
                            startActivity(intent);
                            finish();
                        } else {
                            //showAlertForEnquiry();
                            Toast.makeText(SignUpTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<A_Test.model.DataResponse> call, Throwable t) {
                    MyProgress.stop();
                    Log.d(TAG, "" + t.getMessage());
                    Toast.makeText(SignUpTestActivity.this, "Invalid Mobile Number..", Toast.LENGTH_SHORT).show();
                }
            });
        }

}