package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class TableName {
    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("TableName")
    private String tableName;


    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TableName{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
