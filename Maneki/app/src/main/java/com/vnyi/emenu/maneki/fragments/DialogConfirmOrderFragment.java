package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.EditTextFont;
import com.vnyi.emenu.maneki.customviews.TextViewFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.concurrent.ThreadLocalRandom;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfirmOrderFragment extends BaseMainDialogFragment {


    private static final String TAG = DialogUserOrderFragment.class.getSimpleName();

    @BindView(R.id.tvCodeOrder)
    TextViewFont tvCodeOrder;
    @BindView(R.id.edtCodeOrder)
    EditTextFont edtCodeOrder;

    public static DialogConfirmOrderFragment newInstance() {
        return new DialogConfirmOrderFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_order_fragment, container, false);
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

    }

    private void initViews() {
        tvCodeOrder.setText(genCodeOrder());
    }

    private boolean isValidOrderCode() {
        String edtCode = edtCodeOrder.getText().toString().trim();
        String codeOrder = tvCodeOrder.getText().toString().trim();
        if (edtCode.equals(codeOrder)) {
            return true;
        }
        return false;
    }

    private String genCodeOrder() {
        String orderCode = "";
        for (int i = 0; i < 5; i++) {
            orderCode += ThreadLocalRandom.current().nextInt(0, 9) + "";
        }
        return orderCode;
    }

    @OnClick(R.id.btnYes)
    void onBtnYesClick() {
        if (isValidOrderCode()) {
            mConsumer.accept(true);
            dismiss();
        } else {
            ToastUtils.showToast(getContext(), "Mã bạn nhập không đúng, hãy nhập lại mã!");
        }

    }

    @OnClick(R.id.btnNo)
    void onBtnNoClick() {
        dismiss();
    }

    private Consumer<Boolean> mConsumer;

    public DialogConfirmOrderFragment setConsumer(Consumer<Boolean> consumer) {
        this.mConsumer = consumer;
        return this;
    }

}
