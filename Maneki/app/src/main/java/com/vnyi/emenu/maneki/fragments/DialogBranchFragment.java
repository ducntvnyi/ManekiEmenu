package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.BranchAdapter;
import com.vnyi.emenu.maneki.customviews.DividerItemDecoration;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogBranchFragment extends BaseMainDialogFragment {

    private static final String TAG = DialogUserOrderFragment.class.getSimpleName();
    @BindView(R.id.tvTitle)
    TextViewFont tvTitle;
    @BindView(R.id.rvBranchList)
    RecyclerView rvBranchList;

    private List<Branch> mBranches;
    private Consumer<Branch> mConsumer;
    private BranchAdapter mBranchAdapter;

    public static DialogBranchFragment newInstance() {
        DialogBranchFragment fragment = new DialogBranchFragment();
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_branch_fragment, container, false);
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
        try {
            mBranchAdapter = new BranchAdapter(getContext(), mBranches, branch -> {
                mConsumer.accept(branch);
                dismiss();
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvBranchList.setAdapter(mBranchAdapter);
            rvBranchList.setLayoutManager(layoutManager);
            rvBranchList.addItemDecoration(new DividerItemDecoration(getContext()));
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onClickConfigHome", TAG, e.getMessage());
        }
    }

    private void loadData() {
        mBranchAdapter.notifyDataSetChanged();
    }

    public DialogBranchFragment setListBranch(List<Branch> branches) {
        this.mBranches = branches;
        return this;
    }

    public DialogBranchFragment setConsumer(Consumer<Branch> branchConsumer) {
        this.mConsumer = branchConsumer;
        return this;
    }

}
