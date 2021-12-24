package com.throtel.grocery.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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

public class SearchProductActivity extends BaseActivity implements SearchListProductAdapter.OnItemClickListener {
    private static final String TAG = SearchProductActivity.class.getSimpleName();
    private RecyclerView rvSearchProductList;
    // private List<Contact> contactList;
    // private ContactsAdapter mAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        rvSearchProductList = findViewById(R.id.rv_product_list);
        // contactList = new ArrayList<>();
        //  mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
        // whiteNotificationBar(rvSearchProductList);


        //getSearchProductList("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        //String str = getIntent().getStringExtra("TITLE");
        //searchView.setQuery("", false);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // filter recycler view when query submitted
                // mAdapter.getFilter().filter(query);
                getSearchProductList(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //  mAdapter.getFilter().filter(query);
                getSearchProductList(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Toast.makeText(SearchProductActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpProductList(ArrayList<ProductList> productList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchProductActivity.this);
        rvSearchProductList.setLayoutManager(layoutManager);
        rvSearchProductList.setAdapter(new SearchListProductAdapter(this, productList, SearchProductActivity.this));
    }


    @Override
    public void onItemClick(ProductList product) {

    }

    public void resetGraph(Context context) {
        getSearchProductList("");
    }
}
