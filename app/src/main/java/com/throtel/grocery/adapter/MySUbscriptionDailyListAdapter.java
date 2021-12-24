package com.throtel.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.models.SubscriptionOrdersList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MySUbscriptionDailyListAdapter extends RecyclerView.Adapter<MySUbscriptionDailyListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SubscriptionOrdersList> rowItems;
    private OnItemClickListener onItemClickListener;

    public MySUbscriptionDailyListAdapter(Context context, ArrayList<SubscriptionOrdersList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_my_subscription_list, parent, false);
        return new MySUbscriptionDailyListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final SubscriptionOrdersList order = rowItems.get(position);


        holder.tvName.setText(order.getProductName());
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
        holder.tvValidity.setText("1 Month");
        String deliveryDate= convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(order.getCustomerOrderDeliverDate());
        holder.tvOrderdate.setText("Start Date :"+ deliveryDate);

        holder.tvCancel.setVisibility(View.GONE);



        holder.tvStatus.setText(order.getOrderStatus());
        if(order.getOrderStatus().contains("Ongoing"))
        {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));

        }
        else if(order.getOrderStatus().contains("Complete")) {

            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
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
        public void onItemClick(SubscriptionOrdersList order);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvOrderdate,tvTotalAmnt,tvTime,tvValidity;
        TextView tvCancel,tvStatus;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pname);
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

