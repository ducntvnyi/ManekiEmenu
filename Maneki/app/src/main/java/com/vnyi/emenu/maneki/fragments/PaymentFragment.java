package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;

import com.vnyi.emenu.maneki.R;

/**
 * Created by Hungnd on 11/6/17.
 */

public class PaymentFragment extends BaseFragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();


    public static Fragment newInstance() {
        Fragment fragment = new PaymentFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.payment_fragment;
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
