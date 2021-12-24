package books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.models.notifications.NotificationList;
import books.utils.Utils;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<NotificationList> rowItems;

    public NotificationListAdapter(Context context, ArrayList<NotificationList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_notification, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        NotificationList notificationList = rowItems.get(position);
        holder.tvDesc.setText(notificationList.getNotificationDesc());
        holder.tvDate.setText(Utils.convertDate_Server_TO_dd_MM_yyyy(notificationList.getNotificationDate(), "dd MMM yy"));
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDesc, tvDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
