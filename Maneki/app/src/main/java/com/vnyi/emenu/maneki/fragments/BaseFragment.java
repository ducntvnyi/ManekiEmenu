package com.vnyi.emenu.maneki.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.util.ProgressDialogUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.activities.MainActivity;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Hungnd on 11/1/17.
 */

public abstract class BaseFragment extends Fragment {

    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected ProgressDialogUtils progressDialog = null;
    protected MainActivity mActivity;
    protected Context mContext;

    @Nullable
    @BindView(R.id.rootView)
    View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            LanguageUtils.configLanguage(mActivity, VnyiUtils.getLanguageApp(mActivity));
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(getFragmentLayoutId(), container, false);
        ButterKnife.bind(this, this.rootView);
        initViews();
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            this.mActivity = (MainActivity) getActivity();

//            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * show dialog
     */
    protected void showDialog() {
        try {
            // dismiss dialog
            dismissDialog();
            progressDialog = new ProgressDialogUtils();
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show(mActivity);
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    /**
     * show toast mesage
     *
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(mActivity, msg);
    }

    public abstract int getFragmentLayoutId();

    public abstract void initViews();

    public abstract void initData();

}
