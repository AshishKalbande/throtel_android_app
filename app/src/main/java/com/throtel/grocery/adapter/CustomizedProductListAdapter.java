package com.throtel.grocery.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CartProductList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.utils.LocalData;
import com.throtel.grocery.views.MyProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomizedProductListAdapter extends RecyclerView.Adapter<CustomizedProductListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<CartProductList> rowItems;
    private StatusUpdateListener statusUpdateListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private   Double TotalPrice=0.0;
    List<String> lables;
    private int lastSelectedPosition = -1;


    public CustomizedProductListAdapter(Context context, ArrayList<CartProductList> rowItems, StatusUpdateListener statusUpdateListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.statusUpdateListener = statusUpdateListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_customized_product_list, parent, false);
        return new CustomizedProductListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final CartProductList packProduct = rowItems.get(position);

        holder.tvName.setText(packProduct.getProductName());
        holder.tvPrice.setText(" ₹ " + packProduct.getProductSellingPrice().toString());
        holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + packProduct.getProductNetPrice().toString() + "</strike>"));
        holder.tvDiscount.setText("Save : ₹"+packProduct.getDiscountAmount().toString());
        holder.tvWeight.setText(packProduct.getProductWeight());

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + packProduct.getProductImage1();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to Delete Product?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //DeleteCartProduct(packProduct, position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onItemClickListener.onItemClick(product);
                // SubProductList assignWeight=product.getSubProductList().get(position);


            }
        });




    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface StatusUpdateListener {
        void onStatusUpdate(int index, CartProductList packProduct);
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight;
        ImageView ivImage,ivDelete;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_net_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);

            ivImage = itemView.findViewById(R.id.img);
            ivDelete=itemView.findViewById(R.id.img_delete);




        }
    }

    //Delete Product from Cart
//    private void DeleteCartProduct(final CartProductList product, final int rowIndex) {
//        MyProgress.start(context);
//        LocalData localData = LocalData.getInstance(context);
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
//                deleteCustomizedProduct(localData.getCustomerId(),String.valueOf(product.getCartId()));
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    rowItems.remove(rowIndex);
//                    statusUpdateListener.onStatusUpdate(rowIndex, product);
//                    notifyDataSetChanged();
//                } else {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("Sub Product List", t.getMessage());
//                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}

