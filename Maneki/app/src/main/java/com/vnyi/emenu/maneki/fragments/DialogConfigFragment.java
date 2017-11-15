package com.vnyi.emenu.maneki.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableRow;

import com.qslib.util.KeyboardUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.EditTextFont;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.BranchModel;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.TableModel;
import com.vnyi.emenu.maneki.models.UserModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.models.response.UserOrder;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfigFragment extends BaseDialogFragment {

    private static final String TAG = DialogConfigFragment.class.getSimpleName();

    @BindView(R.id.ivDismiss)
    ImageView ivDismiss;
    @BindView(R.id.rlContent)
    LinearLayout rlContent;
    @BindView(R.id.llConfigContainer)
    LinearLayout llConfigContainer;
    @BindView(R.id.switchConfig)
    Switch switchConfig;
    @BindView(R.id.scrollViewConfig)
    ScrollView scrollViewConfig;
    @BindView(R.id.llConfig)
    LinearLayout llConfig;
    @BindView(R.id.tbrBranch)
    TableRow tbrBranch;
    @BindView(R.id.tvBranch)
    TextViewFont tvBranch;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;
    @BindView(R.id.tvTableNameUser)
    TextViewFont tvTableNameUser;
    @BindView(R.id.tvLoadListParent)
    TextViewFont tvLoadListParent;
    @BindView(R.id.tvLinkSaleOff)
    TextViewFont tvLinkSaleOff;
    @BindView(R.id.tbrLinkUseApp)
    TableRow tbrUseApp;
    @BindView(R.id.tvLinkUseApp)
    TextViewFont tvLinkUseApp;
    @BindView(R.id.tvChangeTable)
    TextViewFont tvChangeTable;
    @BindView(R.id.tvNumTableShow)
    TextViewFont tvNumTableShow;
    @BindView(R.id.btnConfirmConfig)
    ButtonFont btnConfirmConfig;
    @BindView(R.id.edtLinkServer)
    EditTextFont edtLinkServer;

    // views label
    @BindView(R.id.tvBranchLabel)
    TextViewFont tvBranchLabel;
    @BindView(R.id.tvTableNameLabel)
    TextViewFont tvTableNameLabel;
    @BindView(R.id.tvTableNameUserLabel)
    TextViewFont tvTableNameUserLabel;
    @BindView(R.id.tvLoadListParentLabel)
    TextViewFont tvLoadListParentLabel;
    @BindView(R.id.tvLinkSaleOffLabel)
    TextViewFont tvLinkSaleOffLabel;
    @BindView(R.id.tvLinkUseAppLabel)
    TextViewFont tvLinkUseAppLabel;
    @BindView(R.id.tvChangeTableLabel)
    TextViewFont tvChangeTableLabel;
    @BindView(R.id.tvNumTableShowLabel)
    TextViewFont tvNumTableShowLabel;


    private ConfigValueModel mConfigValueModel;

    private boolean isCompleteUpdateConfig = false;

    public static DialogConfigFragment newInstance() {
        DialogConfigFragment fragment = new DialogConfigFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_config_fragment, container, false);
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
        try {
            getDialog().getWindow().setWindowAnimations(com.qslib.library.R.style.DialogAnimationLeft);

            switchConfig.setOnCheckedChangeListener((compoundButton, checked) -> {
                if (checked) {
                    // update UI
                    // Load config valueLoad
                    llConfigContainer.setVisibility(View.VISIBLE);
                    //                scrollViewConfig.setVisibility(View.VISIBLE);
                } else {
                    //                scrollViewConfig.setVisibility(View.GONE);
                    llConfigContainer.setVisibility(View.GONE);
                }
                KeyboardUtils.hideSoftKeyboard(getActivity());
            });
            loadData();
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    private String getBranchName() {
        try {
            BranchModel branchModel = VnyiUtils.getListBranch(getContext());

            if (branchModel == null) return mConfigValueModel.getUserOrder().getConfigValue();

            Branch branch = VnyiUtils.getBranchName(branchModel.getBranches(), Integer.parseInt(mConfigValueModel.getBranch().getConfigValue()));

            if (branch == null) return mConfigValueModel.getUserOrder().getConfigValue();

            return branch.getBranchName();

        } catch (Exception e) {

            return mConfigValueModel.getUserOrder().getConfigValue();
        }

    }

    private String getUserName() {
        try {
            UserModel userModel = VnyiUtils.getListUser(getContext());
            if (userModel == null) return mConfigValueModel.getUserOrder().getConfigValue();

            UserOrder userOrder = VnyiUtils.getUserName(userModel.getUserOrders(), Integer.parseInt(mConfigValueModel.getUserOrder().getConfigValue()));
            if (userOrder == null) return mConfigValueModel.getUserOrder().getConfigValue();

            return userOrder.getObjName();

        } catch (Exception e) {

            return mConfigValueModel.getUserOrder().getConfigValue();
        }
    }


    private void loadData() {
        try {
            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            if (mConfigValueModel != null) {
                int tableId = Integer.parseInt(mConfigValueModel.getTableName().getConfigValue());

                edtLinkServer.setText(mConfigValueModel.getLinkServer());

                tvBranchLabel.setText(mConfigValueModel.getBranch().getConfigName());

                tvBranch.setText(getBranchName());

                tvTableNameLabel.setText(mConfigValueModel.getTableName().getConfigName());
                tvTableName.setText(getTableName(tableId));
                VnyiPreference.getInstance(getContext()).putString(Constant.KEY_TABLE_NAME, tvTableName.getText().toString().trim());

                tvTableNameUserLabel.setText(mConfigValueModel.getUserOrder().getConfigName());
                tvTableNameUser.setText(getUserName());

                tvLoadListParentLabel.setText(mConfigValueModel.getLoadListParent().getConfigName());
                tvLoadListParent.setText(mConfigValueModel.getLoadListParent().getConfigValue());

                tvLinkSaleOffLabel.setText(mConfigValueModel.getLinkSaleOff().getConfigName());
                tvLinkSaleOff.setText(mConfigValueModel.getLinkSaleOff().getConfigValue());

                tvLinkUseAppLabel.setText(mConfigValueModel.getLinkUserApp().getConfigName());
                tvLinkUseApp.setText(mConfigValueModel.getLinkUserApp().getConfigValue());

                tvChangeTableLabel.setText(mConfigValueModel.getChangeTable().getConfigName());
                tvChangeTable.setText(mConfigValueModel.getChangeTable().getConfigValue());

                tvNumTableShowLabel.setText(mConfigValueModel.getNumbTableShow().getConfigName());
                tvNumTableShow.setText(mConfigValueModel.getNumbTableShow().getConfigValue());
            }
        } catch (NumberFormatException e) {
            VnyiUtils.LogException(getContext(), "loadData", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.ivDismiss)
    void onClickDismiss() {
        dismiss();
    }

    @OnClick(R.id.tbrBranch)
    void onClickBranch() {
        try {
            List<Branch> branches = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_LIST_BRANCH, BranchModel.class).getBranches();

            if (branches == null || branches.size() == 0) return;

            DialogBranchFragment.newInstance().setListBranch(branches).setConsumer(branch -> {
                tvBranch.setText(branch.getBranchName());
                mConfigValueModel.getBranch().setConfigName(branch.getBranchName());
                mConfigValueModel.getBranch().setConfigValue(branch.getBranchId() + "");
                //
                loadTables(mConfigValueModel, table -> {
                    tvTableName.setText(table.getRetDefineId());
                    mConfigValueModel.getTableName().setConfigValue(table.getRetAutoId() + "");
                    mConfigValueModel.getTableName().setName(table.getRetDefineId() + "");
                });
            }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickBranch", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrDevice)
    void onClickTableName() {

        try {
            List<Table> tables = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_LIST_TABLE, TableModel.class).getTables();

            if (tables == null || tables.size() == 0) return;

            DialogTableFragment.newInstance()
                    .setLinkServer(mConfigValueModel.getLinkServer())
                    .setListTable(tables)
                    .setConsumer(tableName -> {
                        tvTableName.setText(tableName.getRetDefineId());
                        mConfigValueModel.getTableName().setConfigValue(tableName.getRetAutoId() + "");
                        mConfigValueModel.getTableName().setName(tableName.getRetDefineId() + "");

                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickTableName", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrTableNameUserOrder)
    void onClickUserOrder() {

        try {
            int branchId = Integer.parseInt(mConfigValueModel.getBranch().getConfigValue().trim());
            List<UserOrder> userOrders = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_LIST_USER_ORDER, UserModel.class).getUserOrders();
            DialogUserOrderFragment.newInstance()
                    .setLinkServer(mConfigValueModel.getLinkServer())
                    .setBranchId(branchId)
                    .setListUser(userOrders)
                    .setConsumer(userOrder -> {
                        tvTableNameUser.setText(userOrder.getObjName());
                        mConfigValueModel.getUserOrder().setConfigValue(userOrder.getObjAutoId() + "");
                        mConfigValueModel.getUserOrder().setConfigName(userOrder.getObjName());
                    })
                    .show(getFragmentManager(), "");
        } catch (NumberFormatException e) {
            VnyiUtils.LogException(getContext(), "onClickUserOrder", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrLoadListParent)
    void onClickLoadListParent() {
        try {
            DialogInputValueFragment.newInstance()
                    .setIsNumberInputType(true)
                    .setConsumerValueInt(value -> {
                        tvLoadListParent.setText("" + value + "");
                        mConfigValueModel.getLoadListParent().setConfigValue(value + "");
                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickLoadListParent", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrLinkSaleOff1111)
    void onClickLinkSaleOff() {

        try {
            DialogInputValueFragment.newInstance()
                    .setIsNumberInputType(false)
                    .setConsumerValueString(value -> {
                        tvLinkSaleOff.setText(value);
                        mConfigValueModel.getLinkSaleOff().setConfigValue(value + "");
                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickBranch", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrLinkUseApp)
    void onClickLinkUserApp() {
        try {
            DialogInputValueFragment.newInstance()
                    .setIsNumberInputType(false)
                    .setConsumerValueString(value -> {
                        tvLinkUseApp.setText(value);
                        mConfigValueModel.getLinkUserApp().setConfigValue(value + "");
                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickLinkUserApp", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrChangeTable)
    void onClickChangeTable() {
        try {
            DialogInputValueFragment.newInstance()
                    .setIsNumberInputType(true)
                    .setConsumerValueInt(value -> {
                        tvChangeTable.setText("" + value + "");
                        mConfigValueModel.getChangeTable().setConfigValue(value + "");
                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickChangeTable", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.tbrNumTableShow)
    void onClickLNumTableShow() {
        try {
            DialogInputValueFragment.newInstance()
                    .setIsNumberInputType(true)
                    .setConsumerValueInt(value -> {
                        tvNumTableShow.setText("" + value + "");
                        mConfigValueModel.getNumbTableShow().setConfigValue(value + "");
                    }).show(getFragmentManager(), "");
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickLNumTableShow", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.btnConfirmConfig)
    void onClickLConfirmConfig() {

        try {
            mConfigValueModel.setLinkServer(edtLinkServer.getText().toString() + "");
            VnyiPreference.getInstance(getContext()).putString(Constant.KEY_LINK_SERVER, edtLinkServer.getText().toString() + "");
            VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_CONFIG_VALUE, mConfigValueModel);
            VnyiUtils.LogException("onClickLConfirmConfig", "==> " + VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class).toString());
            // update config
            new ConfirmConfigTask().execute(mConfigValueModel);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickLConfirmConfig", TAG, e.getMessage());
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
                // update config so luong hien thi theo cot tren giao dien
                updateConfirm(configValueModel.getLinkServer(), configValueModel.getNumbTableShow().getConfigCode(), configValueModel.getNumbTableShow().getConfigValue());
            } catch (Exception e) {
                VnyiUtils.LogException(getContext(), "onClickBranch", TAG, e.getMessage());
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissDialog();
            VnyiUtils.LogException("ConfirmConfigTask", "==> onPostExecute:: " + aBoolean);
            mConsumerConfigValue.accept(true);
        }
    }

    private Consumer<Boolean> mConsumerConfigValue;

    public DialogConfigFragment setConsumerConfigValue(Consumer<Boolean> consumer) {
        this.mConsumerConfigValue = consumer;
        return this;
    }
}
