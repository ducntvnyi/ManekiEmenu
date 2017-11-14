package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.RequestGetList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class GetListAdapter extends BaseRecycleAdapter<ItemModel, GetListAdapter.ViewHolder> {


    private static final String TAG = GetListAdapter.class.getSimpleName();

    private Context mContext;
    private List<RequestGetList> mRequestGetLists;
    private Consumer<RequestGetList> mConsumer;

    public GetListAdapter(Context context, List<RequestGetList> requestGetLists, Consumer<RequestGetList> consumer) {
        this.mContext = context;
        this.mRequestGetLists = requestGetLists;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_get_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        RequestGetList requestGetList = mRequestGetLists.get(position);
        if (requestGetList == null) return;

        holder.binData(mContext, requestGetList);

        holder.view.setOnClickListener(view -> {
            mRequestGetLists.get(position).setChecked(!mRequestGetLists.get(position).isChecked());
            holder.cbxItem.setChecked(mRequestGetLists.get(position).isChecked());
//            mConsumer.accept(requestGetList);
        });
        holder.cbxItem.setOnClickListener(view -> {
            mRequestGetLists.get(position).setChecked(!mRequestGetLists.get(position).isChecked());
            holder.cbxItem.setChecked(mRequestGetLists.get(position).isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return mRequestGetLists == null ? 0 : mRequestGetLists.size();
    }

    public void setGetList(List<RequestGetList> RequestGetLists) {
        this.mRequestGetLists = RequestGetLists;
        notifyDataSetChanged();
    }

    public List<RequestGetList> getListRequest() {
        return this.mRequestGetLists;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbxItem)
        CheckBox cbxItem;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, RequestGetList requestGetList) {
            cbxItem.setText(requestGetList.getRequestNote());
            cbxItem.setChecked(requestGetList.isChecked());
        }
    }
}
