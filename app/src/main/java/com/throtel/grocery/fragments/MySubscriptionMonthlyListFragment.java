package com.throtel.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.MySubscriptionMonthlyOrderDetail;
import com.throtel.grocery.adapter.MySUbscriptionMonthlyListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.MySubscriptionMonthlyDataResponse;
import com.throtel.grocery.models.SubscriptionMonthlyOrderList;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySubscriptionMonthlyListFragment extends BaseFragment implements MySUbscriptionMonthlyListAdapter.OnItemClickListener {

    private static final String TAG = MySubscriptionMonthlyListFragment.class.getSimpleName();


    private RecyclerView rvMySubsriptionList;
    public static int REQUEST_CODE = 102;




    public MySubscriptionMonthlyListFragment() {
        // Required empty public constructor
    }

    public static MySubscriptionMonthlyListFragment newInstance() {

        MySubscriptionMonthlyListFragment fragment = new MySubscriptionMonthlyListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_subscriptions, container, false);

        rvMySubsriptionList = view.findViewById(R.id.rv_my_list);



//        if (NetworkUtil.getConnectivityStatus(context))
            //getMyMonthlyList();

//        else
//            Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


        return view;
    }

    //Product List
//    private void getMyMonthlyList() {
//       // MyProgress.start(context);
//        Call<MySubscriptionMonthlyDataResponse> call = RetrofitClient.getRetrofitClient().getMyMonthlyListData(localData.getCustomerId());
//        call.enqueue(new Callback<MySubscriptionMonthlyDataResponse>() {
//            @Override
//            public void onResponse(Call<MySubscriptionMonthlyDataResponse> call, Response<MySubscriptionMonthlyDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpMyList(response.body().getData().getSubscriptionOrderList());
//                } else {
//                    showToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MySubscriptionMonthlyDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpMyList(ArrayList<SubscriptionMonthlyOrderList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvMySubsriptionList.setLayoutManager(layoutManager);
        rvMySubsriptionList.setAdapter(new MySUbscriptionMonthlyListAdapter(context, list, this));
    }

    @Override
    public void onItemClick(SubscriptionMonthlyOrderList order) {
        Intent intent = new Intent(context, MySubscriptionMonthlyOrderDetail.class);
        intent.putExtra("SOrderList", order);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_CODE)
//            //getMyMonthlyList();

    }



}
