package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.EditTextFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogInputValueFragment extends BaseMainDialogFragment {

    private boolean isNumber = false;
    private String valueString;
    private int valueInt;
    private Consumer<Integer> consumerValueInt;
    private Consumer<String> consumerValueString;

    @BindView(R.id.edtValueString)
    EditTextFont edtValueString;
    @BindView(R.id.edtValueInt)
    EditTextFont edtValueInt;

    public static DialogInputValueFragment newInstance() {
        DialogInputValueFragment fragment = new DialogInputValueFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input_value_fragment, container, false);
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
        edtValueInt.setVisibility(isNumber ? View.VISIBLE : View.GONE);
        edtValueString.setVisibility(isNumber ? View.GONE : View.VISIBLE);
    }

    private void loadData() {
    }

    public DialogInputValueFragment setIsNumberInputType(boolean isNumber) {
        this.isNumber = isNumber;
        return this;
    }

    public DialogInputValueFragment setConsumerValueInt(Consumer<Integer> consumer) {
        this.consumerValueInt = consumer;
        return this;
    }

    public DialogInputValueFragment setConsumerValueString(Consumer<String> consumer) {
        this.consumerValueString = consumer;
        return this;
    }


    @OnClick(R.id.btnYes)
    void onClickYes() {
        if (isNumber) {
            valueInt = Integer.parseInt(edtValueInt.getText().toString());
            this.consumerValueInt.accept(valueInt);
            dismiss();
        } else {
            valueString = edtValueString.getText().toString();
            this.consumerValueString.accept(valueString);
            dismiss();
        }
    }

    @OnClick(R.id.btnCancel)
    void onClickCancel() {
        dismiss();
    }


}
