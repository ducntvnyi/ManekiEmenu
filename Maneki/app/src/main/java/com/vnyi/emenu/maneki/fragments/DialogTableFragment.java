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

public class DialogTableFragment extends BaseMainDialogFragment {

    private List<Branch> mBranches;

    public static DialogTableFragment newInstance() {
        DialogTableFragment fragment = new DialogTableFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_table_fragment, container, false);
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

    public DialogTableFragment setListBranch(List<Branch> branches) {
        this.mBranches = branches;
        return this;
    }
}
