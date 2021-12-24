package books.activity;

import static books.activity.AddressListActivity.REQUEST_CODE;
import static books.fragments.BaseFragment.RegularTotalAmount;

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

import com.google.android.material.card.MaterialCardView;
import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import books.adapter.ImageSliderAdapter;
import books.api.RetrofitClient;
import books.models.Color;
import books.models.DataResponse;
import books.models.GroupWiseDataResponse;
import books.models.ProductDetail;
import books.models.ProductList;
import books.models.Size;
import books.views.MyProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.himanshusoni.quantityview.QuantityView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends BaseActivity {
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 101;
    QuantityView quantityView;
    private TextView tvName, tvMRP, tvSellPrice, tvWeight, tvDesc, tvSize,colrname;
    private MaterialCardView colors;
    private Button btnBuyNow, btnAddCart;
    private ProductList product = null;
    private ProductDetail productDetail=null;
    private int subProduct = 0;
    private LinearLayout llButtons;
    private int Qty = 0;
    private ViewPager viewPagerAdds;
    private CircleIndicator circleIndicator;
    private int position = 0;
    private String Id="";
    private ArrayList<Size> sizes;
    List<String> lables;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initViews();

        Intent intent = getIntent();
        if (getIntent() != null) {
            product = (ProductList) getIntent().getSerializableExtra("ProductDetails");
            //  subProduct = getIntent().getIntExtra("SubproductId", 0);
            Log.d("BTAG", "SELECTED WEIGHT...." + subProduct);
            setUpToolbarBackButton("");

            if (product != null) {
                setData();

            }
        }
    }
    private void initViews() {

        tvName = findViewById(R.id.tv_name);
        tvMRP = findViewById(R.id.tv_mrp);
        tvSellPrice = findViewById(R.id.tv_sell_price);
        tvWeight = findViewById(R.id.tv_item_weight);
        tvDesc = findViewById(R.id.tv_desc);
        tvSize = findViewById(R.id.tv_item_sizes);
        colrname = findViewById(R.id.colrname);
        colors = findViewById(R.id.colors);
        quantityView = findViewById(R.id.quantityView);
        btnBuyNow = findViewById(R.id.btn_buy_now);
        btnAddCart = findViewById(R.id.btn_add_to_cart);
        llButtons = findViewById(R.id.ll_buttons);
        llButtons.setVisibility(View.VISIBLE);

        viewPagerAdds = findViewById(R.id.view_pager_adds);
        circleIndicator = findViewById(R.id.indicator);

        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

//                if (newQuantity <= product.getAvailableQuantity()) {
//                    Qty = newQuantity;
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

    private void setData() {
        MyProgress.start(ProductDetailsActivity.this);
        LocalData localData = LocalData.getInstance(ProductDetailsActivity.this);
        Call<GroupWiseDataResponse> call = RetrofitClient.getRetrofitClient().
                getGroupWiseListData(localData.getCustomerId(),product.getProductId().toString());
        call.enqueue(new Callback<GroupWiseDataResponse>() {
            @Override
            public void onResponse(Call<GroupWiseDataResponse> call, Response<GroupWiseDataResponse> response) {
               // Log.d("tegs",response.body().getStatus());
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    //Log.d("tegs",response.body().getStatus());
                   // showToast(response.body().getMessage());
                    productDetail = response.body().getData().getProductDetail();
                    tvName.setText(productDetail.getProductName());
                    tvMRP.setText(Html.fromHtml("<strike> ₹ " + productDetail.getProductNetPrice().toString() + "</strike>"));
                    tvSellPrice.setText("₹ " + productDetail.getProductSellingPrice().toString());
                    tvWeight.setText(productDetail.getProductUnit());
                    tvDesc.setText(productDetail.getProductDescription());
                    //sizes = response.body().getData().getProductDetail().getSizeList();
                    Size size = new Size();
                    size = productDetail.getSizeList().get(0);

                    tvSize.setText(size.getSize());
                    Color color = new Color();
                    color=productDetail.getColorList().get(0);
                    colrname.setText(color.getColorName());
                    colors.setCardBackgroundColor(android.graphics.Color.parseColor(color.getColorCode()));



                    String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + productDetail.getProductImage1();
                      String url1 = RetrofitClient.BASE_PRODUCT_IMAGE_URL + productDetail.getProductImage2();
                    String url3 = RetrofitClient.BASE_PRODUCT_IMAGE_URL + productDetail.getProductImage3();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(url);
                    list.add(url1);
                    list.add(url3);
                    setUpAdds(list);
                } else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GroupWiseDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });


        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Qty = quantityView.getQuantity();
                if (Qty != 0) {
                    RegularTotalAmount = product.getProductSellingPrice() * Qty;
                    Log.d("BTAG", "TOTAL PRICE IS...." + RegularTotalAmount);
                    //OrderType = 0;
                    // startActivity(new Intent(ProductDetailsActivity.this,AddressListActivity.class));
                    addToCartProductForBuyNow(product, Qty);
                } else
                    showToast("Please Select Product Quantity");


            }
        });

        //Enquire Now For login user with od Service Request View

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qty = quantityView.getQuantity();
                if (Qty != 0) {
                    AddToCartProduct(product, Qty);
                } else
                    showToast("Please Select Product Quantity");

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

    //Add Product To Cart
    private void AddToCartProduct(final ProductList product, int Qty) {
        MyProgress.start(ProductDetailsActivity.this);
        LocalData localData = LocalData.getInstance(ProductDetailsActivity.this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    showToast(response.body().getMessage());

                } else {
                    showToast(response.body().getMessage());
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

    private void addToCartProductForBuyNow(final ProductList product, int Qty) {
        MyProgress.start(ProductDetailsActivity.this);
        LocalData localData = LocalData.getInstance(ProductDetailsActivity.this);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Intent ca = new Intent(ProductDetailsActivity.this, AddressListActivity.class);
                        ca.putExtra("changefor", "Checkout");
                        startActivityForResult(ca, REQUEST_CODE);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } else {
                    showToast(getString(R.string.error_general));
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
