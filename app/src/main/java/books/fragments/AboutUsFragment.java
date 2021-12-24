package books.fragments;

import static books.activity.HomeBookActivity.imgLogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.throtel.grocery.R;

import books.activity.HomeBookActivity;
import books.views.MyProgress;

public class AboutUsFragment extends BaseFragment {

    public static int REQUEST_CODE = 102;


    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        imgLogo.setVisibility(View.GONE);
        // HomeActivity.llSearchView.setVisibility(View.GONE);
        HomeBookActivity.bottomNavigationView.setVisibility(View.VISIBLE);

        MyProgress.start(context);
        WebView browser = (WebView)view. findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.setWebViewClient(new MyBrowser());
        browser.loadUrl("https://www.throtelgrocery.com/application-about-us");

        MyProgress.stop();

        return view;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            MyProgress.stop();
            view.loadUrl(url);
            return true;
        }
    }



}
