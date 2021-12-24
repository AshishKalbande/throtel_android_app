package books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.models.ProductList;

import java.util.ArrayList;

public class DailySubscriptionListAdapter extends RecyclerView.Adapter<DailySubscriptionListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ProductList> rowItems;
    private OnItemClickListener onItemClickListener;

    public DailySubscriptionListAdapter(Context context, ArrayList<ProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_daily_subscribe_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final ProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());

//        if(product.getSubProductList().size()>0) {
//            SubProductList subProduct = new SubProductList();
//            subProduct = product.getSubProductList().get(0);
//
//            Log.d("BTAG", "PRODUCT NET PRICE..." + subProduct.getProductNetPrice().toString());//liked
//
//            holder.tvPrice.setText("₹"+subProduct.getProductSellingPrice().toString());
//            holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + subProduct.getProductNetPrice().toString() + "</strike>"));
//            holder.tvDiscount.setText("Save : ₹" + subProduct.getDiscountAmount().toString());
//            holder.tvWeight.setText(subProduct.getProductWeight());
//
//            String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + subProduct.getProductImage1();
//
//            Picasso.with(context)
//                    .load(url) //Load the image
//                    .fit()
//                    .error(R.drawable.no_preview)
//                    .into(holder.ivImage);
//        }

        holder.tvAddToSubscribe.setOnClickListener(new View.OnClickListener() {
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
        public void onItemClick(ProductList product);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight;
        ImageView ivImage;
        TextView tvAddToSubscribe;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);
            ivImage = itemView.findViewById(R.id.img);

            tvAddToSubscribe=itemView.findViewById(R.id.tv_add_to_subscribe);

        }
    }
}

