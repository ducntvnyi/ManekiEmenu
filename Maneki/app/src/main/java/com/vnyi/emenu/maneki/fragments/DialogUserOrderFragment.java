package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.models.response.Branch;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogUserOrderFragment extends BaseMainDialogFragment {

    private List<Branch> mBranches;

    public static DialogUserOrderFragment newInstance() {
        DialogUserOrderFragment fragment = new DialogUserOrderFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_order_fragment, container, false);
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

    }

    private void loadData() {
    }

    public DialogUserOrderFragment setListBranch(List<Branch> branches) {
        this.mBranches = branches;
        return this;
    }
}
