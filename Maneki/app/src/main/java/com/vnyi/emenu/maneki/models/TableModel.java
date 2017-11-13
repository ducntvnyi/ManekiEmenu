package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.Table;

import java.util.List;

/**
 * Created by Hungnd on 11/13/17.
 */

public class TableModel {

    private List<Table> tables;

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "tables=" + tables.toString() +
                '}';
    }
}
