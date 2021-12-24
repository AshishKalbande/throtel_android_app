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
import com.throtel.grocery.models.PackList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PackListAdapter extends RecyclerView.Adapter<PackListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<PackList> rowItems;
    private OnItemClickListener onItemClickListener;

    public PackListAdapter(Context context, ArrayList<PackList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_pack_list, parent, false);
        return new PackListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final PackList pack = rowItems.get(position);

        holder.tvPackName.setText(pack.getPackName());
        String url = RetrofitClient.BASE_SUBSCRIPTION_PACK_IMAGE_URL + pack.getPackImage();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivPackImage);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(pack);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(PackList pack);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPackName;
        ImageView ivPackImage;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvPackName = itemView.findViewById(R.id.tv_pack_name);
            ivPackImage=itemView.findViewById(R.id.iv_pack);



        }
    }


}


