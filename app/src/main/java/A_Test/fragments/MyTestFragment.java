package A_Test.fragments;

import static books.activity.HomeBookActivity.bottomNavigationView;
import static books.activity.HomeBookActivity.imgLogo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.throtel.grocery.R;

import books.activity.HomeBookActivity;
import books.adapter.OrdersListAdapter;
import books.fragments.BaseFragment;
import books.fragments.OrdersFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTestFragment extends BaseFragment {
    public static int REQUEST_CODE = 102;
    private View view;



    public MyTestFragment() {
        // Required empty public constructor
    }


    public static MyTestFragment newInstance() {
        MyTestFragment fragment = new MyTestFragment();
        return fragment;
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
//        return inflater.inflate(R.layout.fragment_my_test, container, false);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_test, container, false);
//        imgLogo.setVisibility(View.GONE);
        // HomeActivity.llSearchView.setVisibility(View.GONE);
//        bottomNavigationView.setVisibility(View.VISIBLE);



        return view;
    }
}