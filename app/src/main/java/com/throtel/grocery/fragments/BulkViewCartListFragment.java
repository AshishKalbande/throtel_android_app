package com.throtel.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.AddressListActivity;
import com.throtel.grocery.adapter.BulkViewCartListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.models.CategoryList;
import com.throtel.grocery.models.ViewCartDataResponse;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BulkViewCartListFragment extends BaseFragment implements BulkViewCartListAdapter.StatusUpdateListener {

    private static final String TAG = BulkViewCartListFragment.class.getSimpleName();
    public static int REQUEST_CODE = 102;
   // public Double BulkTotalPrice;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private RecyclerView rvProductList;
    private CategoryList category;
    private TextView tvItemCount, tvTotalPrice, tvCheckoutPrice;
    private LinearLayout llCheckout;


    public BulkViewCartListFragment() {
        // Required empty public constructor
    }

    public static BulkViewCartListFragment newInstance() {

        BulkViewCartListFragment fragment = new BulkViewCartListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_cart, container, false);

        rvProductList = view.findViewById(R.id.rv_product_list);
        tvItemCount = view.findViewById(R.id.tv_item_count);
        llCheckout = view.findViewById(R.id.ll_checkout);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        tvCheckoutPrice = view.findViewById(R.id.tv_total);

        llCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // BulkTotalAmount = BulkTotalPrice;
                //OrderType = 1;
                // startActivity(new Intent(context, AddressListActivity.class));
                Intent ca = new Intent(context, AddressListActivity.class);
                ca.putExtra("changefor", "Checkout");
                startActivityForResult(ca, REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (NetworkUtil.getConnectivityStatus(context))
                getViewCartList();
            else
                Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    //Product List
    private void getViewCartList() {
        //MyProgress.start(context);

//        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId(), "Bulk");
//
//        call.enqueue(new Callback<ViewCartDataResponse>() {
//            @Override
//            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpViewCartList(response.body().getData().getCartProductList());
//
//                } else {
//                    showToast(response.body().getMessage());
//                    tvItemCount.setText(" 0 Items in cart");
//                    tvTotalPrice.setText("Total Price : ₹ " + 0.00);
//                    llCheckout.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });

    }


//    private void setUpViewCartList(ArrayList<CartProductList> cartList) {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        rvProductList.setLayoutManager(layoutManager);
//        rvProductList.setAdapter(new BulkViewCartListAdapter(context, cartList, this));
//
//        tvItemCount.setText(cartList.size() + " Items in cart");
//        BulkItemCount = cartList.size();
//        BulkTotalPrice = 0.0;
//        for (CartProductList i : cartList) {
//           // BulkTotalPrice += i.getProductPricePerGramOrMl() * 1000 * i.getSelectedQuantity();
//        }
//        tvTotalPrice.setText("Total Price : ₹ " + BulkTotalPrice);
//        llCheckout.setVisibility(View.VISIBLE);
//        tvCheckoutPrice.setText("₹ " + BulkTotalPrice);
//    }

    @Override
    public void onStatusUpdate(int index, CartProductList product) {
        getViewCartList();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getViewCartList();

    }
}
