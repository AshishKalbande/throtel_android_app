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
import books.models.CustomerProductList;

public class CustomerOrdersListAdapter extends RecyclerView.Adapter<CustomerOrdersListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<CustomerProductList> rowItems;
    private OnItemClickListener onItemClickListener;

    public CustomerOrdersListAdapter(Context context, ArrayList<CustomerProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_customer_order_list, parent, false);
        return new CustomerOrdersListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final CustomerProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .placeholder(R.drawable.no_preview)
                .into(holder.ivImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(CustomerProductList category);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textview);
            ivImage = itemView.findViewById(R.id.img);

        }
    }
}
