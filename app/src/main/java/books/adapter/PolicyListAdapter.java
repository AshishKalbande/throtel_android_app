package books.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import books.models.PolicyList;

public class PolicyListAdapter extends RecyclerView.Adapter<PolicyListAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<PolicyList> rowItems;

    public PolicyListAdapter(Context context, ArrayList<PolicyList> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_policy_list, parent, false);
        return new PolicyListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        PolicyList policy = rowItems.get(position);
        holder.tvPolicyCategory.setText(policy.getPolicyCategory());
        holder.tvPolicyDescription.setText(Html.fromHtml(policy.getPolicyDesc()));

        holder.tvPolicyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.expandableLayout1.isExpanded()) {
                    holder.expandableLayout1.collapse();
                    Drawable img = context.getResources().getDrawable(R.drawable.ic_expand_more_24);
                    img.setBounds(0, 0, 80, 80);
                    holder.tvPolicyCategory.setCompoundDrawablesRelative(null, null, img, null);

                } else {

                    holder.expandableLayout1.expand();
                    Drawable img = context.getResources().getDrawable(R.drawable.ic_expand_less);
                    img.setBounds(0, 0, 80, 80);
                    holder.tvPolicyCategory.setCompoundDrawablesRelative(null, null, img, null);

                }

            }
        });

        holder.expandableLayout1.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvPolicyCategory, tvPolicyDescription;
        private ExpandableLayout expandableLayout1;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPolicyCategory = itemView.findViewById(R.id.tv_policy_category);
            tvPolicyDescription = itemView.findViewById(R.id.tv_policy_description);
            expandableLayout1 = itemView.findViewById(R.id.expandable_layout_1);
        }
    }
}