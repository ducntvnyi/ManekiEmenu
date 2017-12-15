package com.vnyi.emenu.maneki.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.util.KeyboardUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.concurrent.ThreadLocalRandom;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfirmChangeTableFragment extends BaseMainDialogFragment {


    private static final String TAG = DialogUserOrderFragment.class.getSimpleName();

    @BindView(R.id.tvCodeOrder)
    TextViewFont tvCodeOrder;
    @BindView(R.id.edtCodeOrder)
    EditText edtCodeOrder;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;

    final int maxLength = 5;
    private String code = "";

    public static DialogConfirmChangeTableFragment newInstance() {
        return new DialogConfirmChangeTableFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_change_table_fragment, container, false);
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
        code = genCodeOrder();
        initViews();

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        try {
            tvCodeOrder.setText(tvCodeOrder.getText().toString() + "\n" + code);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    private boolean isValidOrderCode() {
        String edtCode = edtCodeOrder.getText().toString().trim();

        return edtCode.equals(code);
    }

    private String genCodeOrder() {
        StringBuilder orderCode = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            orderCode.append(ThreadLocalRandom.current().nextInt(0, 9)).append("");
        }
        return orderCode.toString();
    }

    @OnClick(R.id.btnYes)
    void onBtnYesClick() {
        if (isValidOrderCode()) {
            mConsumer.accept(true);
            dismiss();
        } else {
            ToastUtils.showToast(getContext(), getString(R.string.Confirm_code_order_wrong));
        }

    }

    @OnClick(R.id.edtCodeOrder)
    void onClickCodeOrder() {
        KeyboardUtils.setFocusToEditText(getContext(), edtCodeOrder);
    }

    @OnClick(R.id.btnNo)
    void onBtnNoClick() {
        dismiss();
    }

    private Consumer<Boolean> mConsumer;

    public DialogConfirmChangeTableFragment setConsumer(Consumer<Boolean> consumer) {
        this.mConsumer = consumer;
        return this;
    }

}
