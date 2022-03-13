package A_Test.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.throtel.grocery.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import A_Test.Api.NetworkUtils;
import A_Test.Api.RetrofitClient;
import A_Test.model.CityDataResponse;
import A_Test.model.CityList;
import A_Test.model.PincodeList;
import A_Test.model.PincodeListDataResponse;
import A_Test.model.StateList;
import A_Test.model.StateListDataResponse;
import A_Test.model.UpdateUserLists;
import A_Test.model.UpdateUserTestResponse;
import A_Test.model.UserTestDetails;
import books.activity.BaseActivity;
import books.utils.Utils;
import books.views.MyProgress;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileTestActivity extends BaseActivity {
    private static final String TAG = UpdateProfileTestActivity.class.getSimpleName();
    private int PERMISSION_REQUEST_CODE = 401;
    private int PICK_IMAGE_CAMERA = 1000;
    private int PICK_IMAGE_GALLERY = 1001;
    private TextInputEditText edtName, edtEmail, edtPhone, edtSchoolName, edtClassName;
    private String name, email, phone, password, cpassword, schoolName, className,ids;
    private ArrayList<StateList> listState;
    private ArrayList<CityList> listCity;
    private ArrayList<PincodeList> listPincode;
    private Spinner spnCity, spnState, spnPincode, spnBatch;
    private String spnStateid, spnCityid, spnPinid, spnSelectedpin, spnBatchid;
    String url = "";
    private int id;
    private Uri outputFileUri = null;
    private File profileImageFile = null;
    String Picimage;
    private String iName,iPhone,iEmail,State,Distric,City;
    private Button btnUpdateChange;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_test);

//        Toast.makeText(getApplicationContext(), spnStateid.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "StateID: "+spnStateid+ " CityID:"+spnCityid+" TahsilID: "+spnPinid, Toast.LENGTH_SHORT).show();
        initViews();
        if (NetworkUtils.getConnectivityStatus(this)) {
            getStateList();
        }else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews() {


        listState = new ArrayList<>();
        listCity = new ArrayList<>();
        listPincode = new ArrayList<>();

        edtName = (TextInputEditText) findViewById(R.id.edt_Uname);
        edtEmail = (TextInputEditText) findViewById(R.id.edt_Uemail);
        edtPhone = (TextInputEditText) findViewById(R.id.edt_Uphone);
        edtSchoolName = (TextInputEditText) findViewById(R.id.edt_Uschool);
        edtClassName = (TextInputEditText) findViewById(R.id.edt_Uclass);
        spnState = findViewById(R.id.spn_state);
        spnCity = findViewById(R.id.spn_city);
        spnPincode = findViewById(R.id.spn_pincode);
        btnUpdateChange = findViewById(R.id.btnTestUpdateChange);

        Intent data = getIntent();
        iName = data.getStringExtra("name");
        iPhone = data.getStringExtra("phone");
        iEmail = data.getStringExtra("email");
        State = data.getStringExtra("state");
        Distric = data.getStringExtra("distric");
        City = data.getStringExtra("pincode");
        Picimage = data.getStringExtra("imageUri");
//        Picimage = data.get

        edtName.setText(iName);
        edtPhone.setText(iPhone);
        edtEmail.setText(iEmail);

//        if (profileImageFile != null) {
//            //      File file = new File(profileImage);
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), profileImageFile);
//            profileImageMultipart = MultipartBody.Part.createFormData("profileImage", profileImageFile.getName(), requestFile);
//        } else {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), "");
//            profileImageMultipart = MultipartBody.Part.createFormData("profileImage", "", requestFile);
//        }

        //
//        id = localData.getTestSignIn().getUserId();
        btnUpdateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validate();

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
                    Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }
            @Override
            public void onFailure(Call<A_Test.model.StateListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<A_Test.model.CityDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnPincode.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<A_Test.model.PincodeListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(UpdateProfileTestActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getIndex(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private void validate() {
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        schoolName = edtSchoolName.getText().toString();
        className = edtClassName.getText().toString();

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
        }else if (spnStateid.contains("Select State")) {
            showToast("Please Select State");
        } else if (spnCityid.contains("Select City")) {
            showToast("Please Select City");
        } else if (spnSelectedpin.contains("Select Tahsil")) {
            showToast("Please Select Tahsil");
        } else {
            UpdateProfile();
        }
    }

    private void UpdateProfile() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), "");
        MultipartBody.Part profileImageMultipart = MultipartBody.Part.createFormData("profileImage", "", requestFile);

        final RequestBody rbId = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getCustomerId());
        final RequestBody rbName = RequestBody.create(MediaType.parse("Multipart/form-data"), edtName.getText().toString());
        final RequestBody rbEmail = RequestBody.create(MediaType.parse("Multipart/form-data"), edtEmail.getText().toString());
        final RequestBody rbPhone = RequestBody.create(MediaType.parse("Multipart/form-data"), edtPhone.getText().toString());
        final RequestBody rbStateId = RequestBody.create(MediaType.parse("Multipart/form-data"), spnStateid);
        final RequestBody rbCityId = RequestBody.create(MediaType.parse("Multipart/form-data"), spnCityid);
        final RequestBody rbPinId = RequestBody.create(MediaType.parse("Multipart/form-data"), spnCityid);
        final RequestBody rsSchoolName = RequestBody.create(MediaType.parse("Multipart/form-data"), edtSchoolName.getText().toString());
        final RequestBody rsClass = RequestBody.create(MediaType.parse("Multipart/form-data"), edtClassName.getText().toString());

        MyProgress.start(this);
        Call<UpdateUserTestResponse> call = A_Test.Api.RetrofitClient.getRetrofitClient().getUpdateProfileTest(rbId,
                rbName,rbEmail,rbPhone,
                profileImageMultipart, rbStateId,rbCityId,rbPinId,rsSchoolName,rsClass);
        call.enqueue(new Callback<UpdateUserTestResponse>() {
            @Override
            public void onResponse(Call<UpdateUserTestResponse> call, retrofit2.Response<UpdateUserTestResponse> response) {
                Utils.hideKeyboard(UpdateProfileTestActivity.this);
                MyProgress.stop();
                if(response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Utils.hideKeyboard(UpdateProfileTestActivity.this);
                        UpdateUserLists updateUserLists = response.body().getData().getTestUserResponse();
                        localData.setTestLoggedIn(true);
                        localData.setTestUpdate(updateUserLists);
                        localData.setCustomerId(String.valueOf(updateUserLists.getId()));
//                        UserTestDetails userTestDetails = response.body().getData().getTestUserResponse();
//                        // save user data
//                        localData.setTestLoggedIn(true);
//                        localData.setTestSignIn(userTestDetails);
//                        localData.setCustomerId(String.valueOf(userTestDetails.getUserId()));
                        Toast.makeText(UpdateProfileTestActivity.this, "Sucessfully Update", Toast.LENGTH_SHORT).show();
                    }else {
                        //showAlertForEnquiry();
                        Toast.makeText(UpdateProfileTestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateProfileTestActivity.this, "Not Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserTestResponse> call, Throwable t) {
                Toast.makeText(UpdateProfileTestActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//
//    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor =managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getContentResolver() != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }
public Bitmap StringToBitMap(String image){
    try{
        byte [] encodeByte=Base64.decode(image, Base64.DEFAULT);

        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }catch(Exception e){
        e.getMessage();
        return null; }
    }

}