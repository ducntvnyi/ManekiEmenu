package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.TableMap;
import com.vnyi.emenu.maneki.models.response.TableObj;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class TableMapListAdapter extends BaseRecycleAdapter<ItemModel, TableMapListAdapter.ViewHolder> {


    private static final String TAG = TableMapListAdapter.class.getSimpleName();
    private Context mContext;
    private List<TableObj> tableList;
    private Consumer<TableObj> mConsumer;

    public TableMapListAdapter(Context context, List<TableObj> tables, Consumer<TableObj> consumer) {
        this.mContext = context;
        this.tableList = tables;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        TableObj tableObj = tableList.get(position);
        if (tableObj == null) return;

        holder.binData(mContext, tableObj);

        holder.view.setOnClickListener(view -> {

            mConsumer.accept(tableObj);
        });
    }

    @Override
    public int getItemCount() {
        return tableList == null ? 0 : tableList.size();
    }

    public void setTableList(List<TableObj> Tables) {
        this.tableList = Tables;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTableName)
        TextViewFont tvTableName;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, TableObj tableMap) {
            try {
                tvTableName.setText(tableMap.getTableName());
                tvTableName.setTextColor(context.getResources().getColor(R.color.white));
                view.setBackgroundColor(context.getResources().getColor(R.color.bg_menu_bottom_active));
                if (!TextUtils.isEmpty(tableMap.getBackgroundColor())) {
                    view.setBackgroundColor(tableMap.isSeleted() ? context.getResources().getColor(R.color.color_app) : Color.parseColor(tableMap.getBackgroundColor()));
                }

            } catch (Exception e) {
                VnyiUtils.LogException(context, " binData onFail", TAG, e.getMessage());
            }

        }
    }
}
