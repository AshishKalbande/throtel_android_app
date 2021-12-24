package books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> rowItems;

    public ImageSliderAdapter(Context context, ArrayList<String> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View myImageLayout = inflater.inflate(R.layout.row_adds, view, false);

        ImageView ivAddsImage = myImageLayout.findViewById(R.id.image);

        Picasso.with(context)
                .load(rowItems.get(position)) //Load the image
                .fit()
                .placeholder(R.drawable.no_preview)
                .into(ivAddsImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

