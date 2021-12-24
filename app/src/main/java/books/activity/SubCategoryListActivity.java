package books.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
import books.adapter.SubCategoryListAdapter;
import books.adapter.SubCategoryWiseListAdapter;
import books.api.RetrofitClient;
import books.models.GroupList;
import books.models.ProductDetail;
import books.models.ProductList;
import books.models.SubCategoryDataResponse;
import books.models.SubCategoryList;
import books.models.SubCategoryWiseProductDataResponse;
import books.utils.NetworkUtil;
import books.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryListActivity extends BaseActivity implements SubCategoryListAdapter.OnItemClickListener, SubCategoryWiseListAdapter.OnItemClickListener,
        SubCategoryWiseListAdapter.StatusUpdateListener {
    private static final String TAG = SubCategoryListActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    NestedScrollView nestedScrollView, scroll2;
    ProgressBar progressBar, progressBar2;
    int page = 0, limit = 0;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private RecyclerView rvSUBCategoryList;
    private RecyclerView rvProductList;
    private GroupList category;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;
    private String strSubCatId = "";
    private ArrayList<ProductList> productList = new ArrayList<>();
    private ArrayList<ProductDetail> productDetails = new ArrayList<>();
    private SubCategoryWiseListAdapter adapterProducts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_list);

        if (getIntent() != null)
            category = (GroupList) getIntent().getSerializableExtra("GroupList");

        setUpToolbarBackButton(category.getMainGroupName());

        nestedScrollView = findViewById(R.id.scroll_view);
        scroll2 = findViewById(R.id.scroll_view2);
        progressBar = findViewById(R.id.progress_bar);
        rvSUBCategoryList = findViewById(R.id.rv_sub_category_list);

        progressBar2 = findViewById(R.id.progress_bar2);
        rvProductList = findViewById(R.id.rv_product_list);
        mLayoutManager = new LinearLayoutManager(SubCategoryListActivity.this);
        rvProductList.setLayoutManager(mLayoutManager);
        adapterProducts = new SubCategoryWiseListAdapter(this, productList, SubCategoryListActivity.this, this);
        rvProductList.setAdapter(adapterProducts);
        rvProductList.setNestedScrollingEnabled(false);
        if (NetworkUtil.getConnectivityStatus(SubCategoryListActivity.this)) {
            getSubCategoryList(page);
            getSubCategoryWiseProductList();
            getViewCartList();
        } else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

       /* nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //check condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //when reach last item position
                    //increase page size
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getSubCategoryList(page);
                }
            }
        });*/
        scroll2.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) scroll2.getChildAt(scroll2.getChildCount() - 1);

                int diff = (view.getBottom() - (scroll2.getHeight() + scroll2
                        .getScrollY()));

                if (diff == 0) {
                    // your pagination code
                    page++;
                    progressBar2.setVisibility(View.VISIBLE);
                    getMoreSubCategoryWiseProductList();
                }
            }
        });

      /*  rvProductList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data

                            loading = true;
                            page++;
                            progressBar2.setVisibility(View.VISIBLE);
                            getMoreSubCategoryWiseProductList();
                        }
                    }
                }
            }
        });*/
//toolbar search
        ImageView ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubCategoryListActivity.this, SearchActivity.class));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewCartList();
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
                    Toast.makeText(SubCategoryListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(SubCategoryListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
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
                SubCategoryListActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        rvSUBCategoryList.setLayoutManager(HorizontalLayout);

        rvSUBCategoryList.setAdapter(new SubCategoryListAdapter(SubCategoryListActivity.this, subCategoryList, this));


    }

    //Product List
    private void getSubCategoryWiseProductList() {
        //  MyProgress.start(SubCategoryListActivity.this);
        page = 0;
        limit = 0;
        Call<SubCategoryWiseProductDataResponse> call = RetrofitClient.getRetrofitClient().getSubCategoryWiseListData(limit, localData.getCustomerId(),String.valueOf(page) ,
                category.getMainGroupId().toString(),strSubCatId );

        call.enqueue(new Callback<SubCategoryWiseProductDataResponse>() {
            @Override
            public void onResponse(Call<SubCategoryWiseProductDataResponse> call, Response<SubCategoryWiseProductDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    progressBar2.setVisibility(View.GONE);
                    productList.clear();
                    productList.addAll(response.body().getData().getProductList());
                    Log.e("listsize",productList.size()+"");
                    adapterProducts.notifyDataSetChanged();
                } else {
                    MyProgress.stop();
                    progressBar2.setVisibility(View.GONE);
//                    Toast.makeText(SubCategoryListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryWiseProductDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(SubCategoryListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Product List
    private void getMoreSubCategoryWiseProductList() {
        //  MyProgress.start(SubCategoryListActivity.this);

        Call<SubCategoryWiseProductDataResponse> call = RetrofitClient.getRetrofitClient().getSubCategoryWiseListData(limit, localData.getCustomerId(),
                 String.valueOf(page), category.getMainGroupId().toString(),strSubCatId);

        call.enqueue(new Callback<SubCategoryWiseProductDataResponse>() {
            @Override
            public void onResponse(Call<SubCategoryWiseProductDataResponse> call, Response<SubCategoryWiseProductDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    progressBar2.setVisibility(View.GONE);
                    productList.addAll(response.body().getData().getProductList());
                    adapterProducts.notifyDataSetChanged();
                } else {
                    MyProgress.stop();
                    progressBar2.setVisibility(View.GONE);
//                    Toast.makeText(SubCategoryListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryWiseProductDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(SubCategoryListActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onItemClick(SubCategoryList category) {
        page = 0;
        if (category.getSubCategoryId() == 0) {
            strSubCatId = "";
            getSubCategoryWiseProductList();
        } else {
            strSubCatId = category.getSubCategoryId().toString();
            getSubCategoryWiseProductList();
        }
    }

    @Override
    public void onItemClick(ProductList product) {

    }

    public void resetGraph(Context context) {
        getViewCartList();
    }

    @Override
    public void onStatusUpdate(int index, ProductList product) {
        getViewCartList();
    }
}
