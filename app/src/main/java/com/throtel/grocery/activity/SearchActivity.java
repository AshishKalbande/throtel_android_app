package com.throtel.grocery.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.SearchListProductAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.ProductList;
import com.throtel.grocery.models.SearchListDataResponse;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements SearchListProductAdapter.OnItemClickListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView rvSearchProductList;
    private ImageView ivClear;
    private TextView txtCancel;
    private EditText edtSearch;
    private SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rvSearchProductList = findViewById(R.id.rv_product_list);
        edtSearch = findViewById(R.id.edtSearch);
        ivClear = findViewById(R.id.ivClear);
        txtCancel = findViewById(R.id.txtCancel);
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                rvSearchProductList.setVisibility(View.GONE);
                ivClear.setVisibility(View.GONE);
                txtCancel.setVisibility(View.VISIBLE);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    txtCancel.setVisibility(View.GONE);
                    getSearchProductList(s.toString().trim());
                } else {
                    rvSearchProductList.setVisibility(View.GONE);
                    txtCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    //Product List
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
                    //Toast.makeText(SearchProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(SearchActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpProductList(ArrayList<ProductList> productList) {
        rvSearchProductList.setVisibility(View.VISIBLE);
        ivClear.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        rvSearchProductList.setLayoutManager(layoutManager);
        rvSearchProductList.setAdapter(new SearchListProductAdapter(this, productList, SearchActivity.this));
    }


    @Override
    public void onItemClick(ProductList product) {

    }

    public void resetGraph(Context context) {
        getSearchProductList("");
    }
}