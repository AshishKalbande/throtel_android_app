package books.fragments;

import static books.activity.HomeBookActivity.imgLogo;

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

import java.util.ArrayList;

import books.activity.HomeBookActivity;
import books.activity.OrderDetailsActivity;
import books.adapter.OrdersListAdapter;
import books.api.RetrofitClient;
import books.models.OrderList;
import books.models.OrderListDataResponse;
import books.utils.NetworkUtil;
import books.views.MyProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends BaseFragment implements OrdersListAdapter.OnItemClickListener {

    public static int REQUEST_CODE = 102;
    private RecyclerView rvOrdersList;
    private View view;


    public OrdersFragment() {
        // Required empty public constructor
    }


    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
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
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        imgLogo.setVisibility(View.GONE);
        // HomeActivity.llSearchView.setVisibility(View.GONE);
        HomeBookActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        rvOrdersList = view.findViewById(R.id.rv_orders_list);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtil.getConnectivityStatus(context)) {
            getViewCartList(view);
            getOrdersList();
        } else
            Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
    }

    private void getOrdersList() {
        MyProgress.start(context);
        Call<OrderListDataResponse> call = RetrofitClient.getRetrofitClient().getOrdersListData(String.valueOf(localData.getCustomerId()));
        call.enqueue(new Callback<OrderListDataResponse>() {
            @Override
            public void onResponse(Call<OrderListDataResponse> call, Response<OrderListDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        setUpOrdersList(response.body().getData().getOrderList());
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else
                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<OrderListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpOrdersList(ArrayList<OrderList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvOrdersList.setLayoutManager(layoutManager);
        rvOrdersList.setAdapter(new OrdersListAdapter(context, list, this));
    }


    @Override
    public void onItemClick(OrderList order) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("OrderList", order);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
