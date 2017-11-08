package com.vnyi.emenu.maneki.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.logger.Logger;
import com.qslib.permission.PermissionUtils;
import com.qslib.util.ProgressDialogUtils;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.response.config.ConfigModel;
import com.vnyi.emenu.maneki.models.response.config.PostIdModel;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.VyniUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Stack;

import static com.qslib.permission.PermissionUtils.REQUEST_CODE_PERMISSION;

/**
 * Created by Hungnd on 11/1/17.
 */

public abstract class BaseActivity extends FragmentActivity {


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


    protected void saveConfigValueLoad(JSONObject configValueObject, boolean isConfirm) {
        try {
            if (configValueObject == null) return;

            List<PostIdModel> postIdModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<PostIdModel>>() {
            });

            if (postIdModels != null && postIdModels.size() > 0) {
                VyniUtils.LogException(TAG, "==> getPosId::" + postIdModels.get(0).getPosId());
                VnyiPreference.getInstance(this).putInt(VnyiApiServices.POST_ID, postIdModels.get(0).getPosId()); // test
            } else {
                VyniUtils.LogException(TAG, "==> getPosId null");
            }

            List<ConfigModel> configModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE_DETAIL), new TypeReference<List<ConfigModel>>() {
            });
            if (configModels != null && configModels.size() > 0) {
                VyniUtils.LogException(TAG, "==> configModels::" + configModels.toString());
                VnyiPreference.getInstance(this).putListObject(VnyiApiServices.CONFIG_VALUE_LOAD, configModels); // test

                // save config value load
                for (ConfigModel configModel : configModels) {

                    VyniUtils.LogException(TAG, "==> configModels::" + configModel.toString());

                    if (configModel.getConfigCode().toUpperCase().equals(VnyiApiServices.KEY_ORG_AUTOID)) { // branch
                        if (configModel.getConfigValue() == null || configModel.getConfigValue().equals("")) {
                            if (configModel.getConfigDefaultValue() == null || configModel.getConfigDefaultValue().equals("")) {
                                VyniUtils.LogException(TAG, "==> branchId error");
                            } else {
                                VnyiPreference.getInstance(this).putInt(VnyiApiServices.BRANCH_ID, Integer.parseInt(configModel.getConfigDefaultValue()));
                                VyniUtils.LogException(TAG, "==> branchId default::" + configModel.getConfigDefaultValue());
                            }
                        } else {
                            VyniUtils.LogException(TAG, "==> branchId::" + configModel.getConfigValue());
                            VnyiPreference.getInstance(this).putInt(VnyiApiServices.BRANCH_ID, Integer.parseInt(configModel.getConfigValue()));
                        }
                    }
                    if (configModel.getConfigCode().toUpperCase().equals(VnyiApiServices.KEY_COUNTER)) { // QUẦY

                        if (configModel.getConfigValue() == null || configModel.getConfigValue().equals("")) {
                            if (configModel.getConfigDefaultValue() == null || configModel.getConfigDefaultValue().equals("")) {
                                VyniUtils.LogException(TAG, "==> counter error");
                            } else {
                                VyniUtils.LogException(TAG, "==> default counter::" + configModel.getConfigDefaultValue());
                                VnyiPreference.getInstance(this).putInt(VnyiApiServices.KEY_STALL, Integer.parseInt(configModel.getConfigDefaultValue()));
                            }
                        } else {
                            VyniUtils.LogException(TAG, "==> counter::" + configModel.getConfigValue());
                            VnyiPreference.getInstance(this).putInt(VnyiApiServices.KEY_STALL, Integer.parseInt(configModel.getConfigValue()));
                        }
                    }
                    if (configModel.getConfigCode().toUpperCase().equals(VnyiApiServices.KEY_AREA)) { // KHU

                        if (configModel.getConfigValue() == null || configModel.getConfigValue().equals("")) {
                            if (configModel.getConfigDefaultValue() == null || configModel.getConfigDefaultValue().equals("")) {
                                VyniUtils.LogException(TAG, "==> area error");

                            } else {
                                VyniUtils.LogException(TAG, "==> area default::" + configModel.getConfigDefaultValue());
                                VnyiPreference.getInstance(this).putInt(VnyiApiServices.AREA_CURRENT, Integer.parseInt(configModel.getConfigDefaultValue()));
                            }
                        } else {
                            VyniUtils.LogException(TAG, "==> area::" + configModel.getConfigValue());
                            VnyiPreference.getInstance(this).putInt(VnyiApiServices.AREA_CURRENT, Integer.parseInt(configModel.getConfigValue()));
                        }
                    }

                }


                if (isConfirm) {

                }
            } else {
                VyniUtils.LogException(TAG, "==> configModels null");
            }

        } catch (JSONException e) {
            VyniUtils.LogException(TAG, e.getMessage());
        }
    }
}
