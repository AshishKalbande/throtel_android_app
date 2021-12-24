package com.throtel.grocery.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.GroupWiseListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CategoryList;
import com.throtel.grocery.models.GroupList;
import com.throtel.grocery.models.GroupWiseDataResponse;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupWiseCategoryActivity extends BaseActivity implements GroupWiseListAdapter.OnItemClickListener {
    private static final String TAG = GroupWiseCategoryActivity.class.getSimpleName();

    private RecyclerView rvCategoryList;
    private GroupList groupList;
    public static int REQUEST_CODE = 102;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_wise_category);


        if (getIntent() != null)
            groupList = (GroupList) getIntent().getSerializableExtra("GroupList");

        setUpToolbarBackButton(groupList.getMainGroupName());

        rvCategoryList = findViewById(R.id.rv_category_list);

        //toolbar search
        ImageView ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupWiseCategoryActivity.this, SearchActivity.class));

            }
        });


        if (NetworkUtil.getConnectivityStatus(GroupWiseCategoryActivity.this))
            getGroupWiseCategoryList();
        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewCartList();
    }

    private void getGroupWiseCategoryList() {
        MyProgress.start(GroupWiseCategoryActivity.this);
        Log.d("BTAG", "MAIN GROUP ID..." + groupList.getMainGroupId());
        Call<GroupWiseDataResponse> call = RetrofitClient.getRetrofitClient().getGroupWiseListData(localData.getCustomerId(), groupList.getMainGroupId().toString());
        call.enqueue(new Callback<GroupWiseDataResponse>() {
            @Override
            public void onResponse(Call<GroupWiseDataResponse> call, Response<GroupWiseDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    setUpCategoryList(response.body().getData().getCategoryList());
                } else {
                    Toast.makeText(GroupWiseCategoryActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupWiseDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(GroupWiseCategoryActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCategoryList(ArrayList<CategoryList> list) {

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GroupWiseCategoryActivity.this, 3);
        rvCategoryList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        rvCategoryList.setAdapter(new GroupWiseListAdapter(GroupWiseCategoryActivity.this, list, this));
    }

    @Override
    public void onItemClick(CategoryList category) {
        Intent intent = new Intent(GroupWiseCategoryActivity.this, SubCategoryListActivity.class);
        intent.putExtra("CategoryList", category);
        startActivityForResult(intent, REQUEST_CODE);
    }


}
