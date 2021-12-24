package books.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.activity.OffersProductListActivity;
import books.models.GlobalOfferList;

public class GlobalOffersAdapter extends RecyclerView.Adapter<GlobalOffersAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<GlobalOfferList> rowItems;

    public GlobalOffersAdapter(Context context, ArrayList<GlobalOfferList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_offers, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final GlobalOfferList globalOfferList = rowItems.get(position);
//        String url = RetrofitClient.BASE_OFFER_IMAGE_URL + globalOfferList.getOfferImage();
//        Log.d("BTAG", "URL IMAGE.." + url);
//
//        Picasso.with(context)
//                .load(url) //Load the image
//                .fit()
//                .placeholder(R.drawable.no_preview)
//                .error(R.drawable.no_preview)
//                .into(holder.ivImage);

        holder.tvName.setText(globalOfferList.getOfferName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OffersProductListActivity.class);
                intent.putExtra("TYPE", "Global");
                intent.putExtra("DATA", globalOfferList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_offer_name);
            ivImage = itemView.findViewById(R.id.image);
        }
    }
}
