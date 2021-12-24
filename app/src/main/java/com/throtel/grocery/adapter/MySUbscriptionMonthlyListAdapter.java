package com.throtel.grocery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.CancelOrderActivity;
import com.throtel.grocery.models.SubscriptionMonthlyOrderList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MySUbscriptionMonthlyListAdapter extends RecyclerView.Adapter<MySUbscriptionMonthlyListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SubscriptionMonthlyOrderList> rowItems;
    private OnItemClickListener onItemClickListener;
    public static int REQUEST_CODE = 102;

    public MySUbscriptionMonthlyListAdapter(Context context, ArrayList<SubscriptionMonthlyOrderList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_my_subscription_monthly_list, parent, false);
        return new MySUbscriptionMonthlyListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final SubscriptionMonthlyOrderList order = rowItems.get(position);

        holder.tvType.setText(order.getOrderType());
        Log.d("BTAG","ORDER TYPE...."+order.getOrderType());
        holder.tvTotalAmnt.setText("₹ "+ String.valueOf(order.getOrderTotal()));
        //======== Date and time change formate.....
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try{

            Date date3 = sdf.parse(order.getCustomerOrderDeliverStartTime());
            // Date date4 = sdf.parse(order.getCustomerOrderDeliverEndTime());
            //new format
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
            //formatting the given time to new format with AM/PM
            System.out.println("Given time in AM/PM: "+sdf2.format(date3));
            holder.tvTime.setText(sdf2.format(date3));
        }catch(ParseException e){
            e.printStackTrace();
        }
        String deliveryDate= convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(order.getCustomerOrderDeliverDate());
        holder.tvOrderdate.setText("Start Date :"+ deliveryDate);

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CancelOrderActivity.class);
                intent.putExtra("SubOrderList", order.getOrderId());
                intent.putExtra("OrderList", 0);
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);


            }
        });

        holder.tvStatus.setText(order.getOrderStatus());
        if(order.getOrderStatus().contains("Pending"))
        {
            holder.tvCancel.setVisibility(View.VISIBLE);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.orange));

        }
        else if(order.getOrderStatus().contains("Ongoing")) {
            holder.tvCancel.setVisibility(View.GONE);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        else if(order.getOrderStatus().contains("Complete")) {
            holder.tvCancel.setVisibility(View.GONE);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if(order.getOrderStatus().contains("Cancel")) {
            holder.tvCancel.setVisibility(View.GONE);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(order);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(SubscriptionMonthlyOrderList order);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvType,tvOrderdate,tvTotalAmnt,tvTime,tvValidity;
        TextView tvCancel,tvStatus;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            tvOrderdate=itemView.findViewById(R.id.tv_order_date);
            tvTotalAmnt = itemView.findViewById(R.id.tv_total_amnt);
            tvTime=itemView.findViewById(R.id.tv_ordered_time);
            tvValidity = itemView.findViewById(R.id.tv_validity);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
            tvStatus = itemView.findViewById(R.id.tv_status);


        }
    }

    public String convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(String strDate) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yy");

        try {
            Date date = originalFormat.parse(strDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}

