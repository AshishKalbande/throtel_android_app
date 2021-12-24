package com.throtel.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.HomeActivity;
import com.throtel.grocery.activity.MonthlySubscribePackActivity;
import com.throtel.grocery.adapter.CustomizedProductListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CMSProductListDataResponse;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.throtel.grocery.activity.HomeActivity.imgLogo;

public class CustomizedSubscriptionFragment extends BaseFragment implements CustomizedProductListAdapter.StatusUpdateListener{

    private RecyclerView rvCMSProductList;
    public static int REQUEST_CODE = 102;
    private LinearLayout llSubscribeView;
    public  Double TotalPrice=0.0;
    public Double TotalNetPrice=0.0;


    public CustomizedSubscriptionFragment() {
        // Required empty public constructor
    }


    public static CustomizedSubscriptionFragment newInstance() {
        CustomizedSubscriptionFragment fragment = new CustomizedSubscriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_cusomized_subscription, container, false);
        imgLogo.setVisibility(View.GONE);
       // HomeActivity.llSearchView.setVisibility(View.VISIBLE);
        HomeActivity.bottomNavigationView.setVisibility(View.GONE);
        rvCMSProductList = view.findViewById(R.id.rv_cms_product_list);


//        if (NetworkUtil.getConnectivityStatus(context))
//            getCMSProductList();
//        else
//            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        llSubscribeView = view.findViewById(R.id.ll_subscribe);

        llSubscribeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ca=new Intent(context, MonthlySubscribePackActivity.class);
                ca.putExtra("total",TotalPrice);
                ca.putExtra("totalnet",TotalNetPrice);
                ca.putExtra("type","Custom Monthly Subscription");
               // ca.putExtra("packid","");
                ca.putExtra("packid","");

                startActivityForResult(ca,REQUEST_CODE);

            }
        });


        return view;
    }

//    private void getCMSProductList() {
//        MyProgress.start(context);
//        Call<CMSProductListDataResponse> call = RetrofitClient.getRetrofitClient().getCMSProductList(localData.getCustomerId());
//        call.enqueue(new Callback<CMSProductListDataResponse>() {
//            @Override
//            public void onResponse(Call<CMSProductListDataResponse> call, Response<CMSProductListDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpCMSProductList(response.body().getData().getCartProductList());
//                    llSubscribeView.setVisibility(View.VISIBLE);
//                } else {
//                    Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CMSProductListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpCMSProductList(ArrayList<CartProductList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvCMSProductList.setLayoutManager(layoutManager);
        rvCMSProductList.setAdapter(new CustomizedProductListAdapter(context, list, this));

        TotalPrice =0.0;
        for (CartProductList i : list) {
            TotalPrice += i.getProductSellingPrice()*i.getSelectedQuantity();
            TotalNetPrice+=i.getProductNetPrice()*i.getSelectedQuantity();
        }
        Log.d("BTAG","Total Price : ₹ " + TotalPrice);
        Log.d("BTAG","Total SELL : ₹ " + TotalNetPrice);

    }



    @Override
    public void onStatusUpdate(int index, CartProductList packProduct) {
        //getCMSProductList();
    }
}
