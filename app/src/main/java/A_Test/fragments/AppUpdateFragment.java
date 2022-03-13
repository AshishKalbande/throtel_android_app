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
 * create an instance of this fragment.
 */
public class AppUpdateFragment extends BaseFragment {
    private static final String TAG = AppUpdateFragment.class.getSimpleName();
    LinearLayoutManager HorizontalLayout;
    private Context context;
    private int position = 0;
    private View view;

    public static AppUpdateFragment newInstance() {
        AppUpdateFragment fragment = new AppUpdateFragment();
        return fragment;
    }

    public AppUpdateFragment() {
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
            view = inflater.inflate(R.layout.fragment_app_update, container, false);


            if (NetworkUtil.getConnectivityStatus(context)) {
            } else
                Toast.makeText(context, context.getString(R.string.error_network), Toast.LENGTH_SHORT).show();


            return view;
    }
}