package A_Test.Activity;

import static books.fragments.BaseFragment.RegularItemCount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;

import com.android.installreferrer.BuildConfig;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.throtel.grocery.R;
import com.throtel.grocery.activity.Choosemodule;

import java.util.ArrayList;
import java.util.List;

import A_Test.fragments.AppUpdateFragment;
import A_Test.fragments.BuyPackageTFragment;
import A_Test.fragments.HomeFragmentTest;
import A_Test.fragments.MainTestFragment;
import A_Test.fragments.MyTestFragment;
import A_Test.fragments.ProfileFragment;
import A_Test.fragments.SelfTestFragment;
import books.activity.BaseActivity;
import books.activity.HelpDeskActivity;
import books.activity.HomeBookActivity;
import books.activity.MyAccountProfileActivity;
import books.activity.MyReturnOrderListActivity;
import books.activity.MySubscriptionsTabsActivity;
import books.activity.NotificationListActivity;
import books.activity.ProductDetailsActivity;
import books.activity.SearchActivity;
import books.activity.ViewCartTabsActivity;
import books.adapter.SubCategoryWiseListAdapter;
import books.api.RetrofitClient;
import books.fragments.AboutUsFragment;
import books.fragments.B2BCategoryFragment;
import books.fragments.BaseFragment;
import books.fragments.CategoryFragment;
import books.fragments.CustomizedSubscriptionFragment;
import books.fragments.DailySubscriptionFragment;
import books.fragments.HomeFragment;
import books.fragments.OrdersFragment;
import books.fragments.PolicyFragment;
import books.fragments.SubscriptionFragment;
import books.fragments.WalletFragment;
import books.models.CartProductList;
import books.models.ProductList;
import books.models.ViewCartDataResponse;
import books.models.notifications.NotificationCountResponse;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeTestActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener{
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static Fragment currentFragment;
    public static Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    public static ImageView imgLogo;
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
        setContentView(R.layout.activity_home_test);

        localData.setLoggedIn(false);

        callPermissions();
        initViews();
        Fragment fragment = HomeFragmentTest.newInstance();
        replaceFragment(fragment, "", HomeFragmentTest.class.getSimpleName());

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
        tvName.setText("Hi  " + localData.getTestSignIn().getName());
//        tvAddress.setText(localData.getBookSignIn().getPincodeNumber() + " , " + localData.getTestSignIn().getCityName() + " - " + localData.getTestSignIn().getStateName());



        Log.d("BTAG", "CUSTOMER ID..." + localData.getCustomerId());
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

//        search_product = findViewById(R.id.search_product);
        imgLogo = findViewById(R.id.img_logo);
        recyclerViewProduct = findViewById(R.id.rv_product_list);
        contentLayout = findViewById(R.id.content_layout);

//        ivSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeTestActivity.this, books.activity.SearchActivity.class));
//            }
//        });


    }



//    public void replaceFragment(Fragment fragment, String name, String tag) {
//
//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.content_layout, fragment, tag);
//        fragmentTransaction.commit();
//
//        toolbar.setTitle(name);
//
//        currentFragment = fragment;
//        if (tag.equalsIgnoreCase(HomeFragmentTest.class.getSimpleName())) {
//            bottomNavigationView.setVisibility(View.VISIBLE);
//        } else {
////            search_product.setIconified(true);
//        }
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        Log.d("BTAG", "CURRENT FRAG..." + currentFragment.getTag());
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (currentFragment.getTag().equals("HomeFragment")) {
//            new AlertDialog.Builder(this)
//                    .setMessage("Are you sure you want to exit?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            ActivityCompat.finishAffinity(HomeTestActivity.this);
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    })
//                    .show();
//        } else {
//            Fragment fragment = HomeFragment.newInstance();
//            replaceFragment(fragment, "", HomeFragment.class.getSimpleName());
//            //bottomNavigationView.setSelectedItemId(R.id.nav_home);
//            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
//        }
//
//    }
//
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//        String title = String.valueOf(item.getTitle());
//        Fragment fragment;
//
//        if (title.equalsIgnoreCase(getString(R.string.nav_home))) {
//            fragment = HomeFragment.newInstance();
//            replaceFragment(fragment, "", HomeFragmentTest.class.getSimpleName());
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_home))) {
//            fragment = OrdersFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_orders), OrdersFragment.class.getSimpleName());
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_category))) {
//            fragment = CategoryFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_category), CategoryFragment.class.getSimpleName());
////        }
////        else if (title.equalsIgnoreCase(getString(R.string.nav_wallet))) {
////            fragment = WalletFragment.newInstance(this);
////            replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
//            //bottomNavigationView.getMenu().findItem(R.id.nav_wallet).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_subscribe))) {
//            fragment = SubscriptionFragment.newInstance();
//            replaceFragment(fragment, "Monthly Subscription", SubscriptionFragment.class.getSimpleName());
//        }
//
//        //drawer menu list
//        else if (title.equalsIgnoreCase("")) {
//            fragment = CategoryFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_category), CategoryFragment.class.getSimpleName());
//            bottomNavigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_account))) {
//            startActivityForResult(new Intent(HomeTestActivity.this, MyAccountProfileActivity.class), 100);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_subscription))) {
//            startActivity(new Intent(HomeTestActivity.this, MySubscriptionsTabsActivity.class));
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_return))) {
//            startActivity(new Intent(HomeTestActivity.this, MyReturnOrderListActivity.class));
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_cusomized_subscription))) {
//            fragment = CustomizedSubscriptionFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_cusomized_subscription), CustomizedSubscriptionFragment.class.getSimpleName());
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_daily_subscription))) {
//            fragment = DailySubscriptionFragment.newInstance();
//            replaceFragment(fragment, "Daily Subscriptions", DailySubscriptionFragment.class.getSimpleName());
//            // bottomNavigationView.getMenu().findItem(R.id.nav_subscribe).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_my_orders))) {
//            fragment = OrdersFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_my_orders), OrdersFragment.class.getSimpleName());
//            bottomNavigationView.getMenu().findItem(R.id.nav_orders).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_b2b_purchase))) {
//            fragment = B2BCategoryFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_b2b_purchase), B2BCategoryFragment.class.getSimpleName());
//            bottomNavigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_refer_earn))) {
//            showToast("Comming Soon...");
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_about_us))) {
//            fragment = AboutUsFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_about_us), AboutUsFragment.class.getSimpleName());
//            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_policy))) {
//            fragment = PolicyFragment.newInstance();
//            replaceFragment(fragment, getString(R.string.nav_policy), PolicyFragment.class.getSimpleName());
//            bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
//
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_need_help))) {
//            Intent intent = new Intent(HomeTestActivity.this, HelpDeskActivity.class);
//            intent.putExtra("DATA", "Need Help");
//            startActivity(intent);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_share))) {
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT,
//                    "Hey check out Our app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);
//        } else if (title.equalsIgnoreCase(getString(R.string.nav_logout))) {
//            new AlertDialog.Builder(this)
//                    .setMessage("Are you sure you want to Logout?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//                            localData.logout();
//                            FirebaseAuth.getInstance().signOut();
//                            LoginManager.getInstance().logOut();
//
//                            startActivity(new Intent(HomeTestActivity.this, Choosemodule.class));
//                            ActivityCompat.finishAffinity(HomeTestActivity.this);
//
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    })
//                    .show();
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100) {
//            if (resultCode == 1 && data != null) {
//                String value = data.getStringExtra("DATA");
//                if (value != null && value.equalsIgnoreCase("Orders")) {
//                    Fragment fragment = OrdersFragment.newInstance();
//                    replaceFragment(fragment, getString(R.string.nav_my_orders), OrdersFragment.class.getSimpleName());
//                    bottomNavigationView.getMenu().findItem(R.id.nav_orders).setChecked(true);
//                }
////                else if (value != null && value.equalsIgnoreCase("Wallet")) {
////                    Fragment fragment = WalletFragment.newInstance(this);
////                    replaceFragment(fragment, getString(R.string.nav_wallet), WalletFragment.class.getSimpleName());
//                    // bottomNavigationView.getMenu().findItem(R.id.nav_wallet).setChecked(true);
////                }
//            }
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//
//        View actionView = menuItem.getActionView();
//        textNotifyItemCountCart = actionView.findViewById(R.id.icon_badge);
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//
//        final MenuItem menuItem1 = menu.findItem(R.id.action_notification);
//        View actionView1 = menuItem1.getActionView();
//        tvNotificationCount = actionView1.findViewById(R.id.icon_badge_notification);
//        actionView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem1);
//            }
//        });
//
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.action_cart:
//                startActivity(new Intent(HomeTestActivity.this, ViewCartTabsActivity.class));
//                return true;
//
//            case R.id.nav_my_test:
//                startActivity(new Intent(HomeTestActivity.this, MyTestFragment.class));
//                return true;
//            case R.id.action_notification:
//                startActivity(new Intent(HomeTestActivity.this, ProfileFragment.class));
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
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

    public void replaceFragment(Fragment fragment, String name, String tag) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment, tag);
        fragmentTransaction.commit();

        toolbar.setTitle(name);

        currentFragment = fragment;
        if (tag.equalsIgnoreCase(HomeFragmentTest.class.getSimpleName())) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
//            search_product.setIconified(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = String.valueOf(item.getTitle());
        Fragment fragment;

        //Bottom nav
        if (title.equalsIgnoreCase(getString(R.string.my_package))) {
            fragment = HomeFragmentTest.newInstance();
            replaceFragment(fragment, "", HomeFragmentTest.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.my_test))) {
            fragment = MyTestFragment.newInstance();
            replaceFragment(fragment, getString(R.string.my_test), MyTestFragment.class.getSimpleName());
        } else if (title.equalsIgnoreCase(getString(R.string.profile))) {
            fragment = ProfileFragment.newInstance();
            replaceFragment(fragment, getString(R.string.profile), ProfileFragment.class.getSimpleName());
        }
        // Drwable navigation
        else if (title.equalsIgnoreCase(getString(R.string.my_pofile))) {
            fragment = ProfileFragment.newInstance();
            replaceFragment(fragment, getString(R.string.my_pofile), ProfileFragment.class.getSimpleName());
        }
//        else if (title.equalsIgnoreCase(getString(R.string.my_pofile))) {
//            startActivityForResult(new Intent(getApplicationContext(), MyAccountProfilesActivity.class), 100);
//        }
        else if (title.equalsIgnoreCase(getString(R.string.main_test))) {
            fragment = MainTestFragment.newInstance();
            replaceFragment(fragment, getString(R.string.main_test), MainTestFragment.class.getSimpleName());
        }else if (title.equalsIgnoreCase(getString(R.string.app_updates))) {
            fragment = AppUpdateFragment.newInstance();
            replaceFragment(fragment, getString(R.string.app_updates), AppUpdateFragment.class.getSimpleName());
        }else if (title.equalsIgnoreCase(getString(R.string.self_test))) {
            fragment = SelfTestFragment.newInstance();
            replaceFragment(fragment, getString(R.string.self_test), SelfTestFragment.class.getSimpleName());
        }else if (title.equalsIgnoreCase(getString(R.string.buy_package))) {
            fragment = BuyPackageTFragment.newInstance();
            replaceFragment(fragment, getString(R.string.buy_package), BuyPackageTFragment.class.getSimpleName());
        }else if (title.equalsIgnoreCase(getString(R.string.nav_logout))) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            localData.logout();
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();

                            startActivity(new Intent(HomeTestActivity.this, Choosemodule.class));
                            ActivityCompat.finishAffinity(HomeTestActivity.this);

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

//    public void resetGraph(Context context) {
//        Fragment fragment = HomeFragmentTest.newInstance();
//        replaceFragment(fragment, "", HomeFragment.class.getSimpleName());
////        getBadgeCountBulk();
//    }



}