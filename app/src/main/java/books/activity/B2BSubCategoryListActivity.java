package books.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.activity.BaseActivity;
import books.activity.SearchActivity;
import books.adapter.B2BSubCategoryWiseListAdapter;
import books.adapter.SubCategoryListAdapter;
import books.api.RetrofitClient;
import books.models.GroupList;
import books.models.ProductList;
import books.models.SubCategoryDataResponse;
import books.models.SubCategoryList;
import books.models.SubCategoryWiseProductDataResponse;
import books.models.SubProductList;
import books.utils.NetworkUtil;
import books.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B2BSubCategoryListActivity extends BaseActivity implements SubCategoryListAdapter.OnItemClickListener, B2BSubCategoryWiseListAdapter.StatusUpdateListener {
    private static final String TAG = B2BSubCategoryListActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    NestedScrollView nestedScrollView, nestedScrollView2;
    ProgressBar progressBar, progressBar2;
    int page = 0, limit = 3;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private RecyclerView rvSUBCategoryList;
    private RecyclerView rvProductList;
    private GroupList category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsub_category_list);


        if (getIntent() != null)
            category = (GroupList) getIntent().getSerializableExtra("CategoryList");

        setUpToolbarBackButton(category.getMainGroupName());

        nestedScrollView = findViewById(R.id.scroll_view);
        progressBar = findViewById(R.id.progress_bar);
        rvSUBCategoryList = findViewById(R.id.rv_sub_category_list);

        nestedScrollView2 = findViewById(R.id.scroll_view2);
        progressBar2 = findViewById(R.id.progress_bar2);
        rvProductList = findViewById(R.id.rv_product_list);

        if (NetworkUtil.getConnectivityStatus(B2BSubCategoryListActivity.this)) {
            getSubCategoryList(page);
            getSubCategoryWiseProductList("", page, limit);
            getViewCartList();
        } else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //check condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //when reach last item position
                    //increase page size
                    page++;
                    Log.d("BTAG", "PAGE INDEX IS...." + page);
                    progressBar.setVisibility(View.VISIBLE);
                    getSubCategoryList(page);


                }
            }
        });

        //toolbar search
        ImageView ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(B2BSubCategoryListActivity.this, SearchActivity.class));

            }
        });
    }

    private void getSubCategoryList(int pageNo) {
        // MyProgress.start(SubCategoryListActivity.this);

        Call<SubCategoryDataResponse> call = RetrofitClient.getRetrofitClient().getSubCategoryListData(localData.getCustomerId(), category.getMainGroupId().toString());

        call.enqueue(new Callback<SubCategoryDataResponse>() {
            @Override
            public void onResponse(Call<SubCategoryDataResponse> call, Response<SubCategoryDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    progressBar.setVisibility(View.GONE);
                    setUpSubCategoryList(response.body().getData().getSubCategoryList());

                } else {
                    MyProgress.stop();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(B2BSubCategoryListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(B2BSubCategoryListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSubCategoryList(ArrayList<SubCategoryList> subCategoryList) {

        SubCategoryList all = new SubCategoryList();
        all.setSubCategoryName("All");
        all.setSubCategoryId(0);
        subCategoryList.add(0, all);


        //======== Category List ==============
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                getApplicationContext());

        // Set LayoutManager on Recycler View
        rvSUBCategoryList.setLayoutManager(
                RecyclerViewLayoutManager);
        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout
                = new LinearLayoutManager(
                B2BSubCategoryListActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        rvSUBCategoryList.setLayoutManager(HorizontalLayout);

        rvSUBCategoryList.setAdapter(new SubCategoryListAdapter(B2BSubCategoryListActivity.this, subCategoryList, this));
    }

    //Product List
    private void getSubCategoryWiseProductList(String subCategoryId, int pageNo, int limit) {
        MyProgress.start(B2BSubCategoryListActivity.this);

        Call<SubCategoryWiseProductDataResponse> call = RetrofitClient.getRetrofitClient().getSubCategoryWiseListData(limit, localData.getCustomerId(), subCategoryId, String.valueOf(pageNo), category.getMainGroupId().toString());

        call.enqueue(new Callback<SubCategoryWiseProductDataResponse>() {
            @Override
            public void onResponse(Call<SubCategoryWiseProductDataResponse> call, Response<SubCategoryWiseProductDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    progressBar2.setVisibility(View.GONE);
                    setUpProductList(response.body().getData().getProductList());

                } else {
                    MyProgress.stop();
                    progressBar2.setVisibility(View.GONE);
                    Toast.makeText(B2BSubCategoryListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    setUpProductList(new ArrayList<ProductList>());
                }
            }

            @Override
            public void onFailure(Call<SubCategoryWiseProductDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(B2BSubCategoryListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpProductList(ArrayList<ProductList> productList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(B2BSubCategoryListActivity.this);
        rvProductList.setLayoutManager(layoutManager);
        rvProductList.setAdapter(new B2BSubCategoryWiseListAdapter(this, productList, B2BSubCategoryListActivity.this));
    }

    @Override
    public void onItemClick(SubCategoryList category) {
        page = 0;
        //Log.d("BTAG","SUB CATEGORY ID...."+category.getSubCategoryId().toString());
        if (category.getSubCategoryId() == 0)
        {
            getSubCategoryWiseProductList("", page, limit);
        } else {
            getSubCategoryWiseProductList(category.getSubCategoryId().toString(), page, limit);
        }
    }

    @Override
    public void onStatusUpdate(int index, SubProductList product) {
        getViewCartList();
    }


}
