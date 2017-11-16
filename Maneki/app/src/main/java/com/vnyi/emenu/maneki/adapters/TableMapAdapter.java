package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

public class TableMapAdapter extends BaseRecycleAdapter<ItemModel, TableMapAdapter.ViewHolder> {


    private static final String TAG = TableMapAdapter.class.getSimpleName();
    private Context mContext;
    private List<Table> tableList;
    private Consumer<Table> mConsumer;

    public TableMapAdapter(Context context, List<Table> branches, Consumer<Table> consumer) {
        this.mContext = context;
        this.tableList = branches;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_map_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        Table table = tableList.get(position);
        if (table == null) return;

        holder.binData(mContext, table);

        holder.view.setOnClickListener(view -> {
            mConsumer.accept(table);
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

        private void binData(Context context, Table table) {
            tvBranch.setText(table.getRetDefineId());
            if (table.isSelected()) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.color_app));
                tvBranch.setTextColor(ContextCompat.getColor(context, R.color.white));
            }


        }
    }
}
