package com.qslib.collection;

import java.util.List;

/**
 * Created by Dang on 5/26/2016.
 */
public class MapEntity<T> {
    private String key;
    private List<T> datas;

    public MapEntity() {
    }

    public MapEntity(String key, List<T> datas) {
        this.key = key;
        this.datas = datas;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "MapEntity{" +
                "key='" + key + '\'' +
                ", datas=" + datas +
                '}';
    }
}
