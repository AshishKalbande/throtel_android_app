package books.fragments;

import static books.activity.HomeBookActivity.imgLogo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.throtel.grocery.R;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import books.activity.HomeBookActivity;
import books.adapter.OrdersListAdapter;
import books.adapter.WalletSummeryAdapter;
import books.models.OrderList;
import books.models.TransactionList;
import books.utils.NetworkUtil;

public class WalletFragment extends BaseFragment implements OrdersListAdapter.OnItemClickListener {
    private static final String TAG = WalletFragment.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    public static Double WalletfinalAmnt = 0.0;
    private static OnPaymentUpdatedListener onPaymentUpdatedListener;
    private RecyclerView rvSummeryList;
    private TextView tvWalletAmount, tv1Amnt, tv2Amnt, tv3Amnt, tv4Amnt;
    private Button btnAddAmount;
    private EditText edtAddAmount;
    private View view;
    String orderPaymentId;

    public WalletFragment() {
        // Required empty public constructor
    }


    public static WalletFragment newInstance(OnPaymentUpdatedListener onPaymentUpdatedListener) {
        WalletFragment fragment = new WalletFragment();
        WalletFragment.onPaymentUpdatedListener = onPaymentUpdatedListener;
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
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        Checkout.preload(getContext());
        WalletfinalAmnt = 0.0;
        imgLogo.setVisibility(View.GONE);
        // HomeActivity.llSearchView.setVisibility(View.GONE);
        HomeBookActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        rvSummeryList = view.findViewById(R.id.rv_summery_list);
        tvWalletAmount = view.findViewById(R.id.tv_wallet_amnt);
        edtAddAmount = view.findViewById(R.id.edt_add);
        tv1Amnt = view.findViewById(R.id.tv_1amnt);
        tv2Amnt = view.findViewById(R.id.tv_2amnt);
        tv3Amnt = view.findViewById(R.id.tv_3amnt);
        tv4Amnt = view.findViewById(R.id.tv_4amnt);

        btnAddAmount = view.findViewById(R.id.btn_add_amnt);
        final DecimalFormat numberFormat = new DecimalFormat("#.00");

        if (NetworkUtil.getConnectivityStatus(context)) {
            //getWalletDetails();
           // getTransactionList();

        }
        //         Toast.makeText(getContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        //===========Get Amount======
        tv1Amnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAddAmount.setText("500");
            }
        });
        tv2Amnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAddAmount.setText("1000");
            }
        });
        tv3Amnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAddAmount.setText("2000");
            }
        });
        tv4Amnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAddAmount.setText("3000");
            }
        });

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edtAddAmount.getText().toString())) {
                    showToast("Please enter Amount");
                } else {
                    WalletfinalAmnt = Double.valueOf(edtAddAmount.getText().toString());
                    numberFormat.format(WalletfinalAmnt);
                    //getOderPaymentId();
                    // startPayment();
                }
            }
        });
        return view;
    }

//    private void getOderPaymentId() {
//        Log.d("BTAG", "orderPaymentId");
//        MyProgress.start(context);
//        Call<OrderPaymentIdResponse> call = RetrofitClient.getRetrofitClient().getOrderPaymentId(WalletfinalAmnt.toString());
//        call.enqueue(new Callback<OrderPaymentIdResponse>() {
//            @Override
//            public void onResponse(Call<OrderPaymentIdResponse> call, Response<OrderPaymentIdResponse> response) {
//                MyProgress.stop();
//                if (response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        Log.d("BTAGG", "orderPaymentId");
//                        orderPaymentId = response.body().getData().getOrderPaymentId();
//                        startPayment();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderPaymentIdResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                //      Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        getViewCartList(view);
        //getWalletDetails();
    }

//    private void getWalletDetails() {
//        MyProgress.start(context);
//        Call<WalletDetailDataResponse> call = RetrofitClient.getRetrofitClient().getWalletDetailsData(localData.getCustomerId());
//        call.enqueue(new Callback<WalletDetailDataResponse>() {
//            @Override
//            public void onResponse(Call<WalletDetailDataResponse> call, Response<WalletDetailDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null) {
//
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        tvWalletAmount.setText("₹ " + response.body().getData().getWalletDetail().getWalletAmount().toString());
//                        WalletAmount = response.body().getData().getWalletDetail().getWalletAmount();
//                        localData.setWalletAmount(String.valueOf(response.body().getData().getWalletDetail().getWalletAmount()));
//                        onPaymentUpdatedListener.onPaymentUpdated(WalletAmount);
//                        Log.i("TAGS", "response.body().getMessage()");
//                    } else {
//                        showToast(response.body().getMessage());
//                    }
//                } else {
//                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WalletDetailDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                //   Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void getTransactionList() {
//        // MyProgress.start(context);
//        Call<WalletSummeryDataResponse> call = RetrofitClient.getRetrofitClient().getSummeryListData(String.valueOf(localData.getCustomerId()));
//        call.enqueue(new Callback<WalletSummeryDataResponse>() {
//            @Override
//            public void onResponse(Call<WalletSummeryDataResponse> call, Response<WalletSummeryDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        setUpSummerysList(response.body().getData().getTransactionList());
//                        Log.i("TAGS", response.body().getMessage());
//                    } else {
//                        showToast(response.body().getMessage());
//                    }
//                } else {
//                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WalletSummeryDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                //        Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpSummerysList(ArrayList<TransactionList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSummeryList.getContext(),
                layoutManager.getOrientation());
        rvSummeryList.addItemDecoration(dividerItemDecoration);
        rvSummeryList.setLayoutManager(layoutManager);
        rvSummeryList.setAdapter(new WalletSummeryAdapter(context, list));
    }


    @Override
    public void onItemClick(OrderList order) {

    }

    //Razor Pay api call
    public void startPayment() {

        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_BJhpLshlVxMi9A");
        checkout.setKeyID("rzp_live_0NoVzbVRnK4EUy");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.final_logo);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", localData.getSignIn().getName());
            options.put("description", "throtel Grocery");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", Math.round(OrderId));//from response of order placed api.
            options.put("order_id", orderPaymentId);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", WalletfinalAmnt * 100);//pass amount in currency subunits
            options.put("prefill.email", localData.getSignIn().getEmail());
            options.put("prefill.contact", localData.getSignIn().getPhone());
            checkout.open(getActivity(), options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public interface OnPaymentUpdatedListener {
        void onPaymentUpdated(double amount);
    }
}
