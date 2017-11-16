package com.vnyi.emenu.maneki.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class Table {

    @JsonProperty("RET_AUTOID")
    private int retAutoId;
    @JsonProperty("RET_DEFINEID")
    private String retDefineId;

    private boolean isSelected;

    public int getRetAutoId() {
        return retAutoId;
    }

    public void setRetAutoId(int retAutoId) {
        this.retAutoId = retAutoId;
    }

    public String getRetDefineId() {
        return retDefineId;
    }

    public void setRetDefineId(String retDefineId) {
        this.retDefineId = retDefineId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Table{" +
                "retAutoId=" + retAutoId +
                ", retDefineId='" + retDefineId + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
