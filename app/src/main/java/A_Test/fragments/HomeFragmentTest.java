package A_Test.fragments;

import static books.fragments.CategoryFragment.REQUEST_CODE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import A_Test.Adapters.AddsAdapter;
import A_Test.Api.RetrofitClient;
import A_Test.model.BannerData;
import A_Test.model.BannerListDataResponse;
import A_Test.model.BannerLists;
import A_Test.model.BatchDataResponse;
import books.activity.GroupWiseCategoryActivity;
import books.activity.ProductDetailsActivity;
import books.adapter.CategoryOffersAdapter;
import books.adapter.DashboardCategoryListAdapter;
import books.adapter.DashboardDailyNeedsAdapter;
import books.adapter.GlobalOffersAdapter;
import books.fragments.HomeFragment;
import books.models.BannerList;
import books.models.CategoryList;
import books.models.CategoryOfferList;
import books.models.DashbordDataResponse;
import books.models.GlobalOfferList;
import books.models.ProductList;
import books.utils.NetworkUtil;
import books.views.MyProgress;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentTest extends BaseFragment {

    private static final String TAG = HomeFragmentTest.class.getSimpleName();
//    RecyclerView rv_DailyNeeds_List;
//    RecyclerView rv_Category_List, rvGlobalOffers, rvCategoryOffers;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    private ViewPager viewPagerAdds;
    private CircleIndicator circleIndicator;
    private Context context;
    private int position = 0;
    private View view;

    public static HomeFragmentTest newInstance() {
        HomeFragmentTest fragment = new HomeFragmentTest();
        return fragment;
    }

    public HomeFragmentTest() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_test, container, false);

//         imgLogo.setVisibility(View.VISIBLE);
        viewPagerAdds = view.findViewById(R.id.view_pager_adds);
        circleIndicator = view.findViewById(R.id.indicator);
//        rv_DailyNeeds_List = view.findViewById(R.id.rv_dailyneeds_list);
//        rv_Category_List = view.findViewById(R.id.rv_category_list);
//        rvGlobalOffers = view.findViewById(R.id.rv_global_offers);
//        rvCategoryOffers = view.findViewById(R.id.rv_category_offers);

        if (NetworkUtil.getConnectivityStatus(context)) {
            getBannerData();
        } else
            Toast.makeText(context, context.getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        return view;
    }


    private void getBannerData() {
        MyProgress.start(context);

        Log.d("BTAG", "CUSTOMER ID..." + localData.getCustomerId());
        Call<BannerListDataResponse> call = RetrofitClient.getRetrofitClient().getBannerData();

        call.enqueue(new Callback<BannerListDataResponse>() {
            @Override
            public void onResponse(Call<BannerListDataResponse> call, Response<BannerListDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null) {

                    setUpAdds(response.body().getData().getBannerList());
//                    showToast(response.body().getData().getBannerList().toString());

                } else {
                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BannerListDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpAdds(final ArrayList<BannerData> bannerLists) {

        A_Test.Adapters.AddsAdapter addsAdapter = new AddsAdapter(context, bannerLists);
        viewPagerAdds.setAdapter(addsAdapter);
        circleIndicator.setViewPager(viewPagerAdds);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (position == bannerLists.size()) {
                    position = 0;
                }
                viewPagerAdds.setCurrentItem(position++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);
    }


}