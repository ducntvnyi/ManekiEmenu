package com.vnyi.emenu.maneki.fragments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.config.ConfigModel;
import com.vnyi.emenu.maneki.models.response.config.PostIdModel;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/10/17.
 */

public class BaseDialogFragment extends BaseMainDialogFragment {

    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    protected void saveConfigValueLoad(JSONObject configValueObject, boolean isConfirm) {
        try {
            if (configValueObject == null) return;
            ConfigValueModel configValueModel = new ConfigValueModel();

            List<PostIdModel> postIdModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<PostIdModel>>() {
            });

            if (postIdModels != null && postIdModels.size() > 0) {
                VnyiUtils.LogException(TAG, "==> getPosId::" + postIdModels.get(0).getPosId());
                VnyiPreference.getInstance(getContext()).putInt(VnyiApiServices.POST_ID, postIdModels.get(0).getPosId()); // test
            } else {
                VnyiUtils.LogException(TAG, "==> getPosId null");
            }

            List<ConfigModel> configModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE_DETAIL), new TypeReference<List<ConfigModel>>() {
            });
            if (configModels != null && configModels.size() > 0) {
                VnyiUtils.LogException(TAG, "==> configModels::" + configModels.toString());
//                VnyiPreference.getInstance(getContext()).putListObject(VnyiApiServices.CONFIG_VALUE_LOAD, configModels); // test


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
                            configValueModel.setLinkUserApp(configModel);
                            break;
                        case 4:
                            configValueModel.setUserOrder(configModel);
                            break;
                        case 5:
                            configValueModel.setTableName(configModel);
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
                VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_CONFIG_VALUE, configValueModel);

                VnyiUtils.LogException(TAG, "==> configModels:: " + configValueModel.toString());

                if (isConfirm) {

                }
            } else {
                VnyiUtils.LogException(TAG, "==> configModels null");
            }

        } catch (JSONException e) {
            VnyiUtils.LogException(TAG, e.getMessage());
        }
    }

    protected void loadConfigValue(Consumer<Boolean> booleanConsumer) {

        if (!NetworkUtils.isNetworkAvailable(getContext())) return;

        String machineId = "584357E3-02BE-406A-962B-51B2D03D1703";  //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_ID);
        String machineName = "Duong Van Chien’s iPad";              //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_NAME);
        String url = VnyiUtils.getLinkServer(getContext());
        ;

        VnyiServices.requestGetConfigValue(url, VnyiApiServices.CONFIG_TYPE_VALUE, machineId, machineName, "", new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> ConfigValue onStarted ");
                showDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ConfigValue onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());
                            // save to local
                            saveConfigValueLoad(configValueObject, false);
                            loadBranch();
                            booleanConsumer.accept(true);
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFail " + ex.getMessage());
                booleanConsumer.accept(false);
            }

            @Override
            public void onFinished() {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFinished ");
            }
        });
    }

    protected void loadBranch() {
        String url = VnyiUtils.getLinkServer(getContext());

        if (!NetworkUtils.isNetworkAvailable(getContext())) return;

        VnyiServices.requestConfigValueGetBranch(url, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> loadBranch onStarted ");
                showDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> loadBranch onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ConfigValue onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());
                            // save to local
                            List<Branch> branchList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<Branch>>() {
                            });


                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    } else {

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                dismissDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFinished ");
            }
        });
    }

}
