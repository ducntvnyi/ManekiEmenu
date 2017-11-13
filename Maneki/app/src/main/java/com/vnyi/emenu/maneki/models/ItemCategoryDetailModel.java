package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;

import java.util.List;

/**
 * Created by Hungnd on 11/13/17.
 */

public class ItemCategoryDetailModel {
    private List<ItemCategoryDetail> itemCategoryDetails;

    public List<ItemCategoryDetail> getItemCategoryDetails() {
        return itemCategoryDetails;
    }

    public void setItemCategoryDetails(List<ItemCategoryDetail> itemCategoryDetails) {
        this.itemCategoryDetails = itemCategoryDetails;
    }

    @Override
    public String toString() {
        return "ItemCategoryDetailModel{" +
                "itemCategoryDetails=" + itemCategoryDetails.toString() +
                '}';
    }
}
