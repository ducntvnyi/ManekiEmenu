package com.qslib.recycleview.swipe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.qslib.recycleview.swipe.SwipeLayout;
import com.qslib.recycleview.swipe.implments.SwipeItemMangerImpl;
import com.qslib.recycleview.swipe.interfaces.SwipeAdapterInterface;
import com.qslib.recycleview.swipe.interfaces.SwipeItemMangerInterface;
import com.qslib.recycleview.swipe.util.Attributes;

import java.util.List;

@SuppressWarnings("ALL")
public abstract class RecyclerSwipeAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements SwipeItemMangerInterface, SwipeAdapterInterface {
    public SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    @Override
    public abstract V onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(V viewHolder, final int position);

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout swipeLayout) {
        mItemManger.closeAllExcept(swipeLayout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}
