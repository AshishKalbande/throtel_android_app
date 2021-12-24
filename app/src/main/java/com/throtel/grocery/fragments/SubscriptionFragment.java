package com.throtel.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.HomeActivity;
import com.throtel.grocery.activity.PackDetailsActivity;
import com.throtel.grocery.adapter.PackListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.PackList;
import com.throtel.grocery.models.PackListDataResponse;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.throtel.grocery.activity.HomeActivity.imgLogo;

public class SubscriptionFragment extends BaseFragment implements PackListAdapter.OnItemClickListener {

    public static int REQUEST_CODE = 102;
    private RecyclerView rvPackList;
    private View view;


    public SubscriptionFragment() {
        // Required empty public constructor
    }


    public static SubscriptionFragment newInstance() {
        SubscriptionFragment fragment = new SubscriptionFragment();
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
        view = inflater.inflate(R.layout.fragment_subscription, container, false);
        imgLogo.setVisibility(View.GONE);
        HomeActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        rvPackList = view.findViewById(R.id.rv_pack_list);


//        if (NetworkUtil.getConnectivityStatus(context))
//            //getPackList();
//        else
//            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewCartList(view);
    }

//    private void getPackList() {
//        MyProgress.start(context);
//        Call<PackListDataResponse> call = RetrofitClient.getRetrofitClient().getPickListData(String.valueOf(localData.getCustomerId()));
//        call.enqueue(new Callback<PackListDataResponse>() {
//            @Override
//            public void onResponse(Call<PackListDataResponse> call, Response<PackListDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpPackList(response.body().getData().getPackList());
//                } else {
//                    showToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PackListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpPackList(ArrayList<PackList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvPackList.setLayoutManager(layoutManager);
        rvPackList.setAdapter(new PackListAdapter(context, list, this));
    }


    @Override
    public void onItemClick(PackList pack) {

        Intent intent = new Intent(context, PackDetailsActivity.class);
        intent.putExtra("PackList", pack);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
