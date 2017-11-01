package com.qslib.listview;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Dang on 5/27/2016.
 */

public abstract class PagingListViewAdapter<T> extends BaseAdapter {
    protected List<T> items = null;

    /**
     * @param items
     */
    public PagingListViewAdapter(List<T> items) {
        this.items = items;
    }

    /**
     * @param newItem
     */
    protected final void addNewItem(T newItem) {
        try {
            if (items != null) {
                items.add(newItem);
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param newItems
     */
    protected final void addNewItems(List<T> newItems) {
        try {
            if (items != null) {
                items.addAll(newItems);
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove item
     *
     * @param item
     */
    protected final void removeItem(T item) {
        try {
            if (items != null) {
                items.remove(item);
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove items
     *
     * @param items
     */
    protected final void removeItems(List<T> items) {
        try {
            if (this.items != null) {
                this.items.removeAll(items);
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * clear data
     */
    protected final void clearList() {
        try {
            if (items != null) {
                items.clear();
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return items == null ? null : items.get(position);
    }
}
