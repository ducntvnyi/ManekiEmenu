package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogCallWaiterFragment extends BaseMainDialogFragment {

    public static DialogCallWaiterFragment newInstance() {
        DialogCallWaiterFragment fragment = new DialogCallWaiterFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_call_waiter_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btnSendRequest)
    void onClickSendRequest(){
        dismiss();
    }

    @OnClick(R.id.btnCancelRquest)
    void onClickCancelRequest(){
        dismiss();
    }

}
