package books.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import books.activity.RatingActivity;
import books.models.OrderList;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<OrderList> rowItems;
    private OnItemClickListener onItemClickListener;

    public OrdersListAdapter(Context context, ArrayList<OrderList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_order_list, parent, false);
        return new OrdersListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        final OrderList order = rowItems.get(position);

        holder.tvOrderId.setText(order.getOrderCode());
        holder.tvTotalAmnt.setText(order.getOrderTotal().toString());
        //======== Date and time change formate.....
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            String deliveryDate = convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(order.getCustomerOrderDeliverDate());
            Date date3 = sdf.parse(order.getCustomerOrderDeliverStartTime());
            Date date4 = sdf.parse(order.getCustomerOrderDeliverEndTime());
            //new format
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
            //formatting the given time to new format with AM/PM
            System.out.println("Given time in AM/PM: " + sdf2.format(date3));
            holder.tvOrderedDate.setText(deliveryDate + " , " + sdf2.format(date3) + " - " + sdf2.format(date4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //==============
        holder.tvOrderStatus.setText(order.getOrderStatus());
        if (order.getOrderStatus().contains("Confirmed")) {
            holder.tvOTP.setVisibility(View.VISIBLE);
            holder.tvOTP.setText(order.getOrderOtp());
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.orange));
            holder.tvOrderStatus.setBackground(context.getDrawable(R.drawable.bg_confirm_line));
            holder.btnRating.setVisibility(View.GONE);
        } else if (order.getOrderStatus().contains("Complete")) {
            holder.tvOTP.setVisibility(View.GONE);
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.tvOrderStatus.setBackground(context.getDrawable(R.drawable.bg_delivered_line));
          //  if (TextUtils.isEmpty(order.getReviewStatus()))
                //holder.btnRating.setVisibility(View.VISIBLE);
            //else if (order.getReviewStatus().equalsIgnoreCase("No"))
               // holder.btnRating.setVisibility(View.VISIBLE);
//            else
//                holder.btnRating.setVisibility(View.GONE);
        } else if (order.getOrderStatus().contains("Cancel")) {
            holder.tvOTP.setVisibility(View.GONE);
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.tvOrderStatus.setBackground(context.getDrawable(R.drawable.bg_cancelled_line));
            holder.btnRating.setVisibility(View.GONE);
        } else if (order.getOrderStatus().contains("Ongoing")) {
            holder.tvOrderStatus.setText("Confirmed");
            holder.tvOTP.setVisibility(View.VISIBLE);
            holder.tvOTP.setText("OTP : " + order.getOrderOtp());
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.orange));
            holder.tvOrderStatus.setBackground(context.getDrawable(R.drawable.bg_confirm_line));
            holder.btnRating.setVisibility(View.GONE);
        } else if (order.getOrderStatus().contains("Pending")) {
            holder.tvOTP.setVisibility(View.GONE);
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.tvOrderStatus.setBackground(context.getDrawable(R.drawable.bg_brown_line));
            holder.btnRating.setVisibility(View.GONE);
            holder.tvOrderStatus.setText("In progress");
        }

        holder.btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RatingActivity.class);
                intent.putExtra("OrderId", String.valueOf(order.getOrderId()));
                context.startActivity(intent);
            }
        });

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

    public interface OnItemClickListener {
        void onItemClick(OrderList order);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvOrderStatus, tvTotalAmnt, tvOrderedDate, tvOTP;
        Button btnRating;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            tvTotalAmnt = itemView.findViewById(R.id.tv_total_amnt);
            tvOrderedDate = itemView.findViewById(R.id.tv_ordered_time);

            tvOTP = itemView.findViewById(R.id.tv_order_otp);

            btnRating = itemView.findViewById(R.id.btn_rating);

        }
    }
}

