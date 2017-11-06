package com.vnyi.emenu.maneki.fragments;


import android.support.v4.app.Fragment;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.utils.Constant;

import butterknife.OnClick;

public class MenuFragment extends BaseFragment {


    public static Fragment newInstance() {
        Fragment fragment = new MenuFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.menu_fragment;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.ivShowConfig)
    void onClickShowConfig() {
        mActivity.changeTab(Constant.INDEX_CONFIG);
    }

    @OnClick(R.id.btnViewOrder)
    void onClickViewOrder() {
        DialogConfirmOrderFragment.newInstance().show(getFragmentManager(), "");
    }
}
