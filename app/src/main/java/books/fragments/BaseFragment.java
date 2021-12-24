package books.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import books.activity.ViewCartTabsActivity;
import books.api.RetrofitClient;
import books.models.CartProductList;
import books.models.ViewCartDataResponse;
import books.views.MyProgress;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment extends Fragment {
   // public static int OrderType = 0;
    public static Double TotalAmount = 0.0;
    public static Double RegularTotalAmount = 0.0;
    public static Double BulkTotalAmount = 0.0;
    public static int RegularItemCount = 0;
   // public static int BulkItemCount = 0;
   // public static double WalletAmount = 0.0;
    protected LocalData localData;
    protected Context context;
    private int count = 0;
    private Double TotalPrice = 0.0, TotalSaved = 0.0, TotalPriceBulk = 0.0, TotalSavedBulk = 0.0;
    private OnBadgeCountChangedListener onBadgeCountChangedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        localData = LocalData.getInstance(context);
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onBadgeCountChangedListener = (OnBadgeCountChangedListener) activity;
        } catch (ClassCastException e) {
            Log.e("BaseFragment", "" + e.getLocalizedMessage());
        }
    }

    protected void getViewCartList(View view) {

        final FrameLayout frameCheckOut = view.findViewById(R.id.frame_checkout);
        final TextView tvItemNo = view.findViewById(R.id.tv_item_no);
        final TextView tvTotalAmnt = view.findViewById(R.id.tv_total_amnt);
        final TextView tvTotalSaved = view.findViewById(R.id.tv_total_saved);

        frameCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, ViewCartTabsActivity.class));

            }
        });

        count = 0;

        Call<ViewCartDataResponse> call = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId());

        call.enqueue(new Callback<ViewCartDataResponse>() {
            @Override
            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {

                    // setUpViewCartList(response.body().getData().getCartProductList());
                    ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
                    frameCheckOut.setVisibility(View.VISIBLE);

                    TotalPrice = 0.0;
                    TotalSaved = 0.0;
                    for (CartProductList i : cartList) {
                        TotalPrice += i.getProductSellingPrice() * i.getSelectedQuantity();
                        TotalSaved += (i.getProductNetPrice() - i.getProductSellingPrice()) * i.getSelectedQuantity();
                    }
                    count = count + cartList.size();
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved ));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                        TotalPrice = 0.0;
                        TotalSaved = 0.0;
                    }
                } else {
                    //  showToast(response.body().getMessage());
                    //frameCheckOut.setVisibility(View.GONE);
                    if (count > 0) {
                        tvItemNo.setText(count + " Items");
                        tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice ));
                        tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved ));
                    } else {
                        frameCheckOut.setVisibility(View.GONE);
                        TotalPrice = 0.0;
                        TotalSaved = 0.0;
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("BaseFragment", t.getMessage());
                showToast(getString(R.string.error_general));
            }
        });
//
//        Call<ViewCartDataResponse> call1 = RetrofitClient.getRetrofitClient().getViewCartList(localData.getCustomerId(), "Bulk");
//
//        call1.enqueue(new Callback<ViewCartDataResponse>() {
//            @Override
//            public void onResponse(Call<ViewCartDataResponse> call, Response<ViewCartDataResponse> response) {
//                MyProgress.stop();
//
//                if (response.body() != null) {
//
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//
//                        // setUpViewCartList(response.body().getData().getCartProductList());
//                        ArrayList<CartProductList> cartList = response.body().getData().getCartProductList();
//                        frameCheckOut.setVisibility(View.VISIBLE);
//
//                        TotalPriceBulk = 0.0;
//                        TotalSavedBulk = 0.0;
//                        for (CartProductList i : cartList) {
////                            TotalPriceBulk += i.getProductPricePerGramOrMl() * 1000 * i.getSelectedQuantity();
////                            TotalSavedBulk += (i.getProductNetPrice() - i.getProductPricePerGramOrMl()) * i.getSelectedQuantity();
//                        }
//                        count = count + cartList.size();
//
//                        if (count > 0) {
//                            tvItemNo.setText(count + " Items");
//                            tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice + TotalPriceBulk));
//                            tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved + TotalSavedBulk));
//                        } else {
//                            frameCheckOut.setVisibility(View.GONE);
//                            TotalPriceBulk = 0.0;
//                            TotalSavedBulk = 0.0;
//                        }
//                    } else {
//                        //  showToast(response.body().getMessage());
//                        //frameCheckOut.setVisibility(View.GONE);
//                        if (count > 0) {
//                            tvItemNo.setText(count + " Items");
//                            tvTotalAmnt.setText(" ₹ " + new DecimalFormat("##.##").format(TotalPrice + TotalPriceBulk));
//                            tvTotalSaved.setText("Total Saved : ₹ " + new DecimalFormat("##.##").format(TotalSaved + TotalSavedBulk));
//                        } else {
//                            frameCheckOut.setVisibility(View.GONE);
//                            TotalPriceBulk = 0.0;
//                            TotalSavedBulk = 0.0;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ViewCartDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BaseFragment", t.getMessage());
//                showToast(getString(R.string.error_general));
//            }
//        });

    }

    public interface OnBadgeCountChangedListener {
        void onBadgeCountChanged(int badCount);
    }
}
