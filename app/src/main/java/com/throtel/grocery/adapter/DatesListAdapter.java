package com.throtel.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

public class DatesListAdapter extends RecyclerView.Adapter<DatesListAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<String> rowItems;
    private OnDeliveryDateCheckedListener onDeliveryDateCheckedListener;

    public DatesListAdapter(Context context, ArrayList<String> rowItems, OnDeliveryDateCheckedListener onDeliveryDateCheckedListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onDeliveryDateCheckedListener = onDeliveryDateCheckedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_dates_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final String group = rowItems.get(position);

        holder.tvCategoryName.setText(group);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onDeliveryDateCheckedListener.onDeliveryDateSelected(isChecked, group);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeliveryDateCheckedListener.onDeliveryDateSelected(true, group);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnDeliveryDateCheckedListener {
        void onDeliveryDateSelected(boolean selected, String groupList);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
