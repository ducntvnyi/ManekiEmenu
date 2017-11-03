package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class Branch {

    @JsonProperty("BranchId")
    private int branchId;
    @JsonProperty("BranchName")
    private String branchName;
    @JsonProperty("BranchNo")
    private String branchNo;
    @JsonProperty("BranchParent")
    private int branchParent;

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public int getBranchParent() {
        return branchParent;
    }

    public void setBranchParent(int branchParent) {
        this.branchParent = branchParent;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", branchNo='" + branchNo + '\'' +
                ", branchParent=" + branchParent +
                '}';
    }
}
