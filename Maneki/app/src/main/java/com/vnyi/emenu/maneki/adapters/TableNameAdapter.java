package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.TableName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class TableNameAdapter extends BaseRecycleAdapter<ItemModel, TableNameAdapter.ViewHolder> {


    private static final String TAG = TableNameAdapter.class.getSimpleName();
    private Context mContext;
    private List<TableName> branchList;
    private Consumer<TableName> mConsumer;

    public TableNameAdapter(Context context, List<TableName> branches, Consumer<TableName> consumer) {
        this.mContext = context;
        this.branchList = branches;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brach_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        TableName tableName = branchList.get(position);
        if (tableName == null) return;

        holder.binData(mContext, tableName);

        holder.view.setOnClickListener(view -> {
            mConsumer.accept(tableName);
        });
    }

    @Override
    public int getItemCount() {
        return branchList == null ? 0 : branchList.size();
    }

    public void setTableList(List<TableName> tableNames) {
        this.branchList = tableNames;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvBranch)
        TextViewFont tvBranch;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, TableName tableName) {
            tvBranch.setText(tableName.getTableName());


        }
    }
}
