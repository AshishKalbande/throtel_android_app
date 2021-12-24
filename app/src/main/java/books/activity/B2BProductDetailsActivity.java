package books.activity;

import static books.activity.AddressListActivity.REQUEST_CODE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import books.adapter.ImageSliderAdapter;
import books.api.RetrofitClient;
import books.models.DataResponse;
import books.models.ProductList;
import books.models.SubProductList;
import books.views.MyProgress;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.himanshusoni.quantityview.QuantityView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import static books.fragments.BaseFragment.OrderType;

public class B2BProductDetailsActivity extends BaseActivity {
    private static final String TAG = B2BProductDetailsActivity.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    QuantityView quantityView;
    private TextView tvName, tvMRP, tvSellPrice, tvWeight, tvDesc;
    private Button btnBuyNow, btnAddCart;
    private TextView tvAvailableStock;
    private LinearLayout llButtons;
    private ProductList product = null;
    private int subProduct = 0;
    private int Qty = 0;
    private ViewPager viewPagerAdds;
    private CircleIndicator circleIndicator;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (getIntent() != null)
            product = (ProductList) getIntent().getSerializableExtra("ProductDetails");
        subProduct = getIntent().getIntExtra("SubproductId", 0);
        setUpToolbarBackButton("");

        initViews();

        if (product != null) {
            setData();
        }
    }

    private void initViews() {
        tvName = findViewById(R.id.tv_name);
        tvMRP = findViewById(R.id.tv_mrp);
        tvSellPrice = findViewById(R.id.tv_sell_price);
        tvWeight = findViewById(R.id.tv_item_weightb2b);
        tvDesc = findViewById(R.id.tv_desc);
        quantityView = findViewById(R.id.quantityView);
        tvAvailableStock = findViewById(R.id.tv_available_stock);
        tvAvailableStock.setVisibility(View.VISIBLE);
        tvAvailableStock.setTextColor(getResources().getColor(R.color.green));
        tvWeight.setVisibility(View.VISIBLE);

        llButtons = findViewById(R.id.ll_buttons);
        btnBuyNow = findViewById(R.id.btn_buy_now);
        btnAddCart = findViewById(R.id.btn_add_to_cart);

        viewPagerAdds = findViewById(R.id.view_pager_adds);
        circleIndicator = findViewById(R.id.indicator);


        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

                Log.d("BTAG", "Old Qty...." + oldQuantity + "New Qty..." + newQuantity);
//                if (newQuantity <= product.getAvailableQuantityInKgOrLtr()) {
//                    Qty = newQuantity;
//
//                } else {
//                    quantityView.setQuantity(oldQuantity);
//                    showToast("No More product stock available");
//                }

            }

            @Override
            public void onLimitReached() {

            }
        });
    }

    private void setUpAdds(final ArrayList<String> bannerLists) {

        ImageSliderAdapter addsAdapter = new ImageSliderAdapter(this, bannerLists);
        viewPagerAdds.setAdapter(addsAdapter);
        circleIndicator.setViewPager(viewPagerAdds);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (position == bannerLists.size()) {
                    position = 0;
                }
                viewPagerAdds.setCurrentItem(position++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);
    }


    private void setData() {

        tvName.setText(product.getProductName());
        if (product.getProductSellingPrice() != null) {
            tvMRP.setText(Html.fromHtml("<strike> ₹ " + product.getProductSellingPrice().toString() + "</strike>"));
        } else
            tvMRP.setText(Html.fromHtml("<strike> ₹ " + 0.0 + "</strike>"));
        if (product.getProductNetPrice() != null) {
            tvSellPrice.setText("₹ " + product.getProductNetPrice() * 1000);
        } else
            tvSellPrice.setText("₹ " + 0.0);

        tvWeight.setText("1Kg / 1Ltr");
       // tvDesc.setText(product.getSubProductList().get(subProduct).getProductDesc());
//        if (product.getAvailableQuantityInKgOrLtr() != null) {
//            tvAvailableStock.setText("Available Stock : " + product.getAvailableQuantityInKgOrLtr());
//        } else
//            tvAvailableStock.setText("Available Stock : " + 0.0);
//
//
//        if (product.getMinQuantityInKgOrLtr() != null && product.getAvailableQuantityInKgOrLtr() != null) {
//            llButtons.setVisibility(View.VISIBLE);
//        }

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Qty = quantityView.getQuantity();
//                if (Qty != 0 && Qty >= product.getMinQuantityInKgOrLtr()) {
//                    BulkTotalAmount = product.getProductNetPrice() * 1000 * Qty;
//                    //OrderType = 1;
//                    //startActivity(new Intent(B2BProductDetailsActivity.this, AddressListActivity.class));
//                    AddToCartProductForBuyNow(product.getSubProductList().get(subProduct), Qty);
//                } else
//                    showToast("Please Select Product Quantity Minimum " + product.getMinQuantityInKgOrLtr());
            }
        });

        //Enquire Now For login user with od Service Request View

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Qty = quantityView.getQuantity();
//                if (Qty != 0 && Qty >= product.getMinQuantityInKgOrLtr()) {
//                    AddToCartProduct(product, Qty);
//                } else
//                    showToast("Please Select Product Quantity Minimum " + product.getMinQuantityInKgOrLtr());

            }
        });

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();
        //String url1 = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage2();
       // String url3 = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage3();
        ArrayList<String> list = new ArrayList<>();
        list.add(url);
       // list.add(url1);
        //list.add(url3);
        setUpAdds(list);
    }

    //Add Product To Cart
    private void AddToCartProduct(final ProductList product, int Qty) {
        MyProgress.start(B2BProductDetailsActivity.this);
        LocalData localData = LocalData.getInstance(B2BProductDetailsActivity.this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        showToast(response.body().getMessage());

                    } else {
                        showToast(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

    private void AddToCartProductForBuyNow(final SubProductList product, int Qty) {
        MyProgress.start(B2BProductDetailsActivity.this);
        LocalData localData = LocalData.getInstance(B2BProductDetailsActivity.this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Intent ca = new Intent(B2BProductDetailsActivity.this, AddressListActivity.class);
                        ca.putExtra("changefor", "Checkout");
                        startActivityForResult(ca, REQUEST_CODE);

                    } else {
                        showToast(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
    }

}
