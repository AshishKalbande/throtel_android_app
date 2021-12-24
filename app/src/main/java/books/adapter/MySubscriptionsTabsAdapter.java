package books.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import books.fragments.MySubscriptionDailyListFragment;
import books.fragments.MySubscriptionMonthlyListFragment;

public class MySubscriptionsTabsAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MySubscriptionsTabsAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MySubscriptionDailyListFragment daily = new MySubscriptionDailyListFragment();
                return daily;
            case 1:
                MySubscriptionMonthlyListFragment monthly = new MySubscriptionMonthlyListFragment();
                return monthly;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}