package com.throtel.grocery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.throtel.grocery.R;
import com.throtel.grocery.api.RetrofitClient;
import com.throtel.grocery.models.BannerList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddsAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BannerList> rowItems;

    public AddsAdapter(Context context, ArrayList<BannerList> rowItems) {
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

        ImageView ivAddsImage = (ImageView) myImageLayout.findViewById(R.id.image);

        String url = RetrofitClient.BASE_BANNER_IMAGE_URL + rowItems.get(position).getBannerImage();
        Log.d("BTAG","URL IMAGE.."+url);

        Picasso.with(context)
                .load(url) //Load the image
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
