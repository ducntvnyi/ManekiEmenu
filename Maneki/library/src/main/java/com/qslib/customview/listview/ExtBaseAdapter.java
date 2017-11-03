package com.qslib.customview.listview;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Dang on 5/27/2016.
 */

public abstract class ExtBaseAdapter<T> extends BaseAdapter {
    protected List<T> items = null;

    /**
     * @param items
     */
    public ExtBaseAdapter(List<T> items) {
        this.items = items;
    }

    /**
     * notification data
     *
     * @param items
     */
    public void notifyDataSetChangedExtBaseAdapter(List<T> items) {
        try {
            this.items = items;
            this.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param newItem
     */
    public void addNewItem(T newItem) {
        try {
            if (this.items != null) {
                this.items.add(newItem);
                this.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param newItems
     */
    public void addNewItems(List<T> newItems) {
        try {
            if (this.items != null) {
                this.items.addAll(newItems);
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
    public void removeItem(T item) {
        try {
            if (this.items != null) {
                this.items.remove(item);
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
    public void removeItems(List<T> items) {
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
    public void clearList() {
        try {
            if (this.items != null) {
                this.items.clear();
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
        return this.items == null ? 0 : this.items.size();
    }

    @Override
    public T getItem(int position) {
        try {
            return this.items == null ? null : this.items.get(position);
        } catch (Exception e) {
        }

        return null;
    }
}
