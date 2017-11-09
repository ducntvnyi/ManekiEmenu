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
import com.qslib.network.NetworkUtils;
import com.qslib.permission.PermissionUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.ProgressDialogUtils;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.config.ConfigModel;
import com.vnyi.emenu.maneki.models.response.config.PostIdModel;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VyniUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        String[] perms = {android.Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return PermissionUtils.requestPermission(activity, REQUEST_CODE_PERMISSION, perms);
    }


    protected void saveConfigValueLoad(JSONObject configValueObject, boolean isConfirm) {
        try {
            if (configValueObject == null) return;
            ConfigValueModel configValueModel = new ConfigValueModel();

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
                    int typeValue = configModel.getTypeValue();

                    switch (typeValue) {
                        case 0:
                            configValueModel.setBranch(configModel);
                            break;
                        case 1:
                            configValueModel.setLoadListParent(configModel);
                            break;
                        case 2:
                            configValueModel.setLinkSaleOff(configModel);
                            break;
                        case 3:
                            configValueModel.setTableName(configModel);
                            break;
                        case 4:
                            configValueModel.setUserOrder(configModel);
                            break;
                        case 5:
                            configValueModel.setLinkUserApp(configModel);
                            break;
                        case 6:
                            configValueModel.setChangeTable(configModel);
                            break;
                        case 7:
                            configValueModel.setNumbTableShow(configModel);
                            break;
                        default:
                            break;
                    }


                }
                VnyiPreference.getInstance(getApplicationContext()).putObject(Constant.KEY_CONFIG_VALUE, configValueModel);


                if (isConfirm) {

                }
            } else {
                VyniUtils.LogException(TAG, "==> configModels null");
            }

        } catch (JSONException e) {
            VyniUtils.LogException(TAG, e.getMessage());
        }
    }

    protected void loadConfigValue() {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String machineId = "584357E3-02BE-406A-962B-51B2D03D1703";  //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_ID);
        String machineName = "Duong Van Chien’s iPad";              //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_NAME);
        String url = VnyiServices.URL_CONFIG;

        VnyiServices.requestGetConfigValue(url, VnyiApiServices.CONFIG_TYPE_VALUE, machineId, machineName, "", new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VyniUtils.LogException(TAG, "==> ConfigValue onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VyniUtils.LogException(TAG, "==> ConfigValue onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VyniUtils.LogException(TAG, "==> ConfigValue onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());
                            // save to local
                            saveConfigValueLoad(configValueObject, false);

                        } catch (JSONException e) {
                            VyniUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VyniUtils.LogException(TAG, "==> ConfigValue onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VyniUtils.LogException(TAG, "==> ConfigValue onFinished ");
            }
        });
    }



    private void confirm() {

    }

    protected void updateConfirm(int postId, int langId, String keyCode, String keyValue) {


        VyniUtils.LogException(TAG, "==> updateConfirm:: keyCode:: " + keyCode + " keyValue::" + keyValue);
        try {
            String url = VnyiPreference.getInstance(getApplicationContext()).getString(Constant.KEY_CONFIG_URL);

            if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

            VnyiServices.requestConfigValueUpdateInfo(url, postId, keyCode, keyValue, langId, new SoapListenerVyni() {

                        @Override
                        public void onStarted() {
                            VyniUtils.LogException(TAG, "==> updateConfirm onStarted ");
                        }

                        @Override
                        public void onSuccess(SoapResponse soapResponse) {

                        }

                        @Override
                        public void onFail(Exception ex) {
                            VyniUtils.LogException(TAG, "==> updateConfirm onFail ");
                        }

                        @Override
                        public void onFinished() {
                            VyniUtils.LogException(TAG, "==> updateConfirm onFinished ");
                        }
                    }
            );
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }
}
