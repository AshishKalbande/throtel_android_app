package com.throtel.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.OffersProductListActivity;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.CategoryOfferList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryOffersAdapter extends RecyclerView.Adapter<CategoryOffersAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<CategoryOfferList> rowItems;

    public CategoryOffersAdapter(Context context, ArrayList<CategoryOfferList> rowItems) {
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
        final CategoryOfferList categoryOfferList = rowItems.get(position);
//        String url = RetrofitClient.BASE_OFFER_IMAGE_URL + categoryOfferList.getOfferImage();
//        Log.d("BTAG", "URL IMAGE.." + url);
//
//        Picasso.with(context)
//                .load(url) //Load the image
//                .fit()
//                .placeholder(R.drawable.no_preview)
//                .error(R.drawable.no_preview)
//                .into(holder.ivImage);

        holder.tvName.setText(categoryOfferList.getOfferName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OffersProductListActivity.class);
                intent.putExtra("TYPE", "Category");
                intent.putExtra("DATA", categoryOfferList);
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
