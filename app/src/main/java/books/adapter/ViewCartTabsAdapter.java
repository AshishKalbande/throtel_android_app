package books.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import books.fragments.RegularViewCartListFragment;

public class ViewCartTabsAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public ViewCartTabsAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RegularViewCartListFragment regular = new RegularViewCartListFragment();
                return regular;
//            case 1:
//                BulkViewCartListFragment bulk = new BulkViewCartListFragment();
//                return bulk;

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