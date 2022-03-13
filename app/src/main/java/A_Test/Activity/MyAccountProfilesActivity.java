package A_Test.Activity;

import static books.activity.AddressListActivity.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.throtel.grocery.R;

import books.activity.AddressListActivity;
import books.activity.BaseActivity;
import books.activity.MyAccountProfileActivity;
import books.utils.NetworkUtil;

public class MyAccountProfilesActivity extends BaseActivity {
    CircularImageView ivProfile;
    private TextView tvName, tvMobile;
    private TextView tvCName, tvType, tvFlatNo, tvCMobile, tvSocietyName, tvLandmark, tvPincode;
    private RelativeLayout rlAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_profiles);

        setUpToolbarBackButton("Profile");

//        initView();

        if (NetworkUtil.getConnectivityStatus(MyAccountProfilesActivity.this)) {
//            getActiveAddress();
        }else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
//        setView();
    }

    private void initView() {
        ivProfile = findViewById(R.id.iv_profile);
        tvName = findViewById(R.id.tv_name);
        tvMobile = findViewById(R.id.tv_mobile);

        tvCName = findViewById(R.id.tv_cname);
        tvCMobile = findViewById(R.id.tv_cmobile);
        tvType = findViewById(R.id.tv_type);
        tvFlatNo = findViewById(R.id.tv_flat_no);
        tvSocietyName = findViewById(R.id.tv_society_name);
        tvLandmark = findViewById(R.id.tv_landmark);
        tvPincode = findViewById(R.id.tv_pincode);
        rlAddress = findViewById(R.id.address);


//        findViewById(R.id.tv_my_subscription).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MyAccountProfileActivity.this, MySubscriptionsTabsActivity.class));
//                finish();
//            }
//        });

        findViewById(R.id.tv_my_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("DATA", "Orders");
                setResult(1, intent);
                finish();
            }
        });
//        findViewById(R.id.tv_my_wallet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("DATA", "Wallet");
//                setResult(1, intent);
//                finish();
//            }
//        });
        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountProfilesActivity.this, AddressListActivity.class);
                intent.putExtra("changefor", "MyAccount");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    private void setView() {

//        String url = RetrofitClient.BASE_CUSTOMER_IMAGE_URL + localData.getSignIn().getProfileImage();
//
//        Picasso.with(MyAccountProfileActivity.this)
//                .load(url) //Load the image
//                .fit()
//                .error(R.drawable.ic_user)
//                .into(ivProfile);
        tvName.setText(localData.getTestSignIn().getName());
        tvMobile.setText(localData.getTestSignIn().getPhone());
        tvPincode.setText(localData.getTestSignIn().getTahsilName() + "-" + localData.getTestSignIn().getDistrictName());
    }

}