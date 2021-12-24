package com.throtel.grocery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
//git
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.activity.AddressListActivity;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.AddressList;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.utils.LocalData;
import com.throtel.grocery.views.MyProgress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ItemViewHolder> {

    public static int lastSelectedPosition = -1;
    List<String> lables;
    private Context context;
    private ArrayList<AddressList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private Double TotalPrice = 0.0;

    public AddressListAdapter(Context context, ArrayList<AddressList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_address_list, parent, false);
        return new AddressListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final AddressList address = rowItems.get(position);

        holder.tvType.setText(address.getAddressType());
        holder.tvName.setText(address.getName());
        holder.tvMobile.setText("Mobile No - " + address.getPhone());
        holder.tvFlatNo.setText(address.getFlatOrHouseNumber() + ", " + address.getStreetOrSocietyName() + ", " + address.getLandmark() +", ");
       holder.tvPincode.setText(address.getPincodeNumber() + ", " + address.getCityName());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(address);
            }
        });
        //change status
        holder.rbStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatusChange(String.valueOf(address.getAddressId()), "Active");
                holder.rbStatus.setChecked(true);
            }
        });

        if (address.getStatus().equals("Active")) {
            holder.rbStatus.setChecked(true);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(address);
            }
        });


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    //chnage status api call
    private void getStatusChange(String Addid, final String sts) {
        //MyProgress.start(context);
        LocalData localData = LocalData.getInstance(context);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                getStatusChange(localData.getCustomerId(), Addid, sts);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   /* address.setStatus(sts);
                    rowItems.set(rowIndex,address);*/
                        ((AddressListActivity) context).resetGraph(context);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(AddressList address);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvType, tvFlatNo, tvMobile, tvSocietyName, tvLandmark, tvPincode;
        ImageView ivEdit, ivImgageDelete;
        RadioButton rbStatus;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvType = itemView.findViewById(R.id.tv_type);
            tvFlatNo = itemView.findViewById(R.id.tv_flat_no);
            tvSocietyName = itemView.findViewById(R.id.tv_society_name);
            tvLandmark = itemView.findViewById(R.id.tv_landmark);
            tvPincode = itemView.findViewById(R.id.tv_pincode);
            rbStatus = itemView.findViewById(R.id.rb_change_status);

            ivEdit = itemView.findViewById(R.id.iv_edit);


        }
    }


}
