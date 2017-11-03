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
}
