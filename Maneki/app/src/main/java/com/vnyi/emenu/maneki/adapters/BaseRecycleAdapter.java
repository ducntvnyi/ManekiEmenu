package com.vnyi.emenu.maneki.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.vnyi.emenu.maneki.callbacks.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hungnd on 11/7/17.
 */

public class BaseRecycleAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> listItems = new ArrayList<>();
    private boolean isLoading;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private int itemPerPage = 18;

    public void setLoadMore(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    totalItemCount = linearLayoutManager.getItemCount();
                    if (totalItemCount < itemPerPage)
                        return;
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    public void setDataList(List<T> items) {
        if (this.listItems != null)
            listItems.clear();
        addDataList(items);
    }

    private void addDataList(List<T> items) {
        if (this.listItems != null && items != null) {
            this.listItems.addAll(items);
            notifyDataSetChanged();
        }
    }
}
