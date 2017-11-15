package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogRemoveItemFragment extends BaseMainDialogFragment {


    private static final String TAG = DialogUserOrderFragment.class.getSimpleName();
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;

    public static DialogRemoveItemFragment newInstance() {
        return new DialogRemoveItemFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_remove_item_fragment, container, false);
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
        try {
            String tableName = VnyiPreference.getInstance(getContext()).getString(Constant.KEY_TABLE_NAME);
            tvTableName.setText(tvTableName.getText() + " " + tableName);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.btnYes)
    void onBtnYesClick() {
        dismiss();
        mConsumer.accept(true);
    }


    @OnClick(R.id.btnNo)
    void onBtnNoClick() {
        dismiss();
        mConsumer.accept(false);
    }

    private Consumer<Boolean> mConsumer;

    public DialogRemoveItemFragment setConsumer(Consumer<Boolean> consumer) {
        this.mConsumer = consumer;
        return this;
    }


}
