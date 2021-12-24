package books.fragments;

import static books.activity.HomeBookActivity.imgLogo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.activity.B2BGroupWiseCategoryActivity;
import books.activity.HomeBookActivity;
import books.adapter.B2BCategoryListAdapter;
import books.api.RetrofitClient;
import books.models.GroupList;
import books.models.GroupListDataResponse;
import books.utils.NetworkUtil;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B2BCategoryFragment extends BaseFragment implements B2BCategoryListAdapter.OnItemClickListener {

    public static int REQUEST_CODE = 102;
    private RecyclerView rvCategoryList;
    private View view;


    public B2BCategoryFragment() {
        // Required empty public constructor
    }


    public static B2BCategoryFragment newInstance() {
        B2BCategoryFragment fragment = new B2BCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        HomeBookActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        imgLogo.setVisibility(View.GONE);
        // HomeActivity.llSearchView.setVisibility(View.GONE);
        rvCategoryList = view.findViewById(R.id.rv_category_list);


        if (NetworkUtil.getConnectivityStatus(context))
            getCategoryList();
        else
            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewCartList(view);
    }

    private void getCategoryList() {
        MyProgress.start(context);
        Call<GroupListDataResponse> call = RetrofitClient.getRetrofitClient().getGroupListData(String.valueOf(localData.getCustomerId()));
        call.enqueue(new Callback<GroupListDataResponse>() {
            @Override
            public void onResponse(Call<GroupListDataResponse> call, Response<GroupListDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    setUpCategoryList(response.body().getData().getGroupList());
                } else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GroupListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCategoryList(ArrayList<GroupList> list) {

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rvCategoryList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        rvCategoryList.setAdapter(new B2BCategoryListAdapter(context, list, this));
    }

    @Override
    public void onItemClick(GroupList groupList) {
        Intent intent = new Intent(context, B2BGroupWiseCategoryActivity.class);
        intent.putExtra("GroupList", groupList);
        startActivityForResult(intent, REQUEST_CODE);
    }


}