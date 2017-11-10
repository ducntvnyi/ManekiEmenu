package com.qslib.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qslib.library.R;
import com.qslib.util.ProgressDialogUtils;

/**
 * Created by Dang on 6/23/2016.
 */
public abstract class BaseMainDialogFragment extends DialogFragment {

    protected ProgressDialogUtils progressDialog = null;
    private FragmentActivity mActivity;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            getDialog().getWindow().setGravity(Gravity.CENTER);
            getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mActivity = getActivity();
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, attributes.height);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {

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

        }
    }
}
