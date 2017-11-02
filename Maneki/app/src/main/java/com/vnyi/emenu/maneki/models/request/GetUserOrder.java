package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class GetUserOrder {

    private String branchId;
    private String langId;

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
        return "GetUserOrder{" +
                "branchId='" + branchId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
