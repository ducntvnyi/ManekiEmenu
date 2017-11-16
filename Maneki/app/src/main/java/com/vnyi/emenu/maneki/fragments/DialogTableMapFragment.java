package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.TableMapAdapter;
import com.vnyi.emenu.maneki.customviews.DividerItemDecoration;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogTableMapFragment extends BaseDialogFragment {

    private static final String TAG = DialogTableMapFragment.class.getSimpleName();

    @BindView(R.id.tvTitle)
    TextViewFont tvTitle;
    @BindView(R.id.rvTableList)
    RecyclerView rvTableList;

    private List<Table> mTableList;
    private Consumer<Table> mConsumer;
    private TableMapAdapter mTableMapAdapter;
    private Table mTableSelected;
    private String linkSerVer;

    public static DialogTableMapFragment newInstance() {
        return new DialogTableMapFragment();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_table_map_fragment, container, false);
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
            mTableMapAdapter = new TableMapAdapter(getContext(), mTableList, table -> {
                mTableSelected = table;
                StreamSupport.stream(mTableList).forEach(table1 -> table1.setSelected(table1.getRetAutoId() == table.getRetAutoId()));
                mTableMapAdapter.notifyDataSetChanged();
            });
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
            rvTableList.setAdapter(mTableMapAdapter);
            rvTableList.setLayoutManager(layoutManager);
//            rvTableList.addItemDecoration(new DividerItemDecoration(getContext()));

        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    private void loadData() {
        mTableMapAdapter.notifyDataSetChanged();
    }

    public DialogTableMapFragment setListTable(List<Table> tableNames) {
        this.mTableList = tableNames;
        return this;
    }

    public DialogTableMapFragment setLinkServer(String url) {
        this.linkSerVer = url;
        return this;
    }

    public DialogTableMapFragment setConsumer(Consumer<Table> consumer) {
        this.mConsumer = consumer;
        return this;
    }


    @OnClick(R.id.btnYes)
    void onBtnYesClick() {
        mConsumer.accept(mTableSelected);
        dismiss();
    }


    @OnClick(R.id.btnNo)
    void onBtnNoClick() {
        dismiss();
    }

}
