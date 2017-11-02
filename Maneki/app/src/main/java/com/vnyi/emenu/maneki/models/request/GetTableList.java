package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class GetTableList {

    private String reaAutoId;
    private String listType;
    private String branchId;
    private String langId;

    public String getReaAutoId() {
        return reaAutoId;
    }

    public void setReaAutoId(String reaAutoId) {
        this.reaAutoId = reaAutoId;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "GetTableList{" +
                "reaAutoId='" + reaAutoId + '\'' +
                ", listType='" + listType + '\'' +
                ", branchId='" + branchId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
