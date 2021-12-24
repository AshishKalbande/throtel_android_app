package com.throtel.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.PackDetailsAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.PackDetailsListDataResponse;
import com.throtel.grocery.models.PackList;
import com.throtel.grocery.models.PackProductList;
import com.throtel.grocery.views.MyProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackDetailsActivity extends BaseActivity implements PackDetailsAdapter.OnItemClickListener {
    private static final String TAG = PackDetailsActivity.class.getSimpleName();


    private RecyclerView rvMonthlyPackList;

    public static int REQUEST_CODE = 102;
    private LinearLayout llSubscribeView;
    private TextView tvPackName;
    private ImageView ivSearch;
    ImageView ivPackImage;
    private PackList pack = null;
    public Double TotalPrice = 0.0;
    public Double TotalNetPrice = 0.0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_details);


        if (getIntent() != null)
            pack = (PackList) getIntent().getSerializableExtra("PackList");
        setUpToolbarBackButton("Pack Details");

        tvPackName = findViewById(R.id.tv_pack_name);
        ivSearch = findViewById(R.id.ivSearch);
        ivPackImage = findViewById(R.id.iv_pack);

        rvMonthlyPackList = findViewById(R.id.rv_monthly_pack_list);

        llSubscribeView = findViewById(R.id.ll_subscribe);

        llSubscribeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ca = new Intent(PackDetailsActivity.this, MonthlySubscribePackActivity.class);
                ca.putExtra("total", TotalPrice);
                ca.putExtra("totalnet", TotalNetPrice);
                ca.putExtra("type", "Monthly Subscription");
                // ca.putExtra("packid",pack.getPackId());
                ca.putExtra("packid", pack.getPackId().toString());
                startActivityForResult(ca, REQUEST_CODE);

            }
        });


        if (pack != null) {

            tvPackName.setText(pack.getPackName());
            String url = RetrofitClient.BASE_SUBSCRIPTION_PACK_IMAGE_URL + pack.getPackImage();

            Picasso.with(PackDetailsActivity.this)
                    .load(url) //Load the image
                    .fit()
                    .error(R.drawable.no_preview)
                    .into(ivPackImage);

            //getMonthlyPackList();

        }
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PackDetailsActivity.this, SearchActivity.class));
            }
        });


    }


    //Product List
//    private void getMonthlyPackList() {
//        MyProgress.start(PackDetailsActivity.this);
//
//        Call<PackDetailsListDataResponse> call = RetrofitClient.getRetrofitClient().getMonthlyPackList(localData.getCustomerId(), pack.getPackId().toString());
//
//        call.enqueue(new Callback<PackDetailsListDataResponse>() {
//            @Override
//            public void onResponse(Call<PackDetailsListDataResponse> call, Response<PackDetailsListDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//
//                        setpackProductList(response.body().getData().getPackProductList());
//                    } else {
//                        showToast(response.body().getMessage());
//                        llSubscribeView.setVisibility(View.GONE);
//                    }
//                } else
//                    Toast.makeText(PackDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<PackDetailsListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(PackDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    private void setpackProductList(ArrayList<PackProductList> packProduct) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(PackDetailsActivity.this);
        rvMonthlyPackList.setLayoutManager(layoutManager);
        rvMonthlyPackList.setAdapter(new PackDetailsAdapter(this, packProduct, PackDetailsActivity.this));

        TotalPrice = 0.0;
        for (PackProductList i : packProduct) {
            TotalPrice += i.getProductSellingPrice() * 1;
            TotalNetPrice += i.getProductNetPrice() * 1;
        }
        Log.d("BTAG", "Total Price : ₹ " + TotalPrice);
        Log.d("BTAG", "Total SELL : ₹ " + TotalNetPrice);


    }


    @Override
    public void onItemClick(PackProductList packProduct) {


    }
}
