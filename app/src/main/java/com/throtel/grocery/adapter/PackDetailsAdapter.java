package com.throtel.grocery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.PackProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PackDetailsAdapter extends RecyclerView.Adapter<PackDetailsAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<PackProductList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private   Double TotalPrice=0.0;
    List<String> lables;
    private int lastSelectedPosition = -1;


    public PackDetailsAdapter(Context context, ArrayList<PackProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_pack_product_list, parent, false);
        return new PackDetailsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final PackProductList packProduct = rowItems.get(position);

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

    public interface OnItemClickListener {
        public void onItemClick(PackProductList packProduct);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight;
        ImageView ivImage;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_net_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);
            ivImage = itemView.findViewById(R.id.img);




        }
    }


}

