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
import com.throtel.grocery.adapter.RegularViewCartListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.interfaces.OnCartItemClick;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.models.CategoryList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.ViewCartDataResponse;
import com.throtel.grocery.utils.LocalData;
import com.throtel.grocery.utils.NetworkUtil;
import com.throtel.grocery.views.MyProgress;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegularViewCartListFragment extends BaseFragment implements RegularViewCartListAdapter.StatusUpdateListener, OnCartItemClick {

    private static final String TAG = RegularViewCartListFragment.class.getSimpleName();


    private RecyclerView rvProductList;
    private CategoryList category;
    private TextView tvItemCount, tvTotalPrice, tvCheckoutPrice;
    public static int REQUEST_CODE = 102;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    private LinearLayout llCheckout;
    public Double TotalPrice;
    private ArrayList<CartProductList> arrListCart = new ArrayList();
    private RegularViewCartListAdapter cartAdapter;


    public RegularViewCartListFragment() {
        // Required empty public constructor
    }

    public static RegularViewCartListFragment newInstance() {

        RegularViewCartListFragment fragment = new RegularViewCartListFragment();
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

                RegularTotalAmount = TotalPrice;
                //OrderType = 0;
                // startActivity(new Intent(context, AddressListActivity.class));
                Intent ca = new Intent(context, AddressListActivity.class);
                ca.putExtra("changefor", "Checkout");
                startActivityForResult(ca, REQUEST_CODE);
            }
        });


        if (NetworkUtil.getConnectivityStatus(context))
            getViewCartList();

        else
            Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvProductList.setLayoutManager(layoutManager);
        cartAdapter = new RegularViewCartListAdapter(context, arrListCart, this, this);
        rvProductList.setAdapter(cartAdapter);

        return view;
    }

    //Product List
    private void getViewCartList() {
        // MyProgress.start(context);

        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    setUpViewCartList(response.body().getData().getCartProductList());

                } else {
                    showToast(response.body().getMessage());
                    tvItemCount.setText(" 0 Items in cart");
                    tvTotalPrice.setText("Total Price : ₹ " + 0.00);
                    llCheckout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setUpViewCartList(ArrayList<CartProductList> cartList) {
        if (cartList.size() > 0) {
            arrListCart.clear();
            arrListCart.addAll(cartList);
            cartAdapter.notifyDataSetChanged();
            llCheckout.setVisibility(View.VISIBLE);
        }
        setPrices();

    }

    private void setPrices() {
        tvItemCount.setText(arrListCart.size() + " Items in cart");
        RegularItemCount = arrListCart.size();
        TotalPrice = 0.0;
        for (CartProductList i : arrListCart) {
            TotalPrice += i.getProductSellingPrice() * i.getSelectedQuantity();
        }
        tvTotalPrice.setText("Total Price : ₹ " + new DecimalFormat("##.##").format(TotalPrice));
        tvCheckoutPrice.setText("₹ " + new DecimalFormat("##.##").format(TotalPrice));

    }

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


    @Override
    public void onItemClick(String type, int position) {
        if (type.equalsIgnoreCase("delete")) {
            DeleteCartProduct(String.valueOf(arrListCart.get(position).getCartId()), position);
        } else if (type.equalsIgnoreCase("qty")) {
            setPrices();
        }
    }

    //Delete Product from Cart
    private void DeleteCartProduct(String productCartId, int rowIndex) {
        MyProgress.start(context);
        LocalData localData = LocalData.getInstance(context);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                deleteCartProduct(productCartId, localData.getCustomerId());
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    arrListCart.remove(rowIndex);
                    cartAdapter.notifyItemRemoved(rowIndex);
                    if (arrListCart.size() > 0) llCheckout.setVisibility(View.VISIBLE);
                    else llCheckout.setVisibility(View.GONE);

                    setPrices();
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
