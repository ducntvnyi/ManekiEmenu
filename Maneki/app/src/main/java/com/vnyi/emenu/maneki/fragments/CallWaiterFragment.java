package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;

import com.vnyi.emenu.maneki.R;

/**
 * Created by Hungnd on 11/6/17.
 */

public class CallWaiterFragment extends BaseFragment {

    private static final String TAG = CallWaiterFragment.class.getSimpleName();


    public static Fragment newInstance() {
        Fragment fragment = new CallWaiterFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.call_waiter_fragment;
    }

    @Override
    public void initViews() {
        // onListener

    }

    @Override
    public void initData() {
        // new ArrayList<>
        DialogCallWaiterFragment.newInstance().show(getFragmentManager(), "");
    }
}
