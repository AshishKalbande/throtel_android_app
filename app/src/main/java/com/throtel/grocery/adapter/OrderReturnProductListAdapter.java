package com.throtel.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CustomerProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderReturnProductListAdapter extends RecyclerView.Adapter<OrderReturnProductListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<CustomerProductList> rowItems;
    private OnItemClickListener onItemClickListener;

    public OrderReturnProductListAdapter(Context context, ArrayList<CustomerProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_return_product_order_list, parent, false);
        return new OrderReturnProductListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final CustomerProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());
       // if (product.ge.equalsIgnoreCase("Regular / Subscription"))
            holder.tvWeight.setText(product.getProductWeight());
       // else
           // holder.tvWeight.setText(product.getQuantity() + "kg/Ltr");

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);

//        if (product.getOrderReturn().equals("Yes")) {
//            holder.tvReturn.setVisibility(View.GONE);
//        } else
//            holder.tvReturn.setVisibility(View.VISIBLE);

        holder.tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(product);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // onItemClickListener.onItemClick(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(CustomerProductList product);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvWeight, tvReturn;
        ImageView ivImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pname);
            tvWeight = itemView.findViewById(R.id.tv_weight);
            tvReturn = itemView.findViewById(R.id.tv_order_return);
            ivImage = itemView.findViewById(R.id.img_product);

        }
    }
}

