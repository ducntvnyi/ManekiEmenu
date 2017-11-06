package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogConfigFragment extends BaseMainDialogFragment {

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

    @OnClick(R.id.ivDismiss)
    void onClickDismiss() {
        dismiss();
    }
}
