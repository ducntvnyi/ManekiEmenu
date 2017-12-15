package com.vnyi.emenu.maneki.fragments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.TableModel;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.models.response.TableName;
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

    String tableName = "";

    protected String getTableName(int tableId) {

        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return tableName;
            String url = VnyiServices.URL_CONFIG;

            ConfigValueModel configValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

            if (configValueModel != null && !configValueModel.getLinkServer().equals(""))
                url = configValueModel.getLinkServer();

            VnyiServices.requestConfigValueTableNameById(url, tableId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> getTableName onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> getTableName onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TableName> tableNames = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TableName>>() {
                                });

                                if (tableNames != null || tableNames.size() > 0) {
                                    tableName = tableNames.get(0).getTableName();
                                    VnyiPreference.getInstance(getContext()).putString(Constant.KEY_TABLE_NAME, tableName);

                                }
                            } catch (JSONException e) {
                                VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                            }

                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " getTableName", TAG, e.getMessage());
        }
        return tableName;
    }


    protected void loadTables(ConfigValueModel configValueModel, Consumer<Table> consumer) {

        try {
            int reaAutoId = 4;
            int listType = 1;
            int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestGetTableList(configValueModel.getLinkServer(), reaAutoId, listType, branchId, langId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    showDialog();
                    VnyiUtils.LogException(TAG, "==> loadTables onStarted ");
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> loadTables onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> loadTables onSuccess:: " + soapResponse.toString());

                            List<Table> tables = JacksonUtils.convertJsonToListObject(soapResponse.getResult(), Table.class);
                            if (tables == null) return;
                            consumer.accept(tables.get(0));
                            TableModel tableModel = new TableModel();
                            tableModel.setTables(tables);
                            VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_LIST_TABLE, tableModel);
                            // save to local
                            VnyiUtils.LogException(TAG, "==> tables" + tables.toString());

                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> loadTables onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> loadBloadTablesranch onFinished ");
                }
            });
        } catch (NumberFormatException e) {
            VnyiUtils.LogException(getContext(), " loadTables", TAG, e.getMessage());
        }
    }

    /**
     * update config app ( chi nhanh - ten ban -...)
     *
     * @param linkServer
     * @param keyCode
     * @param keyValue
     */
    protected void updateConfirm(String linkServer, String keyCode, String keyValue) {

        VnyiUtils.LogException(TAG, "==> updateConfirm:: keyCode:: " + keyCode + " keyValue::" + keyValue);

        int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
        int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestConfigValueUpdateInfo(linkServer, keyCode, keyValue, langId, posId, new SoapListenerVyni() {

                        @Override
                        public void onStarted() {
                            VnyiUtils.LogException(TAG, "==> updateConfirm onStarted ");
                        }

                        @Override
                        public void onSuccess(SoapResponse soapResponse) {

                            VnyiUtils.LogException(TAG, "==> updateConfirm onSuccess ");
                        }

                        @Override
                        public void onFail(Exception ex) {

                            VnyiUtils.LogException(TAG, "==> updateConfirm onFail ");
                        }

                        @Override
                        public void onFinished() {
                            VnyiUtils.LogException(TAG, "==> updateConfirm onFinished ");
                        }
                    }
            );
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " updateConfirm", TAG, e.getMessage());
        }
    }


}
