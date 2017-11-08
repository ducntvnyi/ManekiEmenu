package com.vnyi.emenu.maneki.fragments;

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

import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.util.KeyboardUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.ButtonFont;
import com.vnyi.emenu.maneki.customviews.TextViewFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfigFragment extends BaseMainDialogFragment {

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
    @BindView(R.id.tbrTableName)
    TableRow tbrTableName;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;
    @BindView(R.id.tbrTableNameUserOrder)
    TableRow tbrTableNameUser;
    @BindView(R.id.tvTableNameUser)
    TextViewFont tvTableNameUser;
    @BindView(R.id.tbrLoadListParent)
    TableRow tbrLoadList;
    @BindView(R.id.tvLoadListParent)
    TextViewFont tvLoadListParent;
    @BindView(R.id.tbrLinkSaleOff)
    TableRow tbrLinkSaleOff;
    @BindView(R.id.tvLinkSaleOff)
    TextViewFont tvLinkSaleOff;
    @BindView(R.id.tbrLinkUseApp)
    TableRow tbrUseApp;
    @BindView(R.id.tvLinkUseApp)
    TextViewFont tvLinkUseApp;
    @BindView(R.id.tbrChangeTable)
    TableRow tbrChangeTable;
    @BindView(R.id.tvChangeTable)
    TextViewFont tvChangeTable;
    @BindView(R.id.tbrNumTableShow)
    TableRow tbrNumTableShow;
    @BindView(R.id.tvNumTableShow)
    TextViewFont tvNumTableShow;
    @BindView(R.id.btnConfirmConfig)
    ButtonFont btnConfirmConfig;

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
    }

    @OnClick(R.id.ivDismiss)
    void onClickDismiss() {
        dismiss();
    }

    @OnClick(R.id.tbrBranch)
    void onClickBranch() {
        DialogBranchFragment.newInstance().show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrTableName)
    void onClickTableName() {
        DialogTableFragment.newInstance().show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrTableNameUserOrder)
    void onClickUserOrder() {
        DialogUserOrderFragment.newInstance().show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrLoadListParent)
    void onClickLoadListParent() {
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvLoadListParent.setText("" + value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrLinkSaleOff)
    void onClickLinkSaleOff() {
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(false)
                .setConsumerValueString(value -> {
                    tvLinkSaleOff.setText(value);
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrLinkUseApp)
    void onClickLinkUserApp() {
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(false)
                .setConsumerValueString(value -> {
                    tvLinkUseApp.setText(value);
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrChangeTable)
    void onClickChangeTable() {
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvChangeTable.setText("" + value + "");
                }).show(getFragmentManager(), "");
    }

    @OnClick(R.id.tbrNumTableShow)
    void onClickLNumTableShow() {
        DialogInputValueFragment.newInstance()
                .setIsNumberInputType(true)
                .setConsumerValueInt(value -> {
                    tvNumTableShow.setText("" + value + "");
                }).show(getFragmentManager(), "");
    }
}
