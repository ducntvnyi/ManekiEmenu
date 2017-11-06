package com.vnyi.emenu.maneki.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.qslib.logger.Logger;
import com.qslib.permission.PermissionUtils;
import com.qslib.util.ProgressDialogUtils;
import com.vnyi.emenu.maneki.utils.VyniUtils;

import java.util.Stack;

import static com.qslib.permission.PermissionUtils.REQUEST_CODE_PERMISSION;

/**
 * Created by Hungnd on 11/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private static final String TAG = BaseActivity.class.getSimpleName();

    protected ProgressDialogUtils progressDialog = null;
    // store fragment in back stack
    public Stack<Fragment> fragments = new Stack<>();

    public abstract void initFragmentDefault();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initFragmentDefault();
    }

    public void showProgressDialog() {
        try {
            // dismiss dialog
            this.progressDialog = new ProgressDialogUtils();
            this.progressDialog.show(this);
        } catch (Exception ex) {
            Logger.e(TAG, ex);
        }
    }


    public void hideProgressDialog() {
        try {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        } catch (Exception ex) {
            Logger.e(TAG, ex);
        }
    }


    public void permissionApp() {
        VyniUtils.LogException(TAG, "==> permissionApp");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(this);
                    return;
                }
            }
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }


    public static boolean requestPermission(Activity activity) {
        String[] perms = {
                android.Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return PermissionUtils.requestPermission(activity, REQUEST_CODE_PERMISSION, perms);
    }
}
