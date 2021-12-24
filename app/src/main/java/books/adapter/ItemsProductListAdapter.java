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
import books.api.RetrofitClient;
import books.models.CustomerProductList;

import java.util.ArrayList;

public class ItemsProductListAdapter extends RecyclerView.Adapter<ItemsProductListAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<CustomerProductList> rowItems;

    public ItemsProductListAdapter(Context context, ArrayList<CustomerProductList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final CustomerProductList productList = rowItems.get(position);

        holder.tvProductName.setText(productList.getProductName());
       // if (productList.getProductType().equalsIgnoreCase("Regular / Subscription"))
            //holder.tvProductQuantity.setText(productList.getProductWeight());
        //else
            holder.tvProductQuantity.setText("quantity - " + productList.getQuantity() +", weight - " + productList.getProductWeight());
        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + productList.getProductImage1();
        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .placeholder(R.drawable.no_preview)
                .error(R.drawable.no_preview)
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvProductQuantity;
        ImageView imgProduct;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }
}
