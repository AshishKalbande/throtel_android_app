package books.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.throtel.grocery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import books.api.RetrofitClient;
import books.fcm.Config;
import books.models.CityDataResponse;
import books.models.CityList;
import books.models.DataResponse;
import books.models.LoginDataResponse;
import books.models.PincodeList;
import books.models.PincodeListDataResponse;
import books.models.StateList;
import books.models.StateListDataResponse;
import books.models.UserBookDetail;
import books.utils.Constants;
import books.utils.NetworkUtil;
import books.utils.Utils;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginbookActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = LoginbookActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 1;
    public static String idToken, idAuth, signUpBy;
    String email_mobile, password;
    String name, email, phone;
    private LinearLayout llSignUp;
    private EditText edtEmailMobile, edtPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient googleSignInClient;
    // private SignInButton signInButton;
    private Button signInButton;
    private ArrayList<StateList> listState;
    private ArrayList<CityList> listCity;
    private ArrayList<PincodeList> listPincode;
    private Spinner spnCity, spnState, spnPincode;
    private String spnStateid, spnCityid, spnPinid, spnSelectedpin;
    private String mobile;
    GoogleApiClient mGoogleApiClient;


    //private static final int RC_SIGN_IIN = 12345;
    private LoginButton loginButton;
    private Button loginFb;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessToken;

    // private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        //======= Google Login =======


        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user != null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    //     startActivity(new Intent(LoginActivity.this,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    name = user.getDisplayName();
                    email = user.getEmail();
                    idAuth = user.getUid();
                    // idToken = user


                } else {
                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
//        signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //  Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//             /*   OptionalPendingResult<GoogleSignInResult> pendingResult =
//                        Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);*/
//
//                Intent signInIntent = googleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
//            }
//        });

        firebaseAuth = FirebaseAuth.getInstance();

        //======== Facebook Login =======
//        loginFb = findViewById(R.id.sign_in_fb);
//        loginFb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginButton.performClick();
//            }
//        });
//        // Initialize Facebook Login button
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        loginButton = findViewById(R.id.login_button);
//        mCallbackManager = CallbackManager.Factory.create();
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

        mAccessToken = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                } else {
                    idToken = currentAccessToken.getToken();

                    getFbInfo();
                }
            }
        };
    }


    private void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        AuthCredential credential = FacebookAuthProvider.getCredential(idToken);
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        try {
                            Log.d("LOG_TAG", "fb json object: " + object);
                            Log.d("LOG_TAG", "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");

                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                            if (object.has("email")) {
                                email = object.getString("email");
                            }
                            //   idAuth = user.getUid();
                            //firebaseAuth =
                            name = first_name + " " + last_name;
                            signUpBy = "FaceBook";
                            MyProgress.stop();
                            Log.d("BTAG", "PARAM SOCIAL LOGIN..." + idAuth + " signup by :" + signUpBy + " Id Token " + idToken);
                            // callSocialLoginApi();
//                            if (idAuth != null)
//                                callSocialLoginApi();
//                            else {
//                                showAlert();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //callLoginApi();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void initViews() {

        listState = new ArrayList<>();
        listCity = new ArrayList<>();
        listPincode = new ArrayList<>();

        llSignUp = findViewById(R.id.ll_sign_up);
        edtEmailMobile = findViewById(R.id.edt_email_mobile);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginbookActivity.this, SignUpActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(LoginbookActivity.this).create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                final EditText edtMobile = dialogView.findViewById(R.id.edt_mobile);
                Button submit = dialogView.findViewById(R.id.buttonSubmit);
                Button cancel = dialogView.findViewById(R.id.buttonCancel);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(edtMobile.getText().toString())) {
                            showToast("Please enter mobile number");
                        } else {
                            MyProgress.start(LoginbookActivity.this);
                            Call<DataResponse> call = RetrofitClient.getRetrofitClient().forgotGenerateOTP(edtMobile.getText().toString().trim());
                            call.enqueue(new Callback<DataResponse>() {
                                @Override
                                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                    MyProgress.stop();
                                    if (response.body() != null) {
                                        if (response.body().getStatus().equals("true")) {

                                            Intent intent = new Intent(LoginbookActivity.this, ForgotPasswordActivity.class);
                                            intent.putExtra(Constants.MOBILE, edtMobile.getText().toString());
                                            startActivity(intent);
                                            dialogBuilder.dismiss();

                                        } else {
                                            Toast.makeText(LoginbookActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else
                                        Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DataResponse> call, Throwable t) {
                                    MyProgress.stop();
                                    Log.d(TAG, "" + t.getMessage());
                                    Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // DO SOMETHINGS
                        dialogBuilder.dismiss();
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Due to your Facebook Account privacy policy, you can not Sign Up. Please Sign Up manually!!")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void validate() {
        email_mobile = edtEmailMobile.getText().toString();
        password = edtPassword.getText().toString();


        if (TextUtils.isEmpty(email_mobile)) {
            showToast("Please enter valid mobile number");
        } else if (TextUtils.isEmpty(password)) {
            showToast("Please enter password");
        } else if (!isValidMobile(email_mobile)) {
            showToast("Please enter valid mobile number");
            return;
        } else {

            callLoginApi();
        }

    }

    private void callLoginApi() {

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);


        MyProgress.start(this);
        Call<LoginDataResponse> call = RetrofitClient.getRetrofitClient().login(email_mobile, password, regId);
        call.enqueue(new Callback<LoginDataResponse>() {
            @Override
            public void onResponse(Call<LoginDataResponse> call, Response<LoginDataResponse> response) {
                Utils.hideKeyboard(LoginbookActivity.this);
                MyProgress.stop();

                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        Toast.makeText(LoginbookActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        UserBookDetail userBookDetails = response.body().getData().getUserDetail();
                        // save user data
                        localData.setBookLoggedIn(true);
                        localData.setBookSignIn(userBookDetails);
                        localData.setCustomerId(String.valueOf(userBookDetails.getUserId()));

                        startActivity(new Intent(LoginbookActivity.this, HomeBookActivity.class));
                        finishAffinity();
                    } else {
                        //showAlertForEnquiry();
                        Toast.makeText(LoginbookActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(LoginbookActivity.this, "Invalid Mobile Number..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            //   GoogleSignInResult task = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
           /* Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    MyProgress.stop();

                    firebaseAuthWithGoogle(account.getIdToken());

                    idToken = account.getIdToken();
                    name = account.getDisplayName();
                    email = account.getEmail();

                }
            } catch (ApiException e) {
                MyProgress.stop();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }*/
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        try {
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String strEmailSocial = acct.getEmail();
                String name = acct.getDisplayName();
                String idToken = acct.getIdToken();
                Log.i("TAG", name);
                //sign out after getting email and name
                mGoogleApiClient.connect();
                mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        FirebaseAuth.getInstance().signOut();
                        if (mGoogleApiClient.isConnected() == true) {
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(null);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                });
                if (!strEmailSocial.isEmpty()) {
                    Log.i("TAG", strEmailSocial);
                    // idToken = acct.getIdToken();
                    firebaseAuthWithGoogle(idToken);

                }
            } else {
                Log.e("LOGIN", "Google Error" + result.getStatus());
                Toast.makeText(this, "Problem with you google account", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            Log.e("LOGIN", "Google Error" + e.getLocalizedMessage());
            Toast.makeText(this, "Problem with you google account", Toast.LENGTH_SHORT).show();
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final String iddToken) {
//        MyProgress.start(LoginActivity.this);
        AuthCredential credential = GoogleAuthProvider.getCredential(iddToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            signUpBy = "Google";
//                            MyProgress.stop();

                            Log.d("BTAG", "PARAM SOCIAL LOGIN..." + idAuth + " signup by :" + signUpBy + " Id Token " + idToken);
                            if (idAuth != null) {
                                //callSocialLoginApi();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginbookActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
//                        MyProgress.stop();
                    }
                });
    }
    // [END auth_with_google]


//    private void callSocialLoginApi() {
//
//        //   MyProgress.start(this);
//        Call<LoginDataResponse> call = RetrofitClient.getRetrofitClient().getSocialLogin(idAuth, signUpBy, idToken);
//        call.enqueue(new Callback<LoginDataResponse>() {
//            @Override
//            public void onResponse(Call<LoginDataResponse> call, Response<LoginDataResponse> response) {
//                Utils.hideKeyboard(LoginActivity.this);
//
//                MyProgress.stop();
//
//                if (response.body() != null) {
//
//                    if (response.body().getStatus().equals("true")) {
//                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        UserDetail userDetail = response.body().getData().getUserDetail();
//                        // save user data
//                        // save user data
//                        localData.setLoggedIn(true);
//                        localData.setSignIn(userDetail);
//                        localData.setCustomerId(String.valueOf(userDetail.getUserId()));
//                        Log.d("BTAG", "GET USER LOGIN DETAIL...." + userDetail.getName());
//                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                        finishAffinity();
//                    } else {
//
//                        //Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        callCustomDialog();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, "" + t.getMessage());
//                Toast.makeText(LoginActivity.this, "Invalid Mobile Number..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void callCustomDialog() {
        if (NetworkUtil.getConnectivityStatus(this))
            getStateList();
        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        final AlertDialog dialogBuilder = new AlertDialog.Builder(LoginbookActivity.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.social_signup_custom_alert, null);

        final EditText edtMobile = dialogView.findViewById(R.id.edt_mobile);
        spnState = dialogView.findViewById(R.id.spn_state);
        spnCity = dialogView.findViewById(R.id.spn_city);
        spnPincode = dialogView.findViewById(R.id.spn_pincode);

        Button submit = dialogView.findViewById(R.id.buttonSubmit);
        Button cancel = dialogView.findViewById(R.id.buttonCancel);

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mobile = edtMobile.getText().toString().trim();
//                // validateSocialSignUp();
//                // dialogBuilder.dismiss();
//                if (TextUtils.isEmpty(mobile)) {
//                    showToast("Please enter Mobile Number");
//                }
//                if (!isValidMobile(mobile) || mobile.length() != 10) {
//                    showToast("Please enter valid Mobile Number");
//                } else if (spnStateid.equals("Select State")) {
//                    showToast("Please Select State");
//                } else if (spnCityid.equals("Select City")) {
//                    showToast("Please Select City");
//                } else if (spnSelectedpin.equals("Select Pincode")) {
//                    showToast("Please Select Pincode");
//                } else {
//                    MyProgress.start(LoginActivity.this);
//                    Call<DataResponse> call = RetrofitClient.getRetrofitClient().getSocialSignUp(idAuth, signUpBy, name, email, mobile, spnStateid, spnCityid, spnPinid);
//                    call.enqueue(new Callback<DataResponse>() {
//                        @Override
//                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                            MyProgress.stop();
//                            if (response.body() != null) {
//                                if (response.body().getStatus().equals("true")) {
//                                    Intent intent = new Intent(LoginActivity.this, OTPVerifyActivity.class);
//                                    intent.putExtra(Constants.SOCIALMOBILE, mobile);
//                                    intent.putExtra(Constants.MOBILE, "");
//                                    startActivity(intent);
//                                    dialogBuilder.dismiss();
//                                    finish();
//                                }
//                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(LoginActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DataResponse> call, Throwable t) {
//                            MyProgress.stop();
//                            Log.d(TAG, "" + t.getMessage());
//                            Toast.makeText(LoginActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                }
//            }
//
//        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

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
                    listState.clear();
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
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spnStateid = parent.getItemAtPosition(position).toString();
                            if (position > 0) {
                                if (!spnStateid.equals("Select State")) {
                                    listCity.clear();
                                    spnStateid = String.valueOf(listState.get(position - 1).getStateId());
                                    getCityList(spnStateid);
                                } else {
                                    listCity.clear();
                                    //getCityList("1");
                                    spnCity.setAdapter(null);
                                }
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<StateListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCityList(String StateId) {
        //MyProgress.start(this);
        Call<CityDataResponse> call = RetrofitClient.getRetrofitClient().getDistrictList(StateId);
        call.enqueue(new Callback<CityDataResponse>() {
            @Override
            public void onResponse(Call<CityDataResponse> call, Response<CityDataResponse> response) {
                //MyProgress.stop();
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
                    spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spnCityid = parent.getItemAtPosition(position).toString();
                            if (position > 0) {
                                if (!spnCityid.equals("Select City")) {
                                    listPincode.clear();
                                    spnCityid = String.valueOf(listCity.get(position - 1).getCityId());
                                    getPincodeList(spnCityid);
                                } else {
                                    listPincode.clear();
                                    //getPincodeList("1");
                                    spnPincode.setAdapter(null);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                    spnPincode.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<CityDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPincodeList(String CityId) {
        //MyProgress.start(this);
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
                    services.add("Select Pincode");
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
                            if (position > 0) {
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
                    Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                    spnCity.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<PincodeListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, "" + t.getMessage());
                Toast.makeText(LoginbookActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (authStateListener != null) {
            FirebaseAuth.getInstance().signOut();
        }
        firebaseAuth.addAuthStateListener(authStateListener);

   /*     // Checking if the user is signed in (non-null) and update UI accordingly.
       FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            Toast.makeText(LoginActivity.this, "Currently Logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        MyProgress.start(LoginbookActivity.this);
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, UI will update with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            idToken = token.getToken();
                            name = user.getDisplayName();
                            email = user.getEmail();
                            signUpBy = "FaceBook";
                            MyProgress.stop();
                            Log.d("BTAG", "PARAM SOCIAL LOGIN..." + idAuth + " signup by :" + signUpBy + " Id Token " + idToken);
                            //callSocialLoginApi();

                            Log.d("BTAG", "LOGIN USER NAME...." + user.getDisplayName());
                            //Toast.makeText(LoginActivity.this, "Authentication Succeeded.", Toast.LENGTH_SHORT).show();
                        } else {
                            MyProgress.stop();
                            // If sign-in fails, a message will display to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
