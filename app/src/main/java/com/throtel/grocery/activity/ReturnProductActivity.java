/*
package com.throtel.grocery.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CustomerProductList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.utils.FilePath;
import com.throtel.grocery.views.MyProgress;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnProductActivity extends BaseActivity {

    public static final int RequestPermissionCode = 100;
    Button button, btnSubmit;
    EditText edtReviewMsg;
    ImageView imageView;
    Intent intent;
   // public  static final int RequestPermissionCode  = 100 ;
    private CustomerProductList product = null;
    private String productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_product);

        if (getIntent() != null)
            product = (CustomerProductList) getIntent().getSerializableExtra("productList");
        setUpToolbarBackButton("Return Product");


        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        edtReviewMsg = findViewById(R.id.edt_msg);
        btnSubmit = findViewById(R.id.btn_submit);

        EnableRuntimePermission();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultipartBody.Part productImageMultipart = null;
                String reviewMsg = edtReviewMsg.getText().toString();
                if (TextUtils.isEmpty(reviewMsg)) {
                    showToast("Please Enter Review Message");

                } else {
                    if (productImage != null) {
                        File file = new File(productImage);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("mage/jpg"), file);
                        productImageMultipart = MultipartBody.Part.createFormData("productReturnImage", file.getName(), requestFile);

                        final RequestBody rbCustomerId = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getCustomerId());
                        final RequestBody rbCartId = RequestBody.create(MediaType.parse("Multipart/form-data"), String.valueOf(product.getCartId()));
                        final RequestBody rbReview = RequestBody.create(MediaType.parse("Multipart/form-data"), reviewMsg);

                        MyProgress.start(ReturnProductActivity.this);
                        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getReturn(rbCustomerId, rbCartId, productImageMultipart, rbReview);
                        call.enqueue(new Callback<DataResponse>() {
                            @Override
                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                MyProgress.stop();
                                Log.d("TAG", "" + response.body());
                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                                    showToast(response.body().getMessage());
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();

                                } else {
                                    Toast.makeText(ReturnProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DataResponse> call, Throwable t) {
                                MyProgress.stop();
                                Log.d("TAG", "" + t.getMessage());
                                Toast.makeText(ReturnProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {

                        showToast("Please Click Product Image");

                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            productImage = FilePath.getPath(ReturnProductActivity.this, tempUri);
            Log.d("BTAG", "PATH OF IMAGE 2...." + productImage);

        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ReturnProductActivity.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(ReturnProductActivity.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ReturnProductActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String[] per, int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                EnableRuntimePermission();

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    // Toast.makeText(ReturnProductActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ReturnProductActivity.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}
*/
package com.throtel.grocery.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CustomerProductList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.utils.FilePath;
import com.throtel.grocery.views.MyProgress;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnProductActivity extends BaseActivity
{

    Button button,btnSubmit ;
    EditText edtReviewMsg;
    ImageView imageView ;
    Intent intent ;
    public  static final int RequestPermissionCode  = 100 ;

    private CustomerProductList product = null;
    private String productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_product);

        if (getIntent() != null)
            product = (CustomerProductList) getIntent().getSerializableExtra("productList");
        setUpToolbarBackButton("Return Product");


        button = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageView);
        edtReviewMsg=findViewById(R.id.edt_msg);
        btnSubmit=findViewById(R.id.btn_submit);

        EnableRuntimePermission();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultipartBody.Part productImageMultipart = null;
                String reviewMsg=edtReviewMsg.getText().toString();
                if (TextUtils.isEmpty(reviewMsg))
                {
                    showToast("Please Enter Review Message");

                }
                else {
                    if (productImage != null) {
                        File file = new File(productImage);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("mage/jpg"), file);
                        productImageMultipart = MultipartBody.Part.createFormData("productReturnImage", file.getName(), requestFile);

                        final RequestBody rbCustomerId = RequestBody.create(MediaType.parse("Multipart/form-data"), localData.getCustomerId());
                        final RequestBody rbCartId = RequestBody.create(MediaType.parse("Multipart/form-data"), product.getCartId().toString());
                        final RequestBody rbReview = RequestBody.create(MediaType.parse("Multipart/form-data"), reviewMsg);

//                        MyProgress.start(ReturnProductActivity.this);
//                        Call<DataResponse> call = RetrofitClient.getRetrofitClient().getReturn(rbCustomerId, rbCartId, productImageMultipart, rbReview);
//                        call.enqueue(new Callback<DataResponse>() {
//                            @Override
//                            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                                MyProgress.stop();
//                                Log.d("TAG", "" + response.body());
//                                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                                    showToast(response.body().getMessage());
//                                    Intent intent = new Intent();
//                                    setResult(RESULT_OK, intent);
//                                    finish();
//
//                                } else {
//                                    Toast.makeText(ReturnProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<DataResponse> call, Throwable t) {
//                                MyProgress.stop();
//                                Log.d("TAG", "" + t.getMessage());
//                                Toast.makeText(ReturnProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                            }
//                        });



                    } else {

                        showToast("Please Click Product Image");

                    }
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            productImage = FilePath.getPath(ReturnProductActivity.this, tempUri);
            Log.d("BTAG","PATH OF IMAGE 2...."+productImage);

        }



    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(ReturnProductActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(ReturnProductActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ReturnProductActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    // Toast.makeText(ReturnProductActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ReturnProductActivity.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}