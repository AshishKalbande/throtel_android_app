package com.throtel.grocery.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.installreferrer.BuildConfig;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.throtel.grocery.R;
import com.throtel.grocery.adapter.SubCategoryWiseListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.fragments.AboutUsFragment;
import com.throtel.grocery.fragments.B2BCategoryFragment;
import com.throtel.grocery.fragments.BaseFragment;
import com.throtel.grocery.fragments.CategoryFragment;
import com.throtel.grocery.fragments.CustomizedSubscriptionFragment;
import com.throtel.grocery.fragments.DailySubscriptionFragment;
import com.throtel.grocery.fragments.HomeFragment;
import com.throtel.grocery.fragments.OrdersFragment;
import com.throtel.grocery.fragments.PolicyFragment;
import com.throtel.grocery.fragments.SubscriptionFragment;
import com.throtel.grocery.fragments.WalletFragment;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.ProductList;
import com.throtel.grocery.models.SearchListDataResponse;
import com.throtel.grocery.models.SubProductList;
import com.throtel.grocery.models.ViewCartDataResponse;
import com.throtel.grocery.models.WalletDetailDataResponse;
import com.throtel.grocery.models.notifications.NotificationCountResponse;
import com.throtel.grocery.views.MyProgress;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.throtel.grocery.fragments.BaseFragment.BulkItemCount;
import static com.throtel.grocery.fragments.BaseFragment.RegularItemCount;
import static com.throtel.grocery.fragments.WalletFragment.WalletfinalAmnt;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        BaseFragment.OnBadgeCountChangedListener, SubCategoryWiseListAdapter.OnItemClickListener, WalletFragment.OnPaymentUpdatedListener,
        SubCategoryWiseListAdapter.StatusUpdateListener {
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static Fragment currentFragment;
    public static Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    public static ImageView imgLogo;
    public static String WPaymentId = "";
    int mNotifyItemCount = 0;
    androidx.appcompat.widget.SearchView search_product;
    private TextView textNotifyItemCountCart;
    private TextView tvNotificationCount;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView ivSearch;
    private FragmentTransaction fragmentTransaction;
    private TextView tvName, tvAddress, tvWalletAmnt;
    private RecyclerView recyclerViewProduct;
    private FrameLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callPermissions();
        initViews();
        localData.setBookLoggedIn(false);

        Fragment fragment = HomeFragment.newInstance();
        replaceFragment(fragment, "", HomeFragment.class.getSimpleName());

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        toolbar.setTitle("");
        // set up toolbar
        setSupportActionBar(toolbar);

        //Setup drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        ivSearch = findViewById(R.id.ivSearch);
        bottomNavigationView = findViewById(R.id.nav_bottom_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tv_name);
        tvAddress = headerView.findViewById(R.id.tv_address);
        tvWalletAmnt = headerView.findViewById(R.id.tv_wallet_amnt);
        tvName.setText("Hi  " + localData.getSignIn().getName());
        tvAddress.setText(localData.getSignIn().getPincodeNumber() + " , " + localData.getSignIn().getCityName() + " - " + localData.getSignIn().getStateName());


//        tvWalletAmnt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.closeDrawer(GravityCompat.START);
//                WalletFragment fragment = WalletFragment.newInstance(HomeActivity.this);
//                replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
//                //bottomNavigationView.getMenu().findItem(R.id.nav_wallet).setChecked(true);
//            }
//        });

        Log.d("BTAG", "CUSTOMER ID..." + localData.getCustomerId());
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

//        search_product = findViewById(R.id.search_product);
        imgLogo = findViewById(R.id.img_logo);
        recyclerViewProduct = findViewById(R.id.rv_product_list);
        contentLayout = findViewById(R.id.content_layout);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

      /*  search_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_product.setIconified(false);
            }
        });

        // listening to search query text change
        search_product.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HomeActivity.this, SearchProductActivity.class));
                recyclerViewProduct.setVisibility(View.VISIBLE);
                contentLayout.setVisibility(View.GONE);
            }
        });*/

        /*search_product.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerViewProduct.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });*/

       /* search_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                    recyclerViewProduct.setLayoutManager(layoutManager);
                    recyclerViewProduct.setAdapter(new SubCategoryWiseListAdapter(
                            HomeActivity.this, new ArrayList<ProductList>(), HomeActivity.this, HomeActivity.this));
                } else
                    getSearchProductList(newText);
                return false;
            }
        });*/
    }

    private void getSearchProductList(String searchData) {
        // MyProgress.start(SearchProductActivity.this);

        Call<SearchListDataResponse> call = RetrofitClient.getRetrofitClient().getSearchProductListData(
                localData.getCustomerId(), searchData, "0");

        call.enqueue(new Callback<SearchListDataResponse>() {
            @Override
            public void onResponse(Call<SearchListDataResponse> call, Response<SearchListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    setUpProductList(response.body().getData().getProductList());
                } else {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                    recyclerViewProduct.setLayoutManager(layoutManager);
                    recyclerViewProduct.setAdapter(new SubCategoryWiseListAdapter(
                            HomeActivity.this, new ArrayList<ProductList>(), HomeActivity.this, HomeActivity.this));
                }
            }

            @Override
            public void onFailure(Call<SearchListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("MainActivity", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });

    }

    private void setUpProductList(ArrayList<ProductList> productList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(layoutManager);
        recyclerViewProduct.setAdapter(new SubCategoryWiseListAdapter(this, productList, this, this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (localData.getWalletAmount() == null) {*/
      //  getWalletDetails();
        /*} else if (localData.getWalletAmount() != null) {
            tvWalletAmnt.setText("₹ " + localData.getWalletAmount());
        }*/

        getBadgeCount();
        getBadgeCountBulk();
        getNotificationCount();

    }

    public void replaceFragment(Fragment fragment, String name, String tag) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment, tag);
        fragmentTransaction.commit();

        toolbar.setTitle(name);

        currentFragment = fragment;
        if (tag.equalsIgnoreCase(HomeFragment.class.getSimpleName())) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
//            search_product.setIconified(true);
        }
    }


    @Override
    public void onBackPressed() {
        Log.d("BTAG", "CURRENT FRAG..." + currentFragment.getTag());
       if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment.getTag().equals("HomeFragment")) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.finishAffinity(HomeActivity.this);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        } else {
            Fragment fragment = HomeFragment.newInstance();
            replaceFragment(fragment, "", HomeFragment.class.getSimpleName());
            //bottomNavigationView.setSelectedItemId(R.id.nav_home);
            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        String title = String.valueOf(item.getTitle());
        Fragment fragment;

        if (title.equalsIgnoreCase(getString(R.string.nav_home))) {
            fragment = HomeFragment.newInstance();
            replaceFragment(fragment, "", HomeFragment.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.nav_orders))) {
            fragment = OrdersFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_orders), OrdersFragment.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.nav_category))) {
            fragment = CategoryFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_category), CategoryFragment.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.nav_wallet))) {
            fragment = WalletFragment.newInstance(this);
            replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
            //bottomNavigationView.getMenu().findItem(R.id.nav_wallet).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_subscribe))) {
            fragment = SubscriptionFragment.newInstance();
            replaceFragment(fragment, "Monthly Subscription", SubscriptionFragment.class.getSimpleName());
        }

        //drawer menu list
        else if (title.equalsIgnoreCase("")) {
            fragment = CategoryFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_category), CategoryFragment.class.getSimpleName());
            bottomNavigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_account))) {
            startActivityForResult(new Intent(HomeActivity.this, MyAccountProfileActivity.class), 100);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_subscription))) {
            startActivity(new Intent(HomeActivity.this, MySubscriptionsTabsActivity.class));
        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_return))) {
            startActivity(new Intent(HomeActivity.this, MyReturnOrderListActivity.class));
        } else if (title.equalsIgnoreCase(getString(R.string.nav_cusomized_subscription))) {
            fragment = CustomizedSubscriptionFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_cusomized_subscription), CustomizedSubscriptionFragment.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.nav_daily_subscription))) {
            fragment = DailySubscriptionFragment.newInstance();
            replaceFragment(fragment, "Daily Subscriptions", DailySubscriptionFragment.class.getSimpleName());
           // bottomNavigationView.getMenu().findItem(R.id.nav_subscribe).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_orders))) {
            fragment = OrdersFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_my_orders), OrdersFragment.class.getSimpleName());
            bottomNavigationView.getMenu().findItem(R.id.nav_orders).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_b2b_purchase))) {
            fragment = B2BCategoryFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_b2b_purchase), B2BCategoryFragment.class.getSimpleName());
            bottomNavigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_refer_earn))) {
            showToast("Comming Soon...");
        } else if (title.equalsIgnoreCase(getString(R.string.nav_about_us))) {
            fragment = AboutUsFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_about_us), AboutUsFragment.class.getSimpleName());
            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_policy))) {
            fragment = PolicyFragment.newInstance();
            replaceFragment(fragment, getString(R.string.nav_policy), PolicyFragment.class.getSimpleName());
            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        } else if (title.equalsIgnoreCase(getString(R.string.nav_need_help))) {
            Intent intent = new Intent(HomeActivity.this, HelpDeskActivity.class);
            intent.putExtra("DATA", "Need Help");
            startActivity(intent);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_share))) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out Our app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (title.equalsIgnoreCase(getString(R.string.nav_logout))) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            localData.logout();
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();

                            startActivity(new Intent(HomeActivity.this, Choosemodule.class));
                            ActivityCompat.finishAffinity(HomeActivity.this);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 1 && data != null) {
                String value = data.getStringExtra("DATA");
                if (value != null && value.equalsIgnoreCase("Orders")) {
                    Fragment fragment = OrdersFragment.newInstance();
                    replaceFragment(fragment, getString(R.string.nav_my_orders), OrdersFragment.class.getSimpleName());
                    bottomNavigationView.getMenu().findItem(R.id.nav_orders).setChecked(true);
                } else if (value != null && value.equalsIgnoreCase("Wallet")) {
                    Fragment fragment = WalletFragment.newInstance(this);
                    replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
                   // bottomNavigationView.getMenu().findItem(R.id.nav_wallet).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textNotifyItemCountCart = actionView.findViewById(R.id.icon_badge);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        final MenuItem menuItem1 = menu.findItem(R.id.action_notification);
        View actionView1 = menuItem1.getActionView();
        tvNotificationCount = actionView1.findViewById(R.id.icon_badge_notification);
        actionView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });

        return true;
    }

    private void setupBadge() {

        mNotifyItemCount = RegularItemCount ;
       // mNotifyItemCount = RegularItemCount + BulkItemCount;
        if (textNotifyItemCountCart != null) {
            if (mNotifyItemCount == 0) {
                textNotifyItemCountCart.setVisibility(View.GONE);
            } else {
                textNotifyItemCountCart.setText(String.valueOf(Math.min(mNotifyItemCount, 99)));
                textNotifyItemCountCart.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUpNotificationCount(int count) {
        if (tvNotificationCount != null) {
            if (count == 0) {
                tvNotificationCount.setVisibility(View.GONE);
            } else {
                tvNotificationCount.setText(String.valueOf(Math.min(count, 99)));
                tvNotificationCount.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart:
                startActivity(new Intent(HomeActivity.this, ViewCartTabsActivity.class));
                return true;

            case R.id.action_notification:
                startActivity(new Intent(HomeActivity.this, NotificationListActivity.class));
                return true;
            case R.id.action_search:
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

//    @Override
//    public void onPaymentSuccess(String razorpayPaymentID) {
//        // After successful payment Razorpay send back a unique id
//        //Toast.makeText(this, "Transaction Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();
//        getCheckPayment(razorpayPaymentID);
//    }


//    @Override
//    public void onPaymentError(int i, String error) {
//        // Error message
//        Toast.makeText(this, "Transaction unsuccessful Plz Try Again Later... " + error, Toast.LENGTH_LONG).show();
//
//    }

    //Add Payment to walllet.....
//    public void getCheckPayment(String paymentID) {
//        MyProgress.start(HomeActivity.this);
//        Log.d("BTAG", "PARAM ORDER PAYMENT..." + localData.getCustomerId() + WalletfinalAmnt +
//                paymentID + "UPI" + "Success" + "For Daily Subscription");
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().AddWalletAmount(localData.getCustomerId(), String.valueOf(WalletfinalAmnt), paymentID,
//                "UPI", "Success", "For Daily Subscription");
//
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    showToast(response.body().getMessage());
//                    Fragment fragment = WalletFragment.newInstance(HomeActivity.this);
//                    replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
//
//                    Log.i("BDG", response.body().getMessage());
//                } else {
//                    showToast(getString(R.string.error_general));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(HomeActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    //get Wallet amount
//    private void getWalletDetails() {
//        Log.d("BTAG", "CALL WALLET METHOD...");
//        //MyProgress.start(HomeActivity.this);
//        Call<WalletDetailDataResponse> call = RetrofitClient.getRetrofitClient().getWalletDetailsData(localData.getCustomerId());
//        call.enqueue(new Callback<WalletDetailDataResponse>() {
//            @Override
//            public void onResponse(Call<WalletDetailDataResponse> call, Response<WalletDetailDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    localData.setWalletAmount(response.body().getData().getWalletDetail().getWalletAmount().toString());
//                    tvWalletAmnt.setText("₹ " + response.body().getData().getWalletDetail().getWalletAmount().toString());
//                } else {
//                    Toast.makeText(HomeActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WalletDetailDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(HomeActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void callPermissions() {
        int phonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermission = new ArrayList<>();

        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (phonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
        }
    }


    public void resetGraph(Context context) {
        Fragment fragment = HomeFragment.newInstance();
        replaceFragment(fragment, "", HomeFragment.class.getSimpleName());
        getBadgeCount();
        getBadgeCountBulk();
    }

    private void getBadgeCount() {
        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // setUpViewCartList(response.body().getData().getCartProductList());
                    ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
                    RegularItemCount = cartList.size();
                    setupBadge();

                } else {
                    //  showToast(response.body().getMessage());
                    RegularItemCount = 0;
                    setupBadge();
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

    private void getBadgeCountBulk() {
        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // setUpViewCartList(response.body().getData().getCartProductList());
                    ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
                   // BulkItemCount = cartList.size();
                    setupBadge();

                } else {
                    //  showToast(response.body().getMessage());
                    //BulkItemCount = 0;
                    setupBadge();
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

    private void getNotificationCount() {
        RetrofitClient.getRetrofitClient().getNotificationCount(
                localData.getCustomerId(), "Customer"
        ).enqueue(new Callback<NotificationCountResponse>() {
            @Override
            public void onResponse(Call<NotificationCountResponse> call, Response<NotificationCountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        setUpNotificationCount(response.body().getData().getNotificationDetail().getNotificationCount());
                    } else {
                        setUpNotificationCount(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

    @Override
    public void onBadgeCountChanged(int badCount) {
        setupBadge();
    }

    @Override
    public void onItemClick(ProductList product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductDetails", product);
        startActivity(intent);
    }

    @Override
    public void onStatusUpdate(int index, SubProductList product) {
        getViewCartList();
    }

    @Override
    public void onPaymentUpdated(double amount) {
        tvWalletAmnt.setText(String.valueOf(amount));
    }
}