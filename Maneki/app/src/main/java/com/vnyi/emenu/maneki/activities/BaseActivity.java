package com.vnyi.emenu.maneki.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.logger.Logger;
import com.qslib.network.NetworkUtils;
import com.qslib.permission.PermissionUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.ProgressDialogUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.BranchModel;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.ItemCategoryDetailModel;
import com.vnyi.emenu.maneki.models.NoTicketModel;
import com.vnyi.emenu.maneki.models.TableModel;
import com.vnyi.emenu.maneki.models.TicketModel;
import com.vnyi.emenu.maneki.models.UserModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.models.response.TableName;
import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;
import com.vnyi.emenu.maneki.models.response.UserOrder;
import com.vnyi.emenu.maneki.models.response.config.ConfigModel;
import com.vnyi.emenu.maneki.models.response.config.PostIdModel;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Stack;

import java8.util.function.Consumer;

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

    }

    public void showProgressDialog() {
        try {
            // dismiss dialog
            this.progressDialog = new ProgressDialogUtils();
            this.progressDialog.show(BaseActivity.this);
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
        VnyiUtils.LogException(TAG, "==> permissionApp");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(this);
                    return;
                }
            }
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }


    public static boolean requestPermission(Activity activity) {
        String[] perms = {android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BODY_SENSORS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return PermissionUtils.requestPermission(activity, REQUEST_CODE_PERMISSION, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initFragmentDefault();
                } else {
                    ToastUtils.showToast(getApplicationContext(), getString(R.string.failed_grant_permisson));
                }
            }
        }
    }

    protected void saveConfigValueLoad(JSONObject configValueObject, boolean isConfirm) {
        try {
            if (configValueObject == null) return;
            ConfigValueModel configValueModel = new ConfigValueModel();
            configValueModel.setLinkServer(VnyiApiServices.URL_CONFIG);

            List<PostIdModel> postIdModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<PostIdModel>>() {
            });

            if (postIdModels != null && postIdModels.size() > 0) {
                VnyiUtils.LogException(TAG, "==> getPosId::" + postIdModels.get(0).getPosId());
                VnyiPreference.getInstance(this).putInt(VnyiApiServices.POST_ID, postIdModels.get(0).getPosId()); // test
            } else {
                VnyiUtils.LogException(TAG, "==> getPosId null");
            }

            List<ConfigModel> configModels = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE_DETAIL), new TypeReference<List<ConfigModel>>() {
            });
            if (configModels != null && configModels.size() > 0) {
                VnyiUtils.LogException(TAG, "==> configModels::" + configModels.toString());
//                VnyiPreference.getInstance(this).putListObject(VnyiApiServices.CONFIG_VALUE_LOAD, configModels); // test

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
                            configValueModel.setTableName(configModel);
                            break;
                        case 5:
                            configValueModel.setUserOrder(configModel);
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
                new LoadDataConfigTask().execute(configValueModel);

            } else {
                VnyiUtils.LogException(TAG, "==> configModels null");
            }

        } catch (JSONException e) {
            VnyiUtils.LogException(TAG, e.getMessage());
        }
    }

    public void loadConfigValue() {


        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String machineId = "584357E3-02BE-406A-962B-51B2D03D1703";  //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_ID);
        String machineName = "Duong Van Chien’s iPad";              //AppPreferences.getInstance(getApplicationContext()).getString(VnyiApiServices.MACHINE_NAME);
        String url = VnyiServices.URL_CONFIG;

        ConfigValueModel configValueModel = VnyiPreference.getInstance(getApplicationContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

        if (configValueModel != null && !configValueModel.getLinkServer().equals(""))
            url = configValueModel.getLinkServer();

        Log.d(TAG, "==> url:: " + url);
        VnyiServices.requestGetConfigValue(url, VnyiApiServices.CONFIG_TYPE_VALUE, machineId, machineName, "", new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> ConfigValue onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ConfigValue onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());
                            // save to local
                            saveConfigValueLoad(configValueObject, false);

                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ConfigValue onFinished ");
            }
        });
    }

    protected void loadBranch(String url) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        VnyiServices.requestConfigValueGetBranch(url, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> loadBranch onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadBranch onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> loadBranch onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<Branch> branchList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<Branch>>() {
                            });
                            // save to local
                            VnyiUtils.LogException(TAG, "==> branchList" + branchList.toString());
                            BranchModel branchModel = new BranchModel();
                            branchModel.setBranches(branchList);
                            VnyiPreference.getInstance(getApplicationContext()).putObject(Constant.KEY_LIST_BRANCH, branchModel);
                            VnyiUtils.LogException(TAG, "==> BranchModel:: " + VnyiPreference.getInstance(getApplicationContext()).getObject(Constant.KEY_LIST_BRANCH, BranchModel.class));

                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadBranch onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadBranch onFinished ");
            }
        });
    }

    protected void loadListUser(ConfigValueModel configValueModel) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        int branchId = 1942; // Integer.parseInt(configValueModel.getBranch().getConfigValue());

        VnyiServices.requestConfigValueUserOrder(configValueModel.getLinkServer(), branchId, 1, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> loadTables onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadTables onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> loadTables onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<UserOrder> userOrders = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<UserOrder>>() {
                            });

                            UserModel userModel = new UserModel();
                            userModel.setUserOrders(userOrders);
                            VnyiPreference.getInstance(getApplicationContext()).putObject(Constant.KEY_LIST_USER_ORDER, userModel);

                            // save to local
                            VnyiUtils.LogException(TAG, "==> mUserOrders" + userOrders.toString());

                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadTables onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadBloadTablesranch onFinished ");
            }
        });
    }

    protected void loadTables(ConfigValueModel configValueModel) {

        int reaAutoId = 4;
        int listType = 1;
        int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
        int langId = 1;

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        VnyiServices.requestGetTableList(configValueModel.getLinkServer(), reaAutoId, listType, branchId, langId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
//                showProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadTables onStarted ");
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
//                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadTables onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> loadTables onSuccess:: " + soapResponse.toString());

                        List<Table> tables = JacksonUtils.convertJsonToListObject(soapResponse.getResult(), Table.class);
                        if (tables == null) return;
                        TableModel tableModel = new TableModel();
                        tableModel.setTables(tables);
                        VnyiPreference.getInstance(getApplicationContext()).putObject(Constant.KEY_LIST_TABLE, tableModel);
                        // save to local
                        VnyiUtils.LogException(TAG, "==> tables" + tables.toString());

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
//                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadTables onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
//                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> loadBloadTablesranch onFinished ");
            }
        });
    }

    private class LoadDataConfigTask extends AsyncTask<ConfigValueModel, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            VnyiUtils.LogException("LoadDataConfigTask", "==> onPostExecute");
            showProgressDialog();

        }

        @Override
        protected Void doInBackground(ConfigValueModel... configValueModels) {
            VnyiUtils.LogException("LoadDataConfigTask", "==> doInBackground");
            ConfigValueModel configValueModel = configValueModels[0];
            loadBranch(configValueModel.getLinkServer());
            loadListUser(configValueModel);
            loadTables(configValueModel);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hideProgressDialog();
            VnyiUtils.LogException("ConfirmConfigTask", "==> onPostExecute");
        }
    }

    protected void getTableName(int tableId, Consumer<TableName> consumer) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String url = VnyiServices.URL_CONFIG;

        ConfigValueModel configValueModel = VnyiPreference.getInstance(getApplicationContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

        if (configValueModel != null && !configValueModel.getLinkServer().equals(""))
            url = configValueModel.getLinkServer();

        VnyiServices.requestConfigValueTableNameById(url, tableId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> getTableName onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> getTableName onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> getTableName onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<TableName> tableNames = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TableName>>() {
                            });

                            if (tableNames == null || tableNames.size() == 0) {
                                consumer.accept(null);
                            } else {
                                VnyiUtils.LogException(TAG, "==> tableNames" + tableNames.toString());
                                TableName tableName = tableNames.get(0);
                                consumer.accept(tableName);
                            }
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> getTableName onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> getTableName onFinished ");
            }
        });
    }

    /**
     * menu
     * create bill or get bill
     *
     * @param configValueModel
     * @param ticketId
     * @param userId
     * @param langId
     * @paramconsumer
     */
    public void ticketLoadInfo(ConfigValueModel configValueModel, int ticketId, int userId, int langId) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String url = VnyiServices.URL_CONFIG;
        int tableId = 0;

        if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
            url = configValueModel.getLinkServer();
            tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());
        }

        VnyiServices.requestTicketLoadInfo(url, ticketId, tableId, userId, langId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<TicketLoadInfo> ticketLoadInfoList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketLoadInfo>>() {
                            });
                            if (ticketLoadInfoList != null && ticketLoadInfoList.size() > 0) {

                                TicketModel ticketModel = new TicketModel();
                                ticketModel.setLangId(langId);
                                ticketModel.setTicketId(ticketId);
                                ticketModel.setUserId(userId);
                                ticketModel.setTableId(ticketLoadInfoList.get(0).getTableID());
                                VnyiPreference.getInstance(getApplicationContext()).putObject(Constant.KEY_TICKET, ticketModel);

//                                consumer.accept(ticketLoadInfoList.get(0));
                            }
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFinished ");
            }
        });
    }

    /**
     * Load Menu Left
     *
     * @param configValueModel
     * @param ticketId
     * @param posId
     * @param langId
     * @param consumer
     */
    public void getListItemCategoryNoTicket(ConfigValueModel configValueModel, int ticketId, int posId, int langId, Consumer<NoTicketModel> consumer) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String url = VnyiServices.URL_CONFIG;
        int tableId = 0;
        int branchId = 0;

        if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
            url = configValueModel.getLinkServer();
            tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());
            branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
        }

        VnyiServices.requestGetListItemCategoryNoTicket(url, ticketId, tableId, posId, langId, branchId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<ItemCategoryNoListNote> categoryNoListNotes = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<ItemCategoryNoListNote>>() {
                            });

                            if (categoryNoListNotes != null) {
                                NoTicketModel noTicketModel = new NoTicketModel();
                                noTicketModel.setItemCategoryNoListNotes(categoryNoListNotes);
                                consumer.accept(noTicketModel);
                            }
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFinished ");
            }
        });
    }

    /**
     * load Menu right
     *
     * @param configValueModel
     * @param postMasterPage
     * @param categoryId
     * @param ticketId
     * @param langId
     * @param objId
     * @param posId
     * @param consumer
     */
    public void getListItemCategoryDetail(ConfigValueModel configValueModel, boolean postMasterPage, int categoryId,
                                          int ticketId, int langId, int objId, int posId, Consumer<ItemCategoryDetailModel> consumer) {

        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

        String url = VnyiServices.URL_CONFIG;


        if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
            url = configValueModel.getLinkServer();
        }

        VnyiServices.requestGetItemCategoryDetail(url, postMasterPage, categoryId, ticketId, langId, objId, posId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onStarted ");
                showProgressDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<ItemCategoryDetail> itemCategoryDetails = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<ItemCategoryDetail>>() {
                            });

                            if (itemCategoryDetails != null) {
                                ItemCategoryDetailModel categoryDetailModel = new ItemCategoryDetailModel();
                                categoryDetailModel.setItemCategoryDetails(itemCategoryDetails);
                                consumer.accept(categoryDetailModel);
                            }
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }

            }

            @Override
            public void onFail(Exception ex) {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
                hideProgressDialog();
                VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFinished ");
            }
        });
    }

}
