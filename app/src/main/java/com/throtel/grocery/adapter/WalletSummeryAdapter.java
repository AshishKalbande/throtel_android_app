package com.throtel.grocery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.models.TransactionList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WalletSummeryAdapter extends RecyclerView.Adapter<WalletSummeryAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<TransactionList> rowItems;


    public WalletSummeryAdapter(Context context, ArrayList<TransactionList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_wallet_summery_list, parent, false);
        return new WalletSummeryAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final TransactionList data = rowItems.get(position);

        if (data.getTransactionAmount() != null) {
            holder.tvAmount.setText("₹ " + data.getTransactionAmount());
        }

        holder.tvDate.setText(convertDate(data.getTransactionDateTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "dd MMM yyyy , hh:mm a"));

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public static String convertDate(String strDate, String oldFormat, String newFormat) {
        if (strDate == null || TextUtils.isEmpty(strDate))
            return "";
        DateFormat originalFormat = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(newFormat);

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertServerDate_dd_MMM_yyyy(String strDate) {
        // DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+SS:SS");
        //  DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy , hh:mm");
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy , hh:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvAmount;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
}

