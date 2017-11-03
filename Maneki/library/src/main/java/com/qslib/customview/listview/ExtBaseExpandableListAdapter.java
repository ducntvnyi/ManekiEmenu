package com.qslib.customview.listview;

import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by DangPP on 7/11/2016.
 */
public abstract class ExtBaseExpandableListAdapter<T extends BaseExpandableEntity<K>, K> extends BaseExpandableListAdapter {
    protected List<T> items;

    public ExtBaseExpandableListAdapter(List<T> items) {
        this.items = items;
    }

    public void notifyDataSetChangedExtBaseExpandableListAdapter(List<T> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        try {
            return this.items == null ? 0 : this.items.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public T getGroup(int groupPosition) {
        try {
            return getGroupCount() <= 0 ? null : this.items.get(groupPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return getGroup(groupPosition) == null || getGroup(groupPosition).getLists() == null ? 0
                    : getGroup(groupPosition).getLists().size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public K getChild(int groupPosition, int childPosition) {
        try {
            return getGroup(groupPosition) == null || getGroup(groupPosition).getLists() == null ? null
                    : getGroup(groupPosition).getLists().get(childPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
