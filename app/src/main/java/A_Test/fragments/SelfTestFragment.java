package A_Test.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.throtel.grocery.R;

import books.utils.NetworkUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelfTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfTestFragment extends BaseFragment {
    private static final String TAG = SelfTestFragment.class.getSimpleName();
    LinearLayoutManager HorizontalLayout;
    private Context context;
    private int position = 0;
    private View view;

    public static SelfTestFragment newInstance() {
        SelfTestFragment fragment = new SelfTestFragment();
        return fragment;
    }

    public SelfTestFragment() {
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
        view = inflater.inflate(R.layout.fragment_self_test, container, false);


        if (NetworkUtil.getConnectivityStatus(context)) {
        } else
            Toast.makeText(context, context.getString(R.string.error_network), Toast.LENGTH_SHORT).show();


        return view;
    }
}