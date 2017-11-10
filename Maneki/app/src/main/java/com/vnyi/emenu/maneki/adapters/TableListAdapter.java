package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class TableListAdapter extends BaseRecycleAdapter<ItemModel, TableListAdapter.ViewHolder> {


    private static final String TAG = TableListAdapter.class.getSimpleName();
    private Context mContext;
    private List<Table> tableList;
    private Consumer<Table> mConsumer;

    public TableListAdapter(Context context, List<Table> tables, Consumer<Table> consumer) {
        this.mContext = context;
        this.tableList = tables;
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
        Table Table = tableList.get(position);
        if (Table == null) return;

        holder.binData(mContext, Table);

        holder.view.setOnClickListener(view -> {
            mConsumer.accept(Table);
        });
    }

    @Override
    public int getItemCount() {
        return tableList == null ? 0 : tableList.size();
    }

    public void setTableList(List<Table> Tables) {
        this.tableList = Tables;
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

        private void binData(Context context, Table Table) {
            tvBranch.setText(Table.getRetDefineId());


        }
    }
}
