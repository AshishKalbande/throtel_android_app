package com.throtel.grocery.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.throtel.grocery.R;
import com.throtel.grocery.adapter.MySubscriptionsTabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class MySubscriptionsTabsActivity extends BaseActivity {
    private static final String TAG = ViewCartTabsActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int REQUEST_CODE = 102;
    MySubscriptionsTabsAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcart_tabs_list_activity);

        setUpToolbarBackButton("My Subscriptions");


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);



        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new MySubscriptionsTabsAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            // getMyNoticeList();
            viewPager.getAdapter().notifyDataSetChanged();

    }


}
