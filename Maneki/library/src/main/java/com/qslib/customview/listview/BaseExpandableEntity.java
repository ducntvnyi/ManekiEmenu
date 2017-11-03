package com.qslib.customview.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dang on 7/27/2016.
 */
public class BaseExpandableEntity<K> implements Serializable {
    protected List<K> lists;

    public BaseExpandableEntity() {
    }

    public BaseExpandableEntity(List<K> lists) {
        this.lists = lists;
    }

    public List<K> getLists() {
        return this.lists == null ? this.lists = new ArrayList<>() : this.lists;
    }

    public void setLists(List<K> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "BaseExpandableEntity{" +
                "lists=" + lists +
                '}';
    }
}
