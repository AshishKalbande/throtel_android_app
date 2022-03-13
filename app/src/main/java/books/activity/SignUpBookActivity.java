package books.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.throtel.grocery.R;

import java.util.ArrayList;

import books.api.RetrofitClient;
import books.models.CityDataResponse;
import books.models.CityList;
import books.models.DataResponse;
import books.models.PincodeList;
import books.models.PincodeListDataResponse;
import books.models.StateList;
import books.models.StateListDataResponse;
import books.utils.Constants;
import books.utils.NetworkUtil;
import books.utils.Utils;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpBookActivity extends AppCompatActivity {
    private static final String TAG = SignUpBookActivity.class.getSimpleName();
    private LinearLayout llLogin;
    private MaterialButton btnRegister;
    private TextInputEditText edtName, edtEmail, edtPhone, edtPassword, edtCPassword, edtSchoolName, edtClassName;

    private String name, email, phone, password, cpassword, schoolName, className;

    private ArrayList<StateList> listState;
    private ArrayList<CityList> listCity;
    private ArrayList<PincodeList> listPincode;
    private Spinner spnCity, spnState, spnPincode;
    private String spnStateid, spnCityid, spnPinid, spnSelectedpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_book);

        initViews();
        if (NetworkUtil.getConnectivityStatus(this))
            getStateList();
        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

    }

    private void initViews() {

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpBookActivity.this, LoginbookActivity.class);
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
            Toast.makeText(this, "Please enter Full Name", Toast.LENGTH_SHORT).show();
        }  else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 10) {
            Toast.makeText(this, "Please enter valid Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(schoolName)) {
            Toast.makeText(this, "Please enter School Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(className)) {
            Toast.makeText(this, "Please enter Class Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cpassword)) {
            Toast.makeText(this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(cpassword)) {
            Toast.makeText(this, "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
        } else if (spnStateid.contains("Select State")) {
            Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show();
        } else if (spnCityid.contains("Select District")) {
            Toast.makeText(this, "Please Select District", Toast.LENGTH_SHORT).show();
        } else if (spnSelectedpin.contains("Select Tahsil")) {
            Toast.makeText(this, "Please Select Tahsil", Toast.LENGTH_SHORT).show();
        } else {
            callSignUpApi();
        }
    }
    private void getStateList() {
        MyProgress.start(this);
        Call<StateListDataResponse> call = RetrofitClient.getRetrofitClient().getStateList();
        call.enqueue(new Callback<StateListDataResponse>() {
            @Override
            public void onResponse(Call<StateListDataResponse> call, Response<StateListDataResponse> response) {
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
                    Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }
            @Override
            public void onFailure(Call<StateListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCityList(String StateId) {
        //MyProgress.start(this);
        Call<CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
        call.enqueue(new Callback<CityDataResponse>() {
            @Override
            public void onResponse(Call<CityDataResponse> call, Response<CityDataResponse> response) {
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
                    Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<CityDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getPincodeList(String CityId) {
        // MyProgress.start(this);
        Call<PincodeListDataResponse> call = RetrofitClient.getRetrofitClient().getTahsilList(CityId);
        call.enqueue(new Callback<PincodeListDataResponse>() {
            @Override
            public void onResponse(Call<PincodeListDataResponse> call, Response<PincodeListDataResponse> response) {
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
                    Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnPincode.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<PincodeListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callSignUpApi() {

        MyProgress.start(this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getSignUpBook(name, email, phone,schoolName, className, password, cpassword, spnStateid, spnCityid, spnPinid);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                Utils.hideKeyboard(SignUpBookActivity.this);
                MyProgress.stop();

                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        Toast.makeText(SignUpBookActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignUpBookActivity.this, OTPVerifyActivity.class);
                        intent.putExtra(Constants.MOBILE, phone);
                        intent.putExtra(Constants.SOCIALMOBILE, "");
                        startActivity(intent);
                        finish();
                    } else {
                        //showAlertForEnquiry();
                        Toast.makeText(SignUpBookActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpBookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(SignUpBookActivity.this, "Invalid Mobile Number..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}