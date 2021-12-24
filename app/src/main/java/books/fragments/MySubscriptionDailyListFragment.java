package books.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;

import java.util.ArrayList;

import books.activity.MySubscriptionDailyOrderDetail;
import books.adapter.MySUbscriptionDailyListAdapter;
import books.models.SubscriptionOrdersList;

public class MySubscriptionDailyListFragment extends BaseFragment implements MySUbscriptionDailyListAdapter.OnItemClickListener {

    private static final String TAG = MySubscriptionDailyListFragment.class.getSimpleName();


    private RecyclerView rvMySubsriptionList;
    public static int REQUEST_CODE = 102;




    public MySubscriptionDailyListFragment() {
        // Required empty public constructor
    }

    public static MySubscriptionDailyListFragment newInstance() {

        MySubscriptionDailyListFragment fragment = new MySubscriptionDailyListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_subscriptions, container, false);

        rvMySubsriptionList = view.findViewById(R.id.rv_my_list);



//        if (NetworkUtil.getConnectivityStatus(context))
//            //getMyList();
//
//        else
//            Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
//

        return view;
    }

    //Product List
//    private void getMyList() {
//       // MyProgress.start(context);
//        Call<MySubscriptionDataResponse> call = RetrofitClient.getRetrofitClient().getMyDailyListData(localData.getCustomerId());
//        call.enqueue(new Callback<MySubscriptionDataResponse>() {
//            @Override
//            public void onResponse(Call<MySubscriptionDataResponse> call, Response<MySubscriptionDataResponse> response) {
//
//                MyProgress.stop();
//                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
//
//                    setUpMyList(response.body().getData().getSubscriptionOrderList());
//                } else {
//                    showToast(response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MySubscriptionDataResponse> call, Throwable t) {
//                MyProgress.stop();
//                Log.d("BTAG", t.getMessage());
//                Toast.makeText(context, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setUpMyList(ArrayList<SubscriptionOrdersList> list) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvMySubsriptionList.setLayoutManager(layoutManager);
        rvMySubsriptionList.setAdapter(new MySUbscriptionDailyListAdapter(context, list, this));
    }

    @Override
    public void onItemClick(SubscriptionOrdersList order) {
        Intent intent = new Intent(context, MySubscriptionDailyOrderDetail.class);
        intent.putExtra("SOrderList", order);
        startActivityForResult(intent, REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_CODE)
            //getMyList();

    }


}
