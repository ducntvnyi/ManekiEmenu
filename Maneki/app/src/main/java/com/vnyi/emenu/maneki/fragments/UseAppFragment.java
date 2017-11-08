package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.vnyi.emenu.maneki.R;

import butterknife.BindView;

/**
 * Created by Hungnd on 11/6/17.
 */

public class UseAppFragment extends BaseFragment {

    private static final String TAG = UseAppFragment.class.getSimpleName();
    private static final String URL = "http://www.vnyi.com/";

    @BindView(R.id.webView)
    WebView webView;

    public static Fragment newInstance() {
        Fragment fragment = new UseAppFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.sale_off_fragment;
    }

    @Override
    public void initViews() {
        // onListener
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(URL);

    }

    @Override
    public void initData() {
        // new ArrayList<>
    }
}
