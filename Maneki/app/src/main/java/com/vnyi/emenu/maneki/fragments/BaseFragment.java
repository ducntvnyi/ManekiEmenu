package com.vnyi.emenu.maneki.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qslib.util.KeyboardUtils;
import com.qslib.util.LanguageUtils;
import com.qslib.util.ProgressDialogUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.utils.VyniUtils;


/**
 * Created by Hungnd on 11/1/17.
 */

public class BaseFragment extends Fragment{

    private static final String TAG = BaseFragment.class.getSimpleName();
    protected ProgressDialogUtils progressDialog = null;
    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            LanguageUtils.configLanguage(mActivity, VyniUtils.getLanguageApp(mActivity));
        } catch (Exception e) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mActivity = activity;
            KeyboardUtils.hideSoftKeyboard(mActivity);
        } catch (Exception e) {

        }
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
        } catch (Exception ex) {

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
        } catch (Exception ex) {

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
}
