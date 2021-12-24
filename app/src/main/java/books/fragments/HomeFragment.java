package books.fragments;

import static books.fragments.CategoryFragment.REQUEST_CODE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.throtel.grocery.R;
import books.activity.GroupWiseCategoryActivity;
import books.activity.ProductDetailsActivity;
import books.adapter.AddsAdapter;
import books.adapter.CategoryOffersAdapter;
import books.adapter.DashboardCategoryListAdapter;
import books.adapter.DashboardDailyNeedsAdapter;
import books.adapter.GlobalOffersAdapter;
import books.api.RetrofitClient;
import books.fragments.BaseFragment;
import books.models.BannerList;
import books.models.CategoryList;
import books.models.CategoryOfferList;
import books.models.DashbordDataResponse;
import books.models.GlobalOfferList;
import books.models.ProductList;
import books.utils.NetworkUtil;
import books.views.MyProgress;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements DashboardCategoryListAdapter.OnItemClickListener, DashboardDailyNeedsAdapter.OnItemClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    RecyclerView rv_DailyNeeds_List;
    RecyclerView rv_Category_List, rvGlobalOffers, rvCategoryOffers;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    private ViewPager viewPagerAdds;
    private CircleIndicator circleIndicator;
    private Context context;
    private int position = 0;
    private View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homes, container, false);

       // imgLogo.setVisibility(View.VISIBLE);
        viewPagerAdds = view.findViewById(R.id.view_pager_adds);
        circleIndicator = view.findViewById(R.id.indicator);
        rv_DailyNeeds_List = view.findViewById(R.id.rv_dailyneeds_list);
        rv_Category_List = view.findViewById(R.id.rv_category_list);
        rvGlobalOffers = view.findViewById(R.id.rv_global_offers);
        rvCategoryOffers = view.findViewById(R.id.rv_category_offers);


        if (NetworkUtil.getConnectivityStatus(context)) {
            getDashboardData();
           // getOffers();
        } else
            Toast.makeText(context, context.getString(R.string.error_network), Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewCartList(view);
    }

//    private void getOffers() {
//        RetrofitClient.getRetrofitClient().getOffers(
//                localData.getCustomerId()).enqueue(new Callback<OffersListResponse>() {
//            @Override
//            public void onResponse(Call<OffersListResponse> call, Response<OffersListResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getStatus().equalsIgnoreCase("true")) {
//                        setUpCategoryOffers(response.body().getData().getCategoryOfferList());
//                        setUpGlobalOffers(response.body().getData().getGlobalOfferList());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OffersListResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d(TAG, t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getDashboardData() {
        MyProgress.start(context);

        Log.d("BTAG", "CUSTOMER ID..." + localData.getCustomerId());
        Call<DashbordDataResponse> call = RetrofitClient.getRetrofitClient().getDashBoardData(localData.getCustomerId());

        call.enqueue(new Callback<DashbordDataResponse>() {
            @Override
            public void onResponse(Call<DashbordDataResponse> call, Response<DashbordDataResponse> response) {
                MyProgress.stop();

                if (response.body() != null) {
                    setUpAdds(response.body().getData().getBannerList());
                    setUpDailyNeedsList(response.body().getData().getProductList());
                   // setUpGroupList(response.body().getData().getGroupList());

                } else {
                    Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashbordDataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d(TAG, t.getMessage());
                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpAdds(final ArrayList<BannerList> bannerLists) {

        AddsAdapter addsAdapter = new AddsAdapter(context, bannerLists);
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

    private void setUpDailyNeedsList(ArrayList<ProductList> productList) {

        //======== Category List ==============
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        // Set LayoutManager on Recycler View
        rv_DailyNeeds_List.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        rv_DailyNeeds_List.setLayoutManager(HorizontalLayout);

        rv_DailyNeeds_List.setAdapter(new DashboardDailyNeedsAdapter(context, productList, this));
    }

    private void setUpGroupList(ArrayList<CategoryList> categoryList) {

        //======== Category List ==============
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        // Set LayoutManager on Recycler View
        rv_Category_List.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        rv_Category_List.setLayoutManager(HorizontalLayout);

        rv_Category_List.setAdapter(new DashboardCategoryListAdapter(context, categoryList, this));
    }

    private void setUpGlobalOffers(ArrayList<GlobalOfferList> globalOffers) {

        //======== Category List ==============
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        // Set LayoutManager on Recycler View
        rvGlobalOffers.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        rvGlobalOffers.setLayoutManager(HorizontalLayout);

        rvGlobalOffers.setAdapter(new GlobalOffersAdapter(context, globalOffers));
        rvGlobalOffers.setLayoutFrozen(true);
    }

    private void setUpCategoryOffers(ArrayList<CategoryOfferList> categoryOfferLists) {

        //======== Category List ==============
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        // Set LayoutManager on Recycler View
        rvCategoryOffers.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        rvCategoryOffers.setLayoutManager(HorizontalLayout);

        rvCategoryOffers.setAdapter(new CategoryOffersAdapter(context, categoryOfferLists));
        rvCategoryOffers.setLayoutFrozen(true);
    }


    //Category Item CLick
    @Override
    public void onItemClick(CategoryList category) {

        Intent intent = new Intent(context, GroupWiseCategoryActivity.class);
        intent.putExtra("GroupList", category);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //======= Daily needs Item click ============
    @Override
    public void onItemClick(ProductList product) {

        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("ProductDetails", product);
        startActivityForResult(intent, REQUEST_CODE);

    }

    //Product List


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            getViewCartList(view);

    }


}