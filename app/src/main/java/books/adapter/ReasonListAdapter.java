package books.adapter;

import static books.activity.BaseActivity.Reason;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;
import java.util.List;

import books.models.ReasonList;

public class ReasonListAdapter extends RecyclerView.Adapter<ReasonListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ReasonList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private   Double TotalPrice=0.0;
    List<String> lables;
    private int lastSelectedPosition = -1;


    public ReasonListAdapter(Context context, ArrayList<ReasonList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_reason_list, parent, false);
        return new ReasonListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final ReasonList reason = rowItems.get(position);

        holder.tvName.setText(reason.getReason());


        holder.rbBookSlot.setChecked(lastSelectedPosition == position);

        holder.rbBookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();

                Reason=holder.tvName.getText().toString();
                //Toast.makeText(context, "selected Reaason is " + holder.tvName.getText(), Toast.LENGTH_LONG).show();
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
        public void onItemClick(ReasonList slot);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        //CheckBox cb_book_status;
        RadioButton rbBookSlot;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            //cb_book_status = itemView.findViewById(R.id.cb_book_status);
            rbBookSlot=itemView.findViewById(R.id.rb_book_status);




        }
    }


}

