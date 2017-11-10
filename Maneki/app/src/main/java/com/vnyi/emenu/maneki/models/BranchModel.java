package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.Branch;

import java.util.List;

/**
 * Created by Hungnd on 11/10/17.
 */

public class BranchModel {

    private List<Branch> branches;


    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "BranchModel{" +
                "branches=" + branches.toString() +
                '}';
    }
}
