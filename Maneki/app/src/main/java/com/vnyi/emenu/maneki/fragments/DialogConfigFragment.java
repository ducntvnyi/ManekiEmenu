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

import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.KeyboardUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.EditTextFont;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.BranchModel;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.UserModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.UserOrder;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfigFragment extends BaseDialogFragment {

    private static final String TAG = DialogConfigFragment.class.getSimpleName();

    @BindView(R.id.ivDismiss)
    ImageView ivDismiss;
    @BindView(R.id.rlContent)
    LinearLayout rlContent;
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
        getDialog().getWindow().setWindowAnimations(com.qslib.library.R.style.DialogAnimationLeft);
        switchConfig.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                // update UI
                // Load config valueLoad
                scrollViewConfig.setVisibility(View.VISIBLE);
            } else {
                scrollViewConfig.setVisibility(View.GONE);
            }
            KeyboardUtils.hideSoftKeyboard(getActivity());
        });
        loadData();
    }

    private String getBranchName() {
        try {
            BranchModel branchModel = VnyiUtils.getListBranch(getContext());

            if (branchModel == null) return mConfigValueModel.getUserOrder().getConfigValue();

            Branch branch = VnyiUtils.getBranchName(branchModel.getBranches(), Integer.parseInt(mConfigValueModel.getBranch().getConfigValue()));

            if (branch == null) return mConfigValueModel.getUserOrder().getConfigValue();

            return branch.getBranchName();
        } catch (NumberFormatException e) {
            mConfigValueModel.getUserOrder().getConfigValue();
        }

    }

    private String getUserName() {
        try {
            UserModel userModel = VnyiUtils.getListUser(getContext());
             VnyiUtils.getUserName(.getUserOrders(), Integer.parseInt(mConfigValueModel.getUserOrder().getConfigValue())).getObjName();
        } catch (NumberFormatException e) {
            return mConfigValueModel.getUserOrder().getConfigValue();
        }
    }

    private void loadData() {
        mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
        if (mConfigValueModel != null) {
            edtLinkServer.setText(mConfigValueModel.getLinkServer());

            tvBranchLabel.setText(mConfigValueModel.getBranch().getConfigName());

            tvBranch.setText(getBranchName());

            tvTableNameLabel.setText(mConfigValueModel.getTableName().getConfigName());
            tvTableName.setText(mConfigValueModel.getTableName().getConfigValue());

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
    }

    @OnClick(R.id.ivDismiss)
    void onClickDismiss() {
        dismiss();
    }

    @OnClick(R.id.tbrBranch)
    void onClickBranch() {
        VnyiUtils.LogException(TAG, "==> onClickBranch");
        List<Branch> branches = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_LIST_BRANCH, BranchModel.class).getBranches();

        if (branches == null || branches.size() == 0) return;

        DialogBranchFragment.newInstance()
                .setListBranch(branches)
                .setConsumer(branch -> {
                    tvBranch.setText(branch.getBranchName());
                    mConfigValueModel.getBranch().setConfigName(branch.getBranchName());
                    mConfigValueModel.getBranch().setConfigValue(branch.getBranchId() + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrDevice)
    void onClickTableName() {
        VnyiUtils.LogException(TAG, "==> tbrDevice");
        DialogTableFragment.newInstance()
                .setLinkServer(mConfigValueModel.getLinkServer())
                .setConsumer(tableName -> {
                    tvTableName.setText(tableName.getRetDefineId());
                    mConfigValueModel.getTableName().setConfigValue(tableName.getRetAutoId() + "");
                    mConfigValueModel.getTableName().setConfigName(tableName.getRetDefineId() + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrTableNameUserOrder)
    void onClickUserOrder() {
        VnyiUtils.LogException(TAG, "==> tbrTableNameUserOrder");
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
    }

    @OnClick(R.id.tbrLoadListParent)
    void onClickLoadListParent() {
        VnyiUtils.LogException(TAG, "==> tbrLoadListParent");
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvLoadListParent.setText("" + value + "");
                    mConfigValueModel.getLoadListParent().setConfigDefaultValue(value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrLinkSaleOff1111)
    void onClickLinkSaleOff() {
        VnyiUtils.LogException(TAG, "==> onClickLinkSaleOff");
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(false)
                .setConsumerValueString(value -> {
                    tvLinkSaleOff.setText(value);
                    mConfigValueModel.getLinkSaleOff().setConfigDefaultValue(value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrLinkUseApp)
    void onClickLinkUserApp() {
        VnyiUtils.LogException(TAG, "==> tbrLinkUseApp");
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(false)
                .setConsumerValueString(value -> {
                    tvLinkUseApp.setText(value);
                    mConfigValueModel.getLinkUserApp().setConfigDefaultValue(value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrChangeTable)
    void onClickChangeTable() {
        VnyiUtils.LogException(TAG, "==> tbrChangeTable");
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvChangeTable.setText("" + value + "");
                    mConfigValueModel.getChangeTable().setConfigDefaultValue(value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrNumTableShow)
    void onClickLNumTableShow() {
        VnyiUtils.LogException(TAG, "==> tbrNumTableShow");
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvNumTableShow.setText("" + value + "");
                    mConfigValueModel.getNumbTableShow().setConfigDefaultValue(value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.btnConfirmConfig)
    void onClickLConfirmConfig() {
        VnyiUtils.LogException(TAG, "==> onClickLConfirmConfig");
        mConfigValueModel.setLinkServer(edtLinkServer.getText().toString() + "");
        VnyiPreference.getInstance(getContext()).putString(Constant.KEY_LINK_SERVER, edtLinkServer.getText().toString() + "");
        VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_CONFIG_VALUE, mConfigValueModel);
        VnyiUtils.LogException("onClickLConfirmConfig", "==> " + VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class).toString());
        // update config
        new ConfirmConfigTask().execute(mConfigValueModel);
    }

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
            int posId = 817;
            int langId = 1;
            ConfigValueModel configValueModel = configValueModels[0];

            // update config branch
            updateConfirm(posId, langId, configValueModel.getBranch().getConfigCode(), configValueModel.getBranch().getConfigDefaultValue());
            // update config link sale off
            updateConfirm(posId, langId, configValueModel.getLinkSaleOff().getConfigCode(), configValueModel.getLinkSaleOff().getConfigDefaultValue());
            // update config ten ban cho thiet bi
            updateConfirm(posId, langId, configValueModel.getTableName().getConfigCode(), configValueModel.getTableName().getConfigDefaultValue());
            // update config cap de load danh sach cha ben
            updateConfirm(posId, langId, configValueModel.getLoadListParent().getConfigCode(), configValueModel.getLoadListParent().getConfigDefaultValue());
            // update config link huong dan sd
            updateConfirm(posId, langId, configValueModel.getLinkUserApp().getConfigCode(), configValueModel.getLinkUserApp().getConfigDefaultValue());
            // update config ten user de order tren emene
            updateConfirm(posId, langId, configValueModel.getUserOrder().getConfigCode(), configValueModel.getUserOrder().getConfigDefaultValue());
            // update config chon thay doi ban
            updateConfirm(posId, langId, configValueModel.getChangeTable().getConfigCode(), configValueModel.getChangeTable().getConfigDefaultValue());
            // update config so luong hien thi theo cot tren giao dien
            updateConfirm(posId, langId, configValueModel.getNumbTableShow().getConfigCode(), configValueModel.getNumbTableShow().getConfigDefaultValue());

            return isCompleteUpdateConfig;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissDialog();

            loadConfigValue(isUpdateUI -> {
                if (isUpdateUI) {
//                    loadData();
                    ToastUtils.showToast(getContext(), "Xác nhận thành công!");
                }

            });

            VnyiUtils.LogException("ConfirmConfigTask", "==> onPostExecute:: " + aBoolean);
        }
    }

    protected void updateConfirm(int postId, int langId, String keyCode, String keyValue) {
        VnyiUtils.LogException(TAG, "==> updateConfirm:: keyCode:: " + keyCode + " keyValue::" + keyValue);
        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestConfigValueUpdateInfo(mConfigValueModel.getLinkServer(), postId, keyCode, keyValue, langId, new SoapListenerVyni() {

                        @Override
                        public void onStarted() {
                            VnyiUtils.LogException(TAG, "==> updateConfirm onStarted ");
                        }

                        @Override
                        public void onSuccess(SoapResponse soapResponse) {
                            isCompleteUpdateConfig = true;
                            VnyiUtils.LogException(TAG, "==> updateConfirm onSuccess ");
                        }

                        @Override
                        public void onFail(Exception ex) {
                            isCompleteUpdateConfig = false;
                            VnyiUtils.LogException(TAG, "==> updateConfirm onFail ");
                        }

                        @Override
                        public void onFinished() {
                            VnyiUtils.LogException(TAG, "==> updateConfirm onFinished ");
                        }
                    }
            );
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }
}
