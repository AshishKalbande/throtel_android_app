package books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;
import books.api.RetrofitClient;
import books.models.CategoryList;

import java.util.ArrayList;

public class GroupWiseListAdapter extends RecyclerView.Adapter<books.adapter.GroupWiseListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<CategoryList> rowItems;
    private books.adapter.GroupWiseListAdapter.OnItemClickListener onItemClickListener;

    public GroupWiseListAdapter(Context context, ArrayList<CategoryList> rowItems, books.adapter.GroupWiseListAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public books.adapter.GroupWiseListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_category_list, parent, false);
        return new books.adapter.GroupWiseListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull books.adapter.GroupWiseListAdapter.ItemViewHolder holder, int position) {

        final CategoryList category = rowItems.get(position);

        holder.tvName.setText(category.getMainCategoryName());

        String url = RetrofitClient.BASE_GROUP_WISE_IMAGE_URL + category.getMainCategoryImage();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(CategoryList category);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivImage=itemView.findViewById(R.id.iv_img);

        }
    }
}
