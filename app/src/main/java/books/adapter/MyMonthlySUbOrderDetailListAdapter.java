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
import books.models.SubscriptionOrderList;

public class MyMonthlySUbOrderDetailListAdapter extends RecyclerView.Adapter<MyMonthlySUbOrderDetailListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SubscriptionOrderList> rowItems;


    public MyMonthlySUbOrderDetailListAdapter(Context context, ArrayList<SubscriptionOrderList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_monthly_order_detail_list, parent, false);
        return new MyMonthlySUbOrderDetailListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final SubscriptionOrderList order = rowItems.get(position);


        holder.tvName.setText(order.getProductName());
        holder.tvPrice.setText("₹ "+order.getProductPrice());
        holder.tvTotalAmnt.setText("₹ "+ order.getTotalPrice());
        holder.tvQty.setText(String.valueOf(order.getQuantity()));


        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + order.getProductImage();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }



    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvPrice,tvTotalAmnt,tvQty;
        ImageView ivImage;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pname);
            tvPrice=itemView.findViewById(R.id.tv_price);
            tvTotalAmnt = itemView.findViewById(R.id.tv_total_amnt);
            tvQty=itemView.findViewById(R.id.tv_qty);

            ivImage=itemView.findViewById(R.id.iv_img);

        }
    }


}

