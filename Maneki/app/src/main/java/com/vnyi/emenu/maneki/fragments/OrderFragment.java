package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;

import com.vnyi.emenu.maneki.R;

/**
 * Created by Hungnd on 11/6/17.
 */

public class OrderFragment extends BaseFragment {

    private static final String TAG = OrderFragment.class.getSimpleName();


    public static Fragment newInstance() {
        Fragment fragment = new OrderFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.order_fragment;
    }

    @Override
    public void initViews() {
        // onListener

    }

    @Override
    public void initData() {
        // new ArrayList<>
    }
}
