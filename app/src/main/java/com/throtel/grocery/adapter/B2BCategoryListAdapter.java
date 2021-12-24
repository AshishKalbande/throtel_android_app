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
import com.throtel.grocery.models.GroupList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class B2BCategoryListAdapter  extends RecyclerView.Adapter<B2BCategoryListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<GroupList> rowItems;
    private OnItemClickListener onItemClickListener;

    public B2BCategoryListAdapter(Context context, ArrayList<GroupList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_category_list, parent, false);
        return new B2BCategoryListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final GroupList category = rowItems.get(position);

        holder.tvName.setText(category.getMainGroupName());

        String url = RetrofitClient.BASE_GROUP_IMAGE_URL + category.getMainGroupImage();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(GroupList category);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivImage=itemView.findViewById(R.id.iv_img);

        }
    }
}

