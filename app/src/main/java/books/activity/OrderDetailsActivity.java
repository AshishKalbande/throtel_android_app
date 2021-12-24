package books.activity;

import static books.fragments.OrdersFragment.REQUEST_CODE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.activity.BaseActivity;
import books.activity.HelpDeskActivity;
import books.activity.OrderReturnsProductListActivity;
import books.activity.ViewAllOrderItemsActivity;
import books.adapter.CustomerOrdersListAdapter;
import books.api.RetrofitClient;
import books.models.CustomerOrderListDataResponse;
import books.models.CustomerProductList;
import books.models.OrderDetail;
import books.models.OrderDetailsDataResponse;
import books.models.OrderList;
import books.utils.Utils;
import books.views.MyProgress;
import com.vinay.stepview.VerticalStepView;
import com.vinay.stepview.models.Step;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends BaseActivity implements CustomerOrdersListAdapter.OnItemClickListener {
    private static final String TAG = OrderDetailsActivity.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    private TextView tvOrderNo, tvName, tvMobile, tvAddress, tvCityPincode, tvDownloadInvoice, tvViewAll;
    private TextView tvTotal, tvDiscount, tvCoupon, tvPointEarned;
    private Button btnHelp, btnCancelOrder, btnReturn;
    private RecyclerView rvOrderList;
    private TextView tvItemNo;
    private OrderList order = null;
    private OrderDetail orderDetail = null;
    private VerticalStepView mVerticalStepView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detailss);
        initViews();


        if (getIntent() != null)
            order = (OrderList) getIntent().getSerializableExtra("OrderList");
        setUpToolbarBackButton("Order Details");

        if (order != null) {
            getOrderDetails();

        }
    }

    private void initViews() {
        tvOrderNo = findViewById(R.id.tv_order_id);

        tvName = findViewById(R.id.tv_customer_name);
        tvMobile = findViewById(R.id.tv_mobile);
        tvAddress = findViewById(R.id.tv_address);
        tvCityPincode = findViewById(R.id.tv_city_pincode);

        tvTotal = findViewById(R.id.tv_net_price);
        tvDiscount = findViewById(R.id.tv_discount);
        tvCoupon = findViewById(R.id.tv_coupon);
        tvPointEarned = findViewById(R.id.tv_point_earned);

        rvOrderList = findViewById(R.id.rv_order_list);
        tvItemNo = findViewById(R.id.tv_item_no);

        btnHelp = findViewById(R.id.btn_help);
        //btnCancelOrder = findViewById(R.id.btn_cancel);
        btnReturn = findViewById(R.id.btn_return);

        mVerticalStepView = findViewById(R.id.vertical_step_view);

        tvViewAll = findViewById(R.id.tv_view_all);
        tvDownloadInvoice = findViewById(R.id.tv_download_invoice);

        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order != null) {
                    Intent intent = new Intent(OrderDetailsActivity.this, ViewAllOrderItemsActivity.class);
                    intent.putExtra("DATA", order);
                    startActivity(intent);
                }
            }
        });

        tvDownloadInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if (TextUtils.isEmpty("")) {
                    Toast.makeText(OrderDetailsActivity.this, "There is no file to download", Toast.LENGTH_SHORT).show();
                } else*/
                new AlertDialog.Builder(OrderDetailsActivity.this)
                        .setMessage("Are you sure you want to download ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String url = "https://www.throtelgrocery.com/grocery-customer-api/customer-invoice/" + orderDetail.getOrderId();
                                checkPermission(url, Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

    }

    public void checkPermission(String url, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(this, "Download Start", Toast.LENGTH_SHORT).show();
            Utils.downloadFile(this, url, url);
        }
    }

    private long isExpire(String date) {
        if (date.isEmpty() || date.trim().equals("")) {
            return 0;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy , hh:mm a"); // Jan-20-2015 1:30:55 PM
            Date d = null;
            Date d1 = null;
            String today = getToday("dd MMM yy , hh:mm a");

            try {
                d = sdf.parse(date);
                d1 = sdf.parse(today);
                long diff = d1.getTime() - d.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000);

                return diffHours;
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }


    }


    public static String getToday(String format) {
        Date date = new Date();
        return new SimpleDateFormat(format).format(date);
    }


    private void setData() {

//        if (orderDetail.getOrderStatus().equalsIgnoreCase("Cancel")) {
//            btnCancelOrder.setVisibility(View.GONE);
//            btnReturn.setVisibility(View.GONE);
//        } else
            if (orderDetail.getOrderStatus().equalsIgnoreCase("Complete")) {
            if (orderDetail.getOrderDeliverdDate() != null) {
                String orderDeliveredTime = convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString());
                if (isExpire(orderDeliveredTime) > 1) {
                    //btnCancelOrder.setVisibility(View.GONE);
                    btnReturn.setVisibility(View.GONE);
                } else {
                    //btnCancelOrder.setVisibility(View.GONE);
                    btnReturn.setVisibility(View.VISIBLE);
                }
            }
        } else {
//            btnCancelOrder.setVisibility(View.VISIBLE);
            btnReturn.setVisibility(View.GONE);
        }
        if (orderDetail.getOrderPacked().equalsIgnoreCase("Yes")) {
           // btnCancelOrder.setVisibility(View.GONE);
        }


        tvOrderNo.setText(order.getOrderCode());
        tvName.setText(orderDetail.getCustomerName());
        tvMobile.setText("Contact No : " + orderDetail.getCustomerPhone());
        tvAddress.setText(orderDetail.getCustomerAddress()+",");
        tvCityPincode.setText(localData.getBookSignIn().getCityName());

        Double discount = orderDetail.getTotalPriceAfterIncludingServiceCharge() - orderDetail.getNetPrice();
        String strDouble = String.format("%.2f", discount);
        tvTotal.setText("₹ " + orderDetail.getTotalPriceAfterIncludingServiceCharge());
//        tvDiscount.setText("₹ " + strDouble);
//        tvCoupon.setText("₹ " + orderDetail.getCoupanAmount());
        tvPointEarned.setText("0");

        //Steper for order status....
        mVerticalStepView.setReverse(false);
        List<Step> stepList = new ArrayList<>();
        //try {


        if (orderDetail.getOrderPlaced().contains("Yes") && orderDetail.getOrderPacked().contains("No") && orderDetail.getOrderDispatched().contains("No") && orderDetail.getOrderDelivered().contains("No")) {

            if (orderDetail.getOrderPlacedDate() != null)
                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Placed \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderPackedDate() != null)
                stepList.add(new Step("Order Packed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPackedDate().toString()), Step.State.CURRENT));
            else
                stepList.add(new Step("Order Packed  \n" + "", Step.State.CURRENT));
            if (orderDetail.getOrderDispatchedDate() != null)
                stepList.add(new Step("Order Dispatched \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDispatchedDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Dispatched \n" + "", Step.State.NOT_COMPLETED));
            if (orderDetail.getOrderDeliverdDate() != null)
                stepList.add(new Step("Order Delivered \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Delivered \n" + "", Step.State.NOT_COMPLETED));

        } else if (orderDetail.getOrderPlaced().contains("Yes") && orderDetail.getOrderPacked().contains("Yes") && orderDetail.getOrderDispatched().contains("No") && orderDetail.getOrderDelivered().contains("No")) {

            if (orderDetail.getOrderPlacedDate() != null)
                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Placed \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderPackedDate() != null)
                stepList.add(new Step("Order Packed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPackedDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Packed  \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderDispatchedDate() != null)
                stepList.add(new Step("Order Dispatched \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDispatchedDate().toString()), Step.State.CURRENT));
            else
                stepList.add(new Step("Order Dispatched \n" + "", Step.State.CURRENT));
            if (orderDetail.getOrderDeliverdDate() != null)
                stepList.add(new Step("Order Delivered \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Delivered \n" + "", Step.State.NOT_COMPLETED));

        } else if (orderDetail.getOrderPlaced().contains("Yes") && orderDetail.getOrderPacked().contains("Yes") && orderDetail.getOrderDispatched().contains("Yes") && orderDetail.getOrderDelivered().contains("No")) {

            if (orderDetail.getOrderPlacedDate() != null)
                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Placed \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderPackedDate() != null)
                stepList.add(new Step("Order Packed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPackedDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Packed  \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderDispatchedDate() != null)
                stepList.add(new Step("Order Dispatched \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDispatchedDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Dispatched \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderDeliverdDate() != null)
                stepList.add(new Step("Order Delivered \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString()), Step.State.CURRENT));
            else
                stepList.add(new Step("Order Delivered \n" + "", Step.State.CURRENT));

        } else if (orderDetail.getOrderPlaced().contains("Yes") && orderDetail.getOrderPacked().contains("Yes") && orderDetail.getOrderDispatched().contains("Yes") && orderDetail.getOrderDelivered().contains("Yes")) {

            if (orderDetail.getOrderPlacedDate() != null)
                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Placed \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderPackedDate() != null)
                stepList.add(new Step("Order Packed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPackedDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Packed  \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderDispatchedDate() != null)
                stepList.add(new Step("Order Dispatched \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDispatchedDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Dispatched \n" + "", Step.State.COMPLETED));
            if (orderDetail.getOrderDeliverdDate() != null)
                stepList.add(new Step("Order Delivered \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString()), Step.State.COMPLETED));
            else
                stepList.add(new Step("Order Delivered \n" + "", Step.State.COMPLETED));


        } else {
            if (orderDetail.getOrderPlacedDate() != null)
                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.CURRENT));
            else
                stepList.add(new Step("Order Placed \n" + "", Step.State.CURRENT));
            if (orderDetail.getOrderPackedDate() != null)
                stepList.add(new Step("Order Packed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPackedDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Packed  \n" + "", Step.State.NOT_COMPLETED));
            if (orderDetail.getOrderDispatchedDate() != null)
                stepList.add(new Step("Order Dispatched \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDispatchedDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Dispatched \n" + "", Step.State.NOT_COMPLETED));
            if (orderDetail.getOrderDeliverdDate() != null)
                stepList.add(new Step("Order Delivered \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderDeliverdDate().toString()), Step.State.NOT_COMPLETED));
            else
                stepList.add(new Step("Order Delivered \n" + "", Step.State.NOT_COMPLETED));

        }
//
//        if (orderDetail.getOrderStatus().equalsIgnoreCase("Cancel")) {
//            stepList = new ArrayList<>();
//            if (orderDetail.getOrderPlacedDate() != null)
//                stepList.add(new Step("Order Placed \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
//            else
//                stepList.add(new Step("Order Placed \n" + "", Step.State.COMPLETED));
//
//            if (orderDetail.getOrderPlacedDate() != null)
//                stepList.add(new Step("Order Cancelled \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPlacedDate()), Step.State.COMPLETED));
//            else
//                stepList.add(new Step("Order Cancelled \n" + "", Step.State.COMPLETED));
//        }

        mVerticalStepView.setCompletedStepTextColor(R.color.black_alpha);
        mVerticalStepView.setCurrentStepTextColor(R.color.black_alpha);
        mVerticalStepView.setNotCompletedStepTextColor(R.color.black_alpha);

        mVerticalStepView.setTextSize(18)
                .setCircleRadius(15)
                .setCompletedLineColor(Color.parseColor("#56b5f0"))
                .setNotCompletedLineColor(Color.parseColor("#56b5f0"))
                .setCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.check))
                .setNotCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.not_complete))
                .setCurrentStepIcon(AppCompatResources.getDrawable(this, R.drawable.current))
                .setLineLength(60);

        mVerticalStepView.setSteps(stepList);


        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, HelpDeskActivity.class);
                intent.putExtra("DATA", "Help Desk");
                startActivity(intent);
            }
        });


//        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(OrderDetailsActivity.this, CancelOrderActivity.class);
//                // intent.putExtra("OrderList", order);
//                intent.putExtra("SubOrderList", 0);
//                intent.putExtra("OrderList", order.getOrderId());
//                startActivityForResult(intent, REQUEST_CODE);
//                finish();
//            }
//        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, OrderReturnsProductListActivity.class);
                intent.putExtra("OrderList", order);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    private void getOrderDetails() {
        MyProgress.start(OrderDetailsActivity.this);
        Call<OrderDetailsDataResponse> call = RetrofitClient.getRetrofitClient().getOrderDetails(localData.getCustomerId(), order.getOrderId().toString());
        call.enqueue(new Callback<OrderDetailsDataResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsDataResponse> call, Response<OrderDetailsDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    orderDetail = response.body().getData().getOrderDetail();
                    setData();
                    getOrdersList();
                } else {
                    Toast.makeText(OrderDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(OrderDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrdersList() {
        MyProgress.start(OrderDetailsActivity.this);
        Call<CustomerOrderListDataResponse> call = RetrofitClient.getRetrofitClient().getCustomerOrdersListData(localData.getCustomerId(), order.getOrderId().toString());
        call.enqueue(new Callback<CustomerOrderListDataResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderListDataResponse> call, Response<CustomerOrderListDataResponse> response) {

                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    setUpOrdersList(response.body().getData().getProductList());
                } else {
                    Toast.makeText(OrderDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerOrderListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BTAG", t.getMessage());
                Toast.makeText(OrderDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpOrdersList(ArrayList<CustomerProductList> list) {

        RecyclerViewLayoutManager = new LinearLayoutManager(this);
        // Set LayoutManager on Recycler View
        rvOrderList.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvOrderList.setLayoutManager(HorizontalLayout);

        rvOrderList.setAdapter(new CustomerOrdersListAdapter(OrderDetailsActivity.this, list, this));
        tvItemNo.setText("No Of Items : " + list.size());
    }


    @Override
    public void onItemClick(CustomerProductList category) {

    }
}
