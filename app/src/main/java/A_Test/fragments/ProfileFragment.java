package A_Test.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.throtel.grocery.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import A_Test.Activity.UpdateProfileTestActivity;
import A_Test.Api.RetrofitClient;
import A_Test.model.CityList;
import A_Test.model.PincodeList;
import A_Test.model.StateList;
import A_Test.model.StateListDataResponse;

import A_Test.model.UpdateUserTestResponse;
import books.fragments.BaseFragment;
import books.utils.Utils;
import books.views.MyProgress;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends BaseFragment {
    private ArrayList<StateList> listState;
    private ArrayList<CityList> listCity;
    private ArrayList<PincodeList> listPincode;
    private int PICK_IMAGE_CAMERA = 1234;
    private int PERMISSION_CAMERA_REQUEST_CODE = 500;
    private Uri outputFileUri = null;
    private File profileImageFile = null;
    private static final String IMAGE_DIRECTORY = "/throtel_profile";
    public static int REQUEST_CODE = 102;
    public static int CAMERA_CODE = 400;
    public static int GALLERY_CODE = 500;
    private View view;
    CircularImageView ivProfile;

//    CharSequence[] options = {"Camera", "Gallery", "Cancel"};
//    private String selectImage;
    private TextView tvName, tvMobile;
    private TextView tvSchool, tvClass, tvAdress, tvEmail;
    ActivityResultLauncher<String> activityResultLauncher;
    ActivityResultLauncher<Intent> activityResultLauncherCamera;
    ActivityResultLauncher<String> activityResultLauncherCameraPermission;


    private Button btnUpdate;
    private String name, email, phone;
//    private RelativeLayout rlAddress;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
//        imgLogo.setVisibility(View.GONE);
        initView();
        setView();
//        getStateList();

        return view;
    }

    private void initView() {
        ivProfile = view.findViewById(R.id.iv_profiles);
        tvName = view.findViewById(R.id.tv_name);
        tvMobile = view.findViewById(R.id.tv_phone);

        tvSchool = view.findViewById(R.id.tv_schoolt);
        tvClass = view.findViewById(R.id.tv_classt);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAdress = view.findViewById(R.id.tv_address);
        btnUpdate = view.findViewById(R.id.btnTestUpdate);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                ivProfile.setImageURI(result);

                Bitmap bitmap = null;
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver() , Uri.parse(String.valueOf(result)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri imageGurl  = getImageUri(getContext(),bitmap);
                File finalFile = new File(getRealPathFromURI(imageGurl));
                UpdateProfile(finalFile);
            }
        });

        activityResultLauncherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    ivProfile.setImageBitmap(bitmap);

                    Uri tempUri = getImageUri(getActivity(), bitmap);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    UpdateProfile(finalFile);

                }
            }
        });

        activityResultLauncherCameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intents.resolveActivity(getContext().getPackageManager()) != null) {
                        activityResultLauncherCamera.launch(intents);
                    } else {
                        Toast.makeText(context, "There is no app", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tvName.getText().toString();
                phone = tvMobile.getText().toString();
                email = tvEmail.getText().toString();
                Intent intent = new Intent(getContext(), UpdateProfileTestActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("state", localData.getTestSignIn().getStatus());
                intent.putExtra("distric", localData.getTestSignIn().getDistrictName());
                intent.putExtra("pincode", localData.getTestSignIn().getTahsilName());
//                intent.putExtra("image",);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activityResultLauncherCameraPermission.launch(Manifest.permission.CAMERA);
                chooseProfile();
            }
        });


    }




//    private void secondChooseProfile() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Select Image");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (options[which].equals("Camera")){
//
//                    Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePic,CAMERA_CODE);
//                    UpdateProfile();
//                }
//                else if (options[which].equals("Gallery")){
//                    Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(gallery,GALLERY_CODE);
//                }else {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }


    @Override
    public void onResume() {
        setView();
        super.onResume();
    }

    private void setView() {
//        String url = RetrofitClient.BASE_CUSTOMER_IMAGE_URL + localData.getSignIn().getProfileImage();

//        String url = localData.getTestSignIn().getProfileImage();
//        Picasso.with(getContext())
//                .load(localData.getTestSignIn().getProfileImage()) //Load the image
//                .fit()
//                .error(R.drawable.ic_user)
//                .into(ivProfile);

//        String url = RetrofitClient.BASE_URL_Test+"login" + localData.getTestSignIn().getProfileImage();
//        Glide.with(getContext())
//                .load(url)
//                .into(ivProfile);


        tvName.setText(localData.getTestSignIn().getName());
        tvMobile.setText(localData.getTestSignIn().getPhone());
        tvSchool.setText(localData.getTestSignIn().getSchoolName());
        tvClass.setText(localData.getTestSignIn().getClassName());
        tvEmail.setText(localData.getTestSignIn().getEmail());
        tvAdress.setText(localData.getTestSignIn().getTahsilName() + "-" + localData.getTestSignIn().getDistrictName());


    }

    private void chooseProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.choose_photo_alert_dialog, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        TextView tvCamera = dialogView.findViewById(R.id.tvCamera);
        TextView tvGallery = dialogView.findViewById(R.id.tvGallery);
        TextView tvCancel = dialogView.findViewById(R.id.tvCancelDialog);

        AlertDialog alertDialogPhotoPic = builder.create();
        alertDialogPhotoPic.show();

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncherCameraPermission.launch(Manifest.permission.CAMERA);
                alertDialogPhotoPic.dismiss();

            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPhotoPic.dismiss();
                activityResultLauncher.launch("image/*");
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPhotoPic.dismiss();
            }
        });

    }

//    private void getStateList(){
//        MyProgress.start(getContext());
//        Call<StateListDataResponse> call = RetrofitClient.getRetrofitClient().getStateList();
//        call.enqueue(new Callback<StateListDataResponse>() {
//            @Override
//            public void onResponse(Call<StateListDataResponse> call, Response<StateListDataResponse> response) {
//                MyProgress.stop();
////                Log.d(TAG, "" + response.toString());
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    listState.addAll(response.body().getData().getStateList());
//                    //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());
//
//                    ArrayList<String> services = new ArrayList<>();
//                    services.add("Select State");
//                    for (int i = 0; i < listState.size(); i++) {
//                        services.add(listState.get(i).getStateName());
//                        if (listState.get(i).getStateName() == localData.getTestSignIn().getStateName()){
//                            Toast.makeText(context, "+"+i, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, services);
////                     spn_service_type.setPrompt("Service Type");
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                    spnCity.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StateListDataResponse> call, Throwable t) {
//
//            }
//        });
//    }

//        private void getCityList(String StateId) {
//        //MyProgress.start(this);
////            Call<CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
//        Call<A_Test.model.CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
//        call.enqueue(new Callback<A_Test.model.CityDataResponse>() {
//            @Override
//            public void onResponse(Call<A_Test.model.CityDataResponse> call, Response<A_Test.model.CityDataResponse> response) {
//                MyProgress.stop();
////                Log.d(TAG, "" + response.toString());
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    listCity.addAll(response.body().getData().getCityList());
//                    //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());
//
//                    ArrayList<String> services = new ArrayList<>();
//                    services.add("Select City");
//                    for (int i = 0; i < listCity.size(); i++) {
//                        services.add(listCity.get(i).getCityName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, services);
//                    // spn_service_type.setPrompt("Service Type");
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spnCity.setAdapter(adapter);
//                    spnPincode.setAdapter(null);
//                    spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            spnCityid = parent.getItemAtPosition(position).toString();
//                            if (position != 0) {
//                                if (!spnCityid.equals("Select District")) {
//                                    listPincode.clear();
//                                    spnCityid = String.valueOf(listCity.get(position - 1).getCityId());
//                                    getPincodeList(spnCityid);
//                                    spnPincode.setVisibility(View.VISIBLE);
//                                } else {
//                                    listPincode.clear();
//                                    //getPincodeList("1");
//                                    spnCity.setAdapter(null);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                    spnCity.setAdapter(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<A_Test.model.CityDataResponse> call, Throwable t) {
//                MyProgress.stop();
////                Log.d(TAG, "" + t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void getPincodeList(String CityId) {
//        // MyProgress.start(this);
////            Call<PincodeListDataResponse> call = RetrofitClient.getRetrofitClient().getTahsilList(CityId);
//        Call<A_Test.model.PincodeListDataResponse> call = RetrofitClient.getRetrofitClient().getTahsilList(CityId);
//        call.enqueue(new Callback<A_Test.model.PincodeListDataResponse>() {
//            @Override
//            public void onResponse(Call<A_Test.model.PincodeListDataResponse> call, Response<A_Test.model.PincodeListDataResponse> response) {
//                MyProgress.stop();
////                Log.d(TAG, "" + response.toString());
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    listPincode.addAll(response.body().getData().getPincodeList());
//                    //Log.d("BTAG", "LIST SERVICES.." + listServices.get(1).getServiceName());
//
//                    ArrayList<String> services = new ArrayList<>();
//                    services.add("Select Tahsil");
//                    for (int i = 0; i < listPincode.size(); i++) {
//                        services.add(listPincode.get(i).getPincodeNumber());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, services);
//                    // spn_service_type.setPrompt("Service Type");
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spnPincode.setAdapter(adapter);
//                    spnPincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            spnSelectedpin = parent.getItemAtPosition(position).toString();
//                            if (position != 0) {
//                                if (!spnSelectedpin.equals("Select Pincode")) {
//                                    spnPinid = String.valueOf(listPincode.get(position - 1).getPincodeId());
//                                } else {
//                                    spnPinid = String.valueOf(position);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                    spnPincode.setAdapter(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<A_Test.model.PincodeListDataResponse> call, Throwable t) {
//                MyProgress.stop();
////                Log.d(TAG, "" + t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private int getIndex(Spinner spinner, String s) {
//        for (int i = 0; i < spinner.getCount(); i++) {
//            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)) {
//                return i;
//            }
//        }
//        return 0;
//    }

    private void UpdateProfile(File file) {

        // profile pic
        RequestBody requestBody = RequestBody.create(MediaType.parse("Multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profileImage", file.getName(), requestBody);

        final RequestBody rbId = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getCustomerId());
        final RequestBody rbName = RequestBody.create(MediaType.parse("Multipart/form-data"), "abc ark");
        final RequestBody rbEmail = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getTestSignIn().getEmail());
        final RequestBody rbPhone = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getTestSignIn().getPhone());
        final RequestBody rbStateId = RequestBody.create(MediaType.parse("Multipart/form-data"), "1");
        final RequestBody rbCityId = RequestBody.create(MediaType.parse("Multipart/form-data"), "1");
        final RequestBody rbPinId = RequestBody.create(MediaType.parse("Multipart/form-data"), "1");

        final RequestBody rsSchoolName = RequestBody.create(MediaType.parse("Multipart/form-data"), "abc");
        final RequestBody rsClass = RequestBody.create(MediaType.parse("Multipart/form-data"), "x");

        MyProgress.start(getActivity());
        Call<UpdateUserTestResponse> call = A_Test.Api.RetrofitClient.getRetrofitClient().getUpdateProfileTest(rbId,
                rbName, rbEmail, rbPhone,
                fileToUpload, rbStateId, rbCityId, rbPinId, rsSchoolName, rsClass);
        call.enqueue(new Callback<UpdateUserTestResponse>() {
            @Override
            public void onResponse(Call<UpdateUserTestResponse> call, Response<UpdateUserTestResponse> response) {
                Utils.hideKeyboard(getActivity());
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(getActivity(), "Sucessfully Update", Toast.LENGTH_SHORT).show();
                    } else {
                        //showAlertForEnquiry();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Not Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserTestResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}