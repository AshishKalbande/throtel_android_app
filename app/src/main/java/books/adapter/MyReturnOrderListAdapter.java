package books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;

import java.util.ArrayList;

import books.api.RetrofitClient;
import books.models.ReturnProductList;

public class MyReturnOrderListAdapter extends RecyclerView.Adapter<MyReturnOrderListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ReturnProductList> rowItems;
    private OnItemClickListener onItemClickListener;

    public MyReturnOrderListAdapter(Context context, ArrayList<ReturnProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_return_product_order_list, parent, false);
        return new MyReturnOrderListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final ReturnProductList order = rowItems.get(position);

        holder.tvName.setText(order.getProductName());
        if (order.getProductType().equalsIgnoreCase("Regular / Subscription"))
            holder.tvWeight.setText(order.getProductWeight());
        else
            holder.tvWeight.setText(order.getQuntity() + "kg/Ltr");

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + order.getProductImage1();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);

        holder.tvReturn.setVisibility(View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(order);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ReturnProductList order);
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

