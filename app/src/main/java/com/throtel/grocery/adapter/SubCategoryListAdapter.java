package com.throtel.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.models.SubCategoryList;

import java.util.ArrayList;

public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SubCategoryList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int index = -1;
    public boolean isAll = true;

    public  SubCategoryListAdapter(Context context, ArrayList<SubCategoryList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_sub_category_list, parent, false);
        return new SubCategoryListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final SubCategoryList category = rowItems.get(position);

        holder.tvName.setText(category.getSubCategoryName());

        /* set color for 'All' tab */
        if (isAll == true) {

            holder.tvName.setBackground(context.getResources().getDrawable(R.drawable.rounded_orange));
            holder.tvName.setWidth(100);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));

            isAll = false;
        } else {
            holder.tvName.setBackground(context.getResources().getDrawable(R.drawable.white_round));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black_alpha));
        }

        /* set color for selected tab */
        if (isAll == false) {
            if (index == position) {
                holder.tvName.setBackground(context.getResources().getDrawable(R.drawable.rounded_orange));
                holder.tvName.setTextColor(context.getResources().getColor(R.color.white));

            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
                if (index == 0)
                    isAll = true;
                //Log.d("BTAG", "INDEX IS...." + index);
               // Log.d("BTAG", "SUB CATEGORY IS...." + category.getSubCategoryId());
                onItemClickListener.onItemClick(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(SubCategoryList category);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
