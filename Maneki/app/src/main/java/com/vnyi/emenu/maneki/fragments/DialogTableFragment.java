package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.TableListAdapter;
import com.vnyi.emenu.maneki.customviews.DividerItemDecoration;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogTableFragment extends BaseDialogFragment {

    private static final String TAG = DialogTableFragment.class.getSimpleName();

    @BindView(R.id.tvTitle)
    TextViewFont tvTitle;
    @BindView(R.id.rvTableList)
    RecyclerView rvTableList;

    private List<Table> mTableList;
    private Consumer<Table> mConsumer;
    private TableListAdapter mTableListAdapter;

    private String linkSerVer;

    public static DialogTableFragment newInstance() {
        return new DialogTableFragment();

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
        try {
            mTableListAdapter = new TableListAdapter(getContext(), mTableList, branch -> {
                mConsumer.accept(branch);
                dismiss();
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvTableList.setAdapter(mTableListAdapter);
            rvTableList.setLayoutManager(layoutManager);
            rvTableList.addItemDecoration(new DividerItemDecoration(getContext()));
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    private void loadData() {
       mTableListAdapter.notifyDataSetChanged();
    }

    public DialogTableFragment setListTable(List<Table> tableNames) {
        this.mTableList = tableNames;
        return this;
    }

    public DialogTableFragment setLinkServer(String url) {
        this.linkSerVer = url;
        return this;
    }

    public DialogTableFragment setConsumer(Consumer<Table> consumer) {
        this.mConsumer = consumer;
        return this;
    }

}
