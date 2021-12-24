package com.throtel.grocery.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.SubscriptionOrderList;
import com.throtel.grocery.utils.LocalData;
import com.throtel.grocery.views.MyProgress;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDailySUbOrderDetailListAdapter extends RecyclerView.Adapter<MyDailySUbOrderDetailListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SubscriptionOrderList> rowItems;
    private StatusUpdateListener statusUpdateListener;


    public MyDailySUbOrderDetailListAdapter(Context context, ArrayList<SubscriptionOrderList> rowItems, StatusUpdateListener statusUpdateListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.statusUpdateListener = statusUpdateListener;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_subscription_detail_list, parent, false);
        return new MyDailySUbOrderDetailListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final SubscriptionOrderList order = rowItems.get(position);


        holder.tvName.setText(order.getProductName());
        holder.tvTotalAmnt.setText("₹ "+ order.getTotalPrice());
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
        holder.tvPrice.setText(order.getProductPrice().toString());
        holder.tvQty.setText(String.valueOf(order.getQuantity()));
        String deliveryDate= convertDate_yyyy_MM_dd_To_dd_MMM_yyyy(order.getCustomerOrderDeliverDate());
        holder.tvOrderdate.setText(deliveryDate);



        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + order.getProductImage();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);

        if(order.getOrderPause().contains("Yes"))
        {
            holder.tvPause.setText("Resume");
            holder.tvPause.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            holder.tvPause.setText("Pause");
            holder.tvPause.setTextColor(context.getResources().getColor(R.color.red));
        }


        holder.tvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(order.getOrderPause().contains("Yes"))
                {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to Resume Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   // getPauseOrder(order,"No",position);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
                else
                {

                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to Pause Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   // getPauseOrder(order,"Yes",position);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface StatusUpdateListener {
        void onStatusUpdate(int index, SubscriptionOrderList order);
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvPrice,tvQty,tvOrderdate,tvTotalAmnt,tvTime,tvPause;
        ImageView ivImage;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_pname);
            tvOrderdate=itemView.findViewById(R.id.tv_order_date);
            tvTotalAmnt = itemView.findViewById(R.id.tv_total_amnt);
            tvTime=itemView.findViewById(R.id.tv_ordered_time);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQty = itemView.findViewById(R.id.tv_qty);

            ivImage=itemView.findViewById(R.id.iv_img);
            tvPause=itemView.findViewById(R.id.tv_order_pause);


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

    //Add Product To Cart
//    private void getPauseOrder(final SubscriptionOrderList product,String status,final int rowIndex) {
//        MyProgress.start(context);
//
//        LocalData localData = LocalData.getInstance(context);
//
//        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
//                getPauseDailySubscription( localData.getCustomerId(), String.valueOf(product.getOrderCartId()),status);
//        call.enqueue(new Callback<DataResponse>() {
//            @Override
//            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    // rowItems.remove(product);
//                    statusUpdateListener.onStatusUpdate(rowIndex, product);
//                    notifyDataSetChanged();
//                } else {
//                    Toast.makeText(context,  response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("Sub Product List", t.getMessage());
//                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}

