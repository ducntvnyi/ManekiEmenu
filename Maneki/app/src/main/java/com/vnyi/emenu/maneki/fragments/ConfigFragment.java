package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17.
 */

public class ConfigFragment extends BaseFragment {

    private static final String TAG = ConfigFragment.class.getSimpleName();


    public static Fragment newInstance() {
        Fragment fragment = new ConfigFragment();

        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.config_fragment;
    }

    @Override
    public void initViews() {
        // onListener

    }

    @Override
    public void initData() {
        // new ArrayList<>
    }


    @OnClick(R.id.ivShowConfig)
    void onClickConfigHome() {
        // check config: true => go to.. : false: go to configScreen
        try {
            DialogConfigFragment.newInstance().setConsumerConfigValue(isConfigValue -> {
                Log.e(TAG, "==> onClickConfigHome:: " + isConfigValue);
                mActivity.loadConfigValue();
            }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "onClickConfigHome", TAG, e.getMessage());
        }
    }
}
