package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import butterknife.BindView;

/**
 * Created by Hungnd on 11/6/17.
 */

public class UseAppFragment extends BaseFragment {

    private static final String TAG = UseAppFragment.class.getSimpleName();
    private static final String URL = "http://www.vnyi.com/";

    @BindView(R.id.webView)
    WebView webView;

    private ConfigValueModel mConfigValueModel;

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
        try {
            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            // onListener
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            if (mConfigValueModel == null) return;
            webView.loadUrl(mConfigValueModel.getLinkUserApp().getConfigDefaultValue());
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " initViews", TAG, e.getMessage());
        }

    }

    @Override
    public void initData() {
        // new ArrayList<>130M tiễn lãi 60Tháng + (2% lãi gốc)
    }
}
