package books.adapter;

import static books.activity.BaseActivity.SlotTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import books.models.SlotList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlotListAdapter extends RecyclerView.Adapter<SlotListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<SlotList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private   Double TotalPrice=0.0;
    List<String> lables;
    private int lastSelectedPosition = -1;


    public SlotListAdapter(Context context, ArrayList<SlotList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_slot_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final SlotList slot = rowItems.get(position);

        SimpleDateFormat timecovert = new SimpleDateFormat("HH:mm:ss");

        try{

            Date date3 = timecovert.parse(slot.getStartTime());
            Date date4 = timecovert.parse(slot.getEndTime());

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");

            System.out.println("Given time in AM/PM: "+sdf2.format(date3));
            holder.tvTime.setText(sdf2.format(date3)+" - "+sdf2.format(date4));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //    holder.tvTime.setText(slot.getStartTime() +" - " + slot.getEndTime());
        holder.rbBookSlot.setChecked(lastSelectedPosition == position);

        holder.rbBookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();

                SlotTime = holder.tvTime.getText().toString();
                //Toast.makeText(context, "selected offer is " + holder.tvTime.getText(), Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onItemClickListener.onItemClick(product);
                // SubProductList assignWeight=product.getSubProductList().get(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(SlotList slot);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime,tvTimes;
        //CheckBox cb_book_status;
        RadioButton rbBookSlot;
        RadioGroup priceRadioGroup;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            //cb_book_status = itemView.findViewById(R.id.cb_book_status);
            rbBookSlot=itemView.findViewById(R.id.rb_book_status);




        }
    }


}

