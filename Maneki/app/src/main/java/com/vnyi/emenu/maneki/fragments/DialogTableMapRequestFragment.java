package com.vnyi.emenu.maneki.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.TableListAdapter;
import com.vnyi.emenu.maneki.adapters.TableMapListAdapter;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.DividerItemDecoration;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.TableMap;
import com.vnyi.emenu.maneki.models.response.TableObj;
import com.vnyi.emenu.maneki.models.response.config.ConfigModel;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogTableMapRequestFragment extends BaseDialogFragment {

    private static final String TAG = DialogTableMapRequestFragment.class.getSimpleName();

    @BindView(R.id.tvTitle)
    TextViewFont tvTitle;
    @BindView(R.id.edtCode)
    EditText edtCode;

    @BindView(R.id.rvTableList)
    RecyclerView rvTableList;

    private List<TableObj> mTableList = new ArrayList<>();
    private Consumer<TableObj> mConsumer;
    private TableMapListAdapter mTableListAdapter;

    private String linkSerVer;
    private TableObj mTableObj = null;
    private ConfigValueModel mConfigValueModel;

    private String password = "124635";

    public static DialogTableMapRequestFragment newInstance() {
        return new DialogTableMapRequestFragment();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_table_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        loadData();
    }

    private void initViews() {
        try {
            mTableListAdapter = new TableMapListAdapter(getContext(), mTableList, tableMap -> {
                mTableObj = tableMap;
                StreamSupport.stream(mTableList).forEach(tableObj -> tableObj.setSeleted(tableObj.getTableId() == tableMap.getTableId()));
                mTableListAdapter.notifyDataSetChanged();
//                DialogConfirmChangeTableFragment.newInstance().setConsumer(isConfirm -> {
//                    if (isConfirm) {
//                        new ConfirmConfigTask().execute(mConfigValueModel);
//                    }
//
//                }).show(getFragmentManager(), null);
            });
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
            rvTableList.setAdapter(mTableListAdapter);
            rvTableList.setLayoutManager(layoutManager);
            rvTableList.addItemDecoration(new DividerItemDecoration(getContext()));
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    private void loadData() {
        loadTables(mConfigValueModel);
    }


    public DialogTableMapRequestFragment setLinkServer(String url) {
        this.linkSerVer = url;
        return this;
    }

    public DialogTableMapRequestFragment setConfigValueModel(ConfigValueModel configValueModel) {
        this.mConfigValueModel = configValueModel;
        return this;
    }

    public DialogTableMapRequestFragment setConsumer(Consumer<TableObj> consumer) {
        this.mConsumer = consumer;
        return this;
    }

    protected void loadTables(ConfigValueModel configValueModel) {

        try {
            int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            int counterId = 0;
            int areaId = 0;
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int objId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
            int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
            boolean isSwitch = false;

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestGetTableMap(configValueModel.getLinkServer(), branchId, counterId, areaId, langId, objId, posId, isSwitch, new SoapListenerVyni() {

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
                            try {
                                JSONObject jsonObject = new JSONObject(soapResponse.getResult());
                                List<TableObj> tables = JacksonUtils.convertJsonToObject(jsonObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TableObj>>() {
                                });
                                if (tables == null) return;
                                mTableList = tables;
                                mTableListAdapter.setTableList(mTableList);
                                // save to local
                                VnyiUtils.LogException(TAG, "==> tables" + tables.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(getContext(), " loadTables onFail", TAG, ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> loadTables onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " loadTables error", TAG, e.getMessage());
        }
    }

    /**
     * update config in background thread
     */
    private class ConfirmConfigTask extends AsyncTask<ConfigValueModel, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            VnyiUtils.LogException("ConfirmConfigTask", "==> onPostExecute");
            showDialog();

        }

        @Override
        protected Boolean doInBackground(ConfigValueModel... configValueModels) {
            VnyiUtils.LogException("ConfirmConfigTask", "==> doInBackground");

            try {
                ConfigValueModel configValueModel = configValueModels[0];

                // update config branch
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getBranch().getConfigCode(), configValueModel.getBranch().getConfigValue());
                // update config link sale off
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getLinkSaleOff().getConfigCode(), configValueModel.getLinkSaleOff().getConfigValue());
                // update config ten ban cho thiet bi
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getTableName().getConfigCode(), configValueModel.getTableName().getConfigValue());
                // update config cap de load danh sach cha ben
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getLoadListParent().getConfigCode(), configValueModel.getLoadListParent().getConfigValue());
                // update config link huong dan sd
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getLinkUserApp().getConfigCode(), configValueModel.getLinkUserApp().getConfigValue());
                // update config ten user de order tren emenu
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getUserOrder().getConfigCode(), configValueModel.getUserOrder().getConfigValue());
                // update config chon thay doi ban
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getChangeTable().getConfigCode(), configValueModel.getChangeTable().getConfigValue());
                String tableConfigValue = configValueModel.getTableName().getConfigValue();
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getTableName().getConfigCode(), tableConfigValue);
                // update config so luong hien thi theo cot tren giao dien
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getNumbTableShow().getConfigCode(), configValueModel.getNumbTableShow().getConfigValue());
            } catch (Exception e) {
                VnyiUtils.LogException(getContext(), "ConfirmConfigTask", TAG, e.getMessage());
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissDialog();
            VnyiUtils.LogException("ConfirmConfigTask", "==> onPostExecute:: " + aBoolean);
            mConsumer.accept(mTableObj);
            dismiss();
        }
    }

    @OnClick(R.id.btnConfirm)
    void onClickConfirm() {
        if (mTableObj == null) {
            ToastUtils.showToast(getContext(), getString(R.string.confirm_table_change));
        } else {
            String pw = edtCode.getText().toString().trim();
            if (pw.equals(password)) {
                mConfigValueModel.getChangeTable().setConfigValue(mTableObj.getTableId() + "");
                mConfigValueModel.getTableName().setConfigValue(mTableObj.getTableName() + "");
                new ConfirmConfigTask().execute(mConfigValueModel);
            }else {
                ToastUtils.showToast(getContext(), getString(R.string.password_incorrect));
            }
        }

    }

}
