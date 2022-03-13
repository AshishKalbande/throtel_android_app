package A_Test.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;

import java.util.ArrayList;

import A_Test.model.BannerData;
import books.models.OffersListResponse;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<BannerData> rowItems;
    private LayoutInflater layoutInflater;

    public BannerAdapter(Context context, ArrayList<BannerData> rowItems, LayoutInflater layoutInflater) {
        this.context = context;
        this.rowItems = rowItems;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public int getCount() {
        return rowItems == null ? 0 : rowItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View myImageLayout = inflater.inflate(R.layout.row_adds, container, false);

//        String url = rowItems.get(position);
//        Picasso.
//
//        ImageView ivAddsImage = (ImageView) myImageLayout.findViewById(R.id.image);
        return super.instantiateItem(container, position);
    }
}
