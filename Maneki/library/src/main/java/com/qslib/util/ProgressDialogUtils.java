package com.qslib.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogUtils {
    // variable
    private static ProgressDialog progressDialog = null;
    // default style
    private int progressStyle = android.app.ProgressDialog.STYLE_SPINNER;
    private String message;

    /**
     * get instance of progress dialog
     *
     * @return
     */
    public static ProgressDialogUtils getInstance() {
        return new ProgressDialogUtils();
    }

    /**
     * @param progressStyle the progressStyle to set
     */
    public ProgressDialogUtils setProgressStyle(int progressStyle) {
        this.progressStyle = progressStyle;
        return this;
    }

    /**
     * set message
     *
     * @param message
     * @return
     */
    public ProgressDialogUtils setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * set progress bar
     *
     * @param percent
     */
    public ProgressDialogUtils setProgressBar(int percent) {
        try {
            if (progressDialog != null) progressDialog.setProgress(percent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return this;
    }

    /**
     * show progress bar
     *
     * @param context
     */
    public void show(Activity context) {
        try {
            dismiss();
            // init new instance
            progressDialog = new ProgressDialog(context);
            if (!StringUtils.isEmpty(message)) progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(progressStyle);
            progressDialog.setProgress(0);
            if (!ActivityUtils.isFinish(context)) progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dismiss dialog
     */
    public void dismiss() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
