package books.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.activity.AddressListActivity;
import books.activity.BaseActivity;
import books.activity.PaymentOptionsActivity;
import books.adapter.DatesListAdapter;
import books.adapter.SlotListAdapter;
import books.api.RetrofitClient;
import books.models.AddressList;
import books.models.DateList;
import books.models.DeliveryDatesResponse;
import books.models.SlotList;
import books.models.SlotListDataResponse;
import books.utils.NetworkUtil;
import books.utils.Utils;
import books.views.MyProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static books.fragments.BaseFragment.OrderType;


public class CheckOutSelectSlotActivity extends BaseActivity implements SlotListAdapter.OnItemClickListener, DatesListAdapter.OnDeliveryDateCheckedListener {
    private static final String TAG = CheckOutSelectSlotActivity.class.getSimpleName();
    public static int REQUEST_CODE = 102;
    private RecyclerView rvSlotList;
    private LinearLayout llPaymentProcess;
    private TextView tvName, tvType, tvFlatNo, tvMobile, tvSocietyName, tvLandmark, tvPincode;
    private AddressList listAddress;
    private ArrayList<String> listAdd;
    private TextView tvChangeAddress;
    private EditText edtDeliveryDate;
    private Dialog dialog;
    private ArrayList<String> deliveryDates = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_select_slot);

        //int addid= Integer.parseInt(getIntent().getStringExtra("address"));


        setUpToolbarBackButton("Address and Date & Time");

        listAddress = new AddressList();
        listAdd = new ArrayList<>();
        SlotTime = "";

        tvName = findViewById(R.id.tv_name);
        tvMobile = findViewById(R.id.tv_mobile);
        tvType = findViewById(R.id.tv_type);
        tvFlatNo = findViewById(R.id.tv_flat_no);
        tvSocietyName = findViewById(R.id.tv_society_name);
        tvLandmark = findViewById(R.id.tv_landmark);
        tvPincode = findViewById(R.id.tv_pincode);

        tvChangeAddress = findViewById(R.id.tv_change);
        edtDeliveryDate = findViewById(R.id.edt_delivery_date);

        rvSlotList = findViewById(R.id.rv_slot_list);

        listAdd = getIntent().getStringArrayListExtra("active");


        tvType.setText(listAdd.get(0));
        tvName.setText(listAdd.get(1));
        tvMobile.setText(listAdd.get(2));
        tvFlatNo.setText(listAdd.get(3) + ", " + listAdd.get(4) + ", " + listAdd.get(5)+", ");
        tvPincode.setText(listAdd.get(7) + ", " + listAdd.get(6));

        getDeliveryDates();

        llPaymentProcess = findViewById(R.id.ll_proceed_to_payment);

        tvChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(CheckOutSelectSlotActivity.this,AddressListActivity.class));
                // finish();
                Intent ca = new Intent(CheckOutSelectSlotActivity.this, AddressListActivity.class);
                ca.putExtra("changefor", "Checkout");
                startActivityForResult(ca, REQUEST_CODE);
                finish();
            }
        });

        llPaymentProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddressId = listAdd.get(8);
                if (TextUtils.isEmpty(edtDeliveryDate.getText().toString())) {
                    showToast("Please Select Delivery Date");
                } else if (SlotTime.equals("")) {
                    showToast("Please Select Delivery Slot Time");
                } else {
                    DeliveryDate = edtDeliveryDate.getText().toString();
                    getCheckDeliveryCharge();
                }


            }
        });

        edtDeliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CheckOutSelectSlotActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //tvStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("dd MMM yy");
                                String strDate = format.format(calendar.getTime());
                                edtDeliveryDate.setText(strDate);

                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar calendar = Calendar.getInstance();  // this is default system date
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());  //set min date                 // set today's date as min date
                calendar.add(Calendar.DAY_OF_MONTH, 30); // add date to 30 days later
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()); //set max date
                datePickerDialog.show();*/
                if (deliveryDates != null)
                    showCategorySelectionDialog(deliveryDates);
                else {
                    showToast("There is no date for delivery");
                }

            }
        });


        if (NetworkUtil.getConnectivityStatus(CheckOutSelectSlotActivity.this))
            getSlotList();

        else
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();


    }

    private void showCategorySelectionDialog(ArrayList<String> datesList) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delivery_dates);
        dialog.setCancelable(false);

        RecyclerView recyclerView = dialog.findViewById(R.id.rv_dates);
        DatesListAdapter adapter = new DatesListAdapter(this, datesList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnOk = dialog.findViewById(R.id.btn_OK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        dialog.show();
    }

    //Product List
    private void getSlotList() {
        MyProgress.start(CheckOutSelectSlotActivity.this);
        Call<SlotListDataResponse> call = RetrofitClient.getRetrofitClient().getSlotList(localData.getCustomerId());
        call.enqueue(new Callback<SlotListDataResponse>() {
            @Override
            public void onResponse(Call<SlotListDataResponse> call, Response<SlotListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    setUpSlotList(response.body().getData().getSlotList());
                } else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SlotListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(CheckOutSelectSlotActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSlotList(ArrayList<SlotList> slotList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckOutSelectSlotActivity.this);
        rvSlotList.setLayoutManager(layoutManager);
        rvSlotList.setAdapter(new SlotListAdapter(this, slotList, CheckOutSelectSlotActivity.this));
    }

    //Check Delivery CHarge...
    private void getCheckDeliveryCharge() {
        MyProgress.start(CheckOutSelectSlotActivity.this);
//        Call<DeliveryChargeDataResponse> call = RetrofitClient.getRetrofitClient().getDeliveryCharge(listAdd.get(7), localData.getCustomerId());
//        call.enqueue(new Callback<DeliveryChargeDataResponse>() {
//            @Override
//            public void onResponse(Call<DeliveryChargeDataResponse> call, Response<DeliveryChargeDataResponse> response) {
                MyProgress.stop();
//
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    if (OrderType == 0) {
//                        TotalAmount = RegularTotalAmount;
//                    } else if (OrderType == 1) {
//                        TotalAmount = BulkTotalAmount;
//                    }
//                    if (TotalAmount < response.body().getData().getDeliveryChargeDetail().getMaxPrice()) {
//                        DeliveryCharge = response.body().getData().getDeliveryChargeDetail().getDeliveryPrice();
//                    } else {
                        DeliveryCharge = 0.0;
                   // }
                    //Log.d("BTAG","DELIVERY CHARGE...."+DeliveryCharge);
                    startActivity(new Intent(CheckOutSelectSlotActivity.this, PaymentOptionsActivity.class));

//                }
//                else {
//        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//                }
            }






    private void getDeliveryDates() {
        RetrofitClient.getRetrofitClient().getDeliveryDatesList(
                localData.getCustomerId()
        ).enqueue(new Callback<DeliveryDatesResponse>() {
            @Override
            public void onResponse(Call<DeliveryDatesResponse> call, Response<DeliveryDatesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    ArrayList<DateList> arrayList = response.body().getData().getDateList();
                    for (DateList dateList : arrayList) {
                        deliveryDates.add(Utils.convertDate(dateList.getDeliveryDate(), "yyyy-MM-dd", "dd MMM yy"));
                    }
                } else {
                    showToast(getString(R.string.error_general));
                }
            }

            @Override
            public void onFailure(Call<DeliveryDatesResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(CheckOutSelectSlotActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(SlotList slot) {

    }

    @Override
    public void onDeliveryDateSelected(boolean selected, String groupList) {
        if (dialog != null)
            dialog.dismiss();
        edtDeliveryDate.setText(groupList);
    }
}
