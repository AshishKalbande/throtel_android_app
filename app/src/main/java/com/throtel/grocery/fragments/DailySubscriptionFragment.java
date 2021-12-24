package com.throtel.grocery.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.DailySubscribeProductActivity;
import com.throtel.grocery.activity.HomeActivity;
import com.throtel.grocery.adapter.DailySubscriptionListAdapter;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.DailySubscriptionListDataResponse;
import com.throtel.grocery.models.ProductList;
import com.throtel.grocery.utils.Constants;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.throtel.grocery.activity.HomeActivity.imgLogo;


public class DailySubscriptionFragment extends BaseFragment implements DailySubscriptionListAdapter.OnItemClickListener {

    private RecyclerView rvDailySubscriptionList;
    GridLayoutManager gridLayoutManager;
    private ArrayList<ProductList> productList;
    public static int REQUEST_CODE = 102;
    NestedScrollView nestedScrollView;
  // int page=0, limit=0;
    private final int FIVE_SECONDS = 5000;
    DailySubscriptionListAdapter adapter;
    private int pageNumber = 0;
    private int count;


    public DailySubscriptionFragment() {
        // Required empty public constructor
    }


    public static DailySubscriptionFragment newInstance() {
        DailySubscriptionFragment fragment = new DailySubscriptionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_subscription, container, false);
        imgLogo.setVisibility(View.GONE);
        HomeActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        rvDailySubscriptionList = view.findViewById(R.id.rv_daily_list);
        productList = new ArrayList<>();
        adapter = new DailySubscriptionListAdapter(context, productList,this);
        gridLayoutManager = new GridLayoutManager(context,2);
       // gridLayoutManager.setStackFromEnd(true);
        rvDailySubscriptionList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        rvDailySubscriptionList.setAdapter(adapter);



        //getDailyList();
       // scheduleMessages();
        rvDailySubscriptionList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentFirstVisible = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                /*Log.d("BTAG","Current visible..."+currentFirstVisible);
                Log.d("BTAG","product list..."+productList.size());
                Log.d("BTAG","Count..."+count);*/
                if (currentFirstVisible == 0 && productList.size() < count) {
                    //getDailyList();
                }
            }
        });



        return view;
    }

    public void scheduleMessages() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //getDailyListAt0();          // this method will contain your almost-finished HTTP calls
                handler.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
    }

//    private void getDailyList() {
//       // if (pageNumber == 0)
//            MyProgress.start(context);
//        Call<DailySubscriptionListDataResponse> call = RetrofitClient.getRetrofitClient().getDailyList(localData.getCustomerId(), String.valueOf(0));
//        call.enqueue(new Callback<DailySubscriptionListDataResponse>() {
//            @Override
//            public void onResponse(Call<DailySubscriptionListDataResponse> call, Response<DailySubscriptionListDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    // limit=response.body().getPageCount();
//                    //Log.d("BTAG", "PAGE  IS...." + pageNumber);
//
//                    setUpDailyList(response.body().getData().getProductList());
//                   /* productList.addAll(0, response.body().getData().getProductList());
//                    adapter.notifyDataSetChanged();
//                    pageNumber++;
//                    count = response.body().getPageCount();*/
//                    //Log.d("BTAG", "PAGE ++  IS...." + pageNumber);
//
//                } else {
//                    Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DailySubscriptionListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void getDailyListAt0() {
//        if (pageNumber == 0)
//         MyProgress.start(context);
//        Call<DailySubscriptionListDataResponse> call = RetrofitClient.getRetrofitClient().getDailyList(localData.getCustomerId(), String.valueOf(0));
//        call.enqueue(new Callback<DailySubscriptionListDataResponse>() {
//            @Override
//            public void onResponse(Call<DailySubscriptionListDataResponse> call, Response<DailySubscriptionListDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                   // limit=response.body().getPageCount();
//                    //Log.d("BTAG", "PAGE NO IS...." + pageNumber);
//
//                    productList.clear();
//                    productList = new ArrayList<>();
//                    productList.addAll(response.body().getData().getProductList());
//                   /* adapter = new DailySubscriptionListAdapter(context, productList,this);
//                    gridLayoutManager = new GridLayoutManager(context,2);
//                    gridLayoutManager.setStackFromEnd(true);
//                    rvDailySubscriptionList.setLayoutManager(gridLayoutManager);
//                    rvDailySubscriptionList.setAdapter(adapter);*/
//                    setUpDailyList(productList);
//                    pageNumber = 0;
//                    pageNumber++;
//                    count = response.body().getPageCount();
//
//                } else {
//                    Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DailySubscriptionListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpDailyList(final ArrayList<ProductList> list) {

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        gridLayoutManager = new GridLayoutManager(context,2);
       // gridLayoutManager.setStackFromEnd(true);
        rvDailySubscriptionList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        rvDailySubscriptionList.setAdapter(new DailySubscriptionListAdapter(context, list, this));


    }



    @Override
    public void onItemClick(final ProductList product) {
        //open dialog for get qty
        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_input_qty, null);

        final EditText edtQty = (EditText) dialogView.findViewById(R.id.edt_qty);
        Button submit = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button cancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtQty.getText().toString())) {
                    showToast("Please enter Quantity");
                }
                else {

                    Intent intent = new Intent(context, DailySubscribeProductActivity.class);
                    intent.putExtra(Constants.DAILYQTY, edtQty.getText().toString());
                    intent.putExtra("ProductDetails", product);
                    intent.putExtra("SubproductId", 0);
                    startActivity(intent);
                    dialogBuilder.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();


    }

    //Product List
//    private void getSearchProductList(String searchData) {
//        //MyProgress.start(context);
//
//        Call<DailySubscriptionListDataResponse> call = RetrofitClient.getRetrofitClient().getSearchDailyListData(localData.getCustomerId(), String.valueOf(0),searchData);
//
//        call.enqueue(new Callback<DailySubscriptionListDataResponse>() {
//            @Override
//            public void onResponse(Call<DailySubscriptionListDataResponse> call, Response<DailySubscriptionListDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    MyProgress.stop();
//                    setUpDailyList(response.body().getData().getProductList());
//
//                } else {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DailySubscriptionListDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}

