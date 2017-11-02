package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class GetTableNameConfigValue {

    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "GetTableNameConfigValue{" +
                "tableId='" + tableId + '\'' +
                '}';
    }
}
