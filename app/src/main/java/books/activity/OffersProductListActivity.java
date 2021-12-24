package books.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.activity.BaseActivity;
import books.activity.SearchActivity;
import books.adapter.SubCategoryWiseListAdapter;
import books.models.CategoryOfferList;
import books.models.GlobalOfferList;
import books.models.ProductDetail;
import books.models.ProductList;

import java.util.ArrayList;

public class OffersProductListActivity extends BaseActivity implements SubCategoryWiseListAdapter.OnItemClickListener,
        SubCategoryWiseListAdapter.StatusUpdateListener {
    private static final String TAG = OffersProductListActivity.class.getSimpleName();
    private RecyclerView rvProductList;
    private ImageView ivSearch;
    private GlobalOfferList globalOfferList;
    private CategoryOfferList categoryOfferList;
    private String type = "Global";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_product_list);

        type = getIntent().getStringExtra("TYPE");

        if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("Global")) {
            globalOfferList = (GlobalOfferList) getIntent().getSerializableExtra("DATA");
            setUpToolbarBackButton(globalOfferList.getOfferName());
        } else {
            categoryOfferList = (CategoryOfferList) getIntent().getSerializableExtra("DATA");
            setUpToolbarBackButton(categoryOfferList.getOfferName());
        }

        rvProductList = findViewById(R.id.rv_product_list);
        ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);

        //getData();
        getViewCartList();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OffersProductListActivity.this, SearchActivity.class));

            }
        });

    }

//    private void getData() {
//        Call<OffersProductListResponse> call;
//        if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("Global")) {
//            call = RetrofitClient.getRetrofitClient().getOffersProductList(
//                    localData.getCustomerId(), globalOfferList.getOfferType(), globalOfferList.getOfferBy(),
//                    "",
//                    String.valueOf(globalOfferList.getOfferAmountOrPercentage()),
//                    globalOfferList.getOfferLimit()
//            );
//        } else {
//            call = RetrofitClient.getRetrofitClient().getOffersProductList(
//                    localData.getCustomerId(), categoryOfferList.getOfferType(), categoryOfferList.getOfferBy(),
//                    String.valueOf(categoryOfferList.getMainGroupId()),
//                    String.valueOf(categoryOfferList.getOfferAmountOrPercentage()),
//                    categoryOfferList.getOfferLimit()
//            );
//        }
//
//        MyProgress.start(this);
//        call.enqueue(new Callback<OffersProductListResponse>() {
//            @Override
//            public void onResponse(Call<OffersProductListResponse> call, Response<OffersProductListResponse> response) {
//                MyProgress.stop();
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        setUpProductList(response.body().getData().getProductList());
//                    } else {
//                        showToast(response.body().getMessage());
//                    }
//                } else {
//                    showToast("Something went wrong");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OffersProductListResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                showToast("Something went wrong");
//            }
//        });
//    }

    private void setUpProductList(ArrayList<ProductList> productList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProductList.setLayoutManager(layoutManager);
        rvProductList.setAdapter(new SubCategoryWiseListAdapter(this, productList, this, this));
    }

    @Override
    public void onStatusUpdate(int index, ProductList product) {
        getViewCartList();
    }

    @Override
    public void onItemClick(ProductList product) {

    }
}
