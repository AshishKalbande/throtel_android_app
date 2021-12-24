package com.throtel.grocery.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.ReturnDetailsDataResponse;
import com.throtel.grocery.models.ReturnProductDetail;
import com.throtel.grocery.models.ReturnProductList;
import com.throtel.grocery.utils.Utils;
import com.throtel.grocery.views.MyProgress;
import com.squareup.picasso.Picasso;
import com.vinay.stepview.VerticalStepView;
import com.vinay.stepview.models.Step;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnOrderListDetailsActivity extends BaseActivity {
    private static final String TAG = ReturnOrderListDetailsActivity.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    private TextView tvOrderNo, tvOrderCode, tvOrderDate;
    private TextView tvPname, tvTotal, tvWeight, tvNote;
    private ImageView ImgProduct, ImgReturnProduct;
    private Button btnHelp;
    private ReturnProductList order = null;
    private ReturnProductDetail orderDetail = null;
    private VerticalStepView mVerticalStepView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order_details);
        initViews();


        if (getIntent() != null)
            order = (ReturnProductList) getIntent().getSerializableExtra("OrderList");
        setUpToolbarBackButton("Order Details");

        if (order != null) {
            //getOrderDetails();

        }
    }

    private void initViews() {
        tvOrderNo = findViewById(R.id.tv_order_id);
        tvOrderCode = findViewById(R.id.tv_order_code);
        tvOrderDate = findViewById(R.id.tv_order_date);
        tvPname = findViewById(R.id.tv_pname);
        tvWeight = findViewById(R.id.tv_weight);
        tvTotal = findViewById(R.id.tv_price);
        tvNote = findViewById(R.id.tv_note);

        ImgProduct = findViewById(R.id.iv_img);
        ImgReturnProduct = findViewById(R.id.img_return);

        btnHelp = findViewById(R.id.btn_help);


        mVerticalStepView = findViewById(R.id.vertical_step_view);
    }


    private void setData() {


        tvOrderNo.setText(order.getReturnId().toString());
        tvOrderCode.setText(orderDetail.getOrderCode());
        tvOrderDate.setText("Order Date : " + Utils.convertDate(orderDetail.getOrderDate(), "yyyy-MM-dd", "dd MMM yy"));
        tvPname.setText(orderDetail.getProductName());
        tvWeight.setText(orderDetail.getProductWeight());
        tvTotal.setText("₹ " + orderDetail.getProductPrice());
        tvNote.setText(orderDetail.getProductReturnReview());

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + orderDetail.getProductImage1();

        Picasso.with(ReturnOrderListDetailsActivity.this)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .placeholder(R.drawable.no_preview)
                .into(ImgProduct);

//        String urlreturn = RetrofitClient.BASE_RETURN_IMAGE_URL + orderDetail.getProductReturnImage();
//
//        Picasso.with(ReturnOrderListDetailsActivity.this)
//                .load(urlreturn) //Load the image
//                .fit()
//                .error(R.drawable.no_preview)
//                .placeholder(R.drawable.no_preview)
//                .into(ImgReturnProduct);


        //Steper for order status....
        mVerticalStepView.setReverse(false);
        List<Step> stepList = new ArrayList<>();
        try {
            if (orderDetail.getReturnOrderStatus().equalsIgnoreCase("Cancel")) {
                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.CURRENT));
                stepList.add(new Step("Order Rejected by vendor \n ", Step.State.COMPLETED));
            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("No")
                    && orderDetail.getSupervisorOrderConfirmation().contains("No")
                    && orderDetail.getOrderAssigned().contains("No") && orderDetail.getDeliveryBoyOrderConfirmation().contains("No")
                    && orderDetail.getOrderPicked().contains("No") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {


                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.CURRENT));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("No")
                    && orderDetail.getOrderAssigned().contains("No") && orderDetail.getDeliveryBoyOrderConfirmation().contains("No")
                    && orderDetail.getOrderPicked().contains("No") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {


                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.CURRENT));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("No") && orderDetail.getDeliveryBoyOrderConfirmation().contains("No")
                    && orderDetail.getOrderPicked().contains("No") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {

                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.CURRENT));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("Yes") && orderDetail.getDeliveryBoyOrderConfirmation().contains("No")
                    && orderDetail.getOrderPicked().contains("No") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {

                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.CURRENT));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("Yes") && orderDetail.getDeliveryBoyOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderPicked().contains("No") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {
                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.CURRENT));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("Yes") && orderDetail.getDeliveryBoyOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderPicked().contains("Yes") && orderDetail.getOrderRecievedBySupervisor().contains("No")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {
                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.CURRENT));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));
            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("Yes") && orderDetail.getDeliveryBoyOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderPicked().contains("Yes") && orderDetail.getOrderRecievedBySupervisor().contains("Yes")
                    && orderDetail.getOrderRecievedByVendor().contains("No")) {

                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.CURRENT));

            } else if (orderDetail.getOrderRaise().contains("Yes") && orderDetail.getVendorOrderConfirmation().contains("Yes")
                    && orderDetail.getSupervisorOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderAssigned().contains("Yes") && orderDetail.getDeliveryBoyOrderConfirmation().contains("Yes")
                    && orderDetail.getOrderPicked().contains("Yes") && orderDetail.getOrderRecievedBySupervisor().contains("Yes")
                    && orderDetail.getOrderRecievedByVendor().contains("Yes")) {

                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.COMPLETED));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.COMPLETED));

            } else {
                stepList.add(new Step("Order Raise  \n" + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRaiseDate()), Step.State.CURRENT));
                stepList.add(new Step("Warehouse Order Confirmation \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getVendorOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Supervisor Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getSupervisorOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Assigned   \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderAssignedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("DeliveryBoy Order Confirmation  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getDeliveryBoyOrderConfirmationDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Picked  \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderPickedDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Supervisor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedBySupervisorDate()), Step.State.NOT_COMPLETED));
                stepList.add(new Step("Order Recieved By Vendor \n " + convertServerDate_dd_MMM_yyyy(orderDetail.getOrderRecievedByVendorDate()), Step.State.NOT_COMPLETED));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mVerticalStepView.setCompletedStepTextColor(R.color.black_alpha);
        mVerticalStepView.setCurrentStepTextColor(R.color.black_alpha);
        mVerticalStepView.setNotCompletedStepTextColor(R.color.black_alpha);

        mVerticalStepView.setTextSize(18)
                .setCircleRadius(15)
                .setCompletedLineColor(Color.parseColor("#f68920"))
                .setNotCompletedLineColor(Color.parseColor("#f68920"))
                .setCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.check))
                .setNotCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.not_complete))
                .setLineLength(60);

        mVerticalStepView.setSteps(stepList);


        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReturnOrderListDetailsActivity.this, HelpDeskActivity.class);
                intent.putExtra("DATA", "Help Desk");
                startActivity(intent);
            }
        });


    }

//    private void getOrderDetails() {
//        MyProgress.start(ReturnOrderListDetailsActivity.this);
//        Call<ReturnDetailsDataResponse> call = RetrofitClient.getRetrofitClient().getReturnOrderDetails(localData.getCustomerId(), order.getReturnId().toString());
//        call.enqueue(new Callback<ReturnDetailsDataResponse>() {
//            @Override
//            public void onResponse(Call<ReturnDetailsDataResponse> call, Response<ReturnDetailsDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    orderDetail = response.body().getData().getReturnProductDetail();
//                    setData();
//
//                } else {
//                    Toast.makeText(ReturnOrderListDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReturnDetailsDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(ReturnOrderListDetailsActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//

}
