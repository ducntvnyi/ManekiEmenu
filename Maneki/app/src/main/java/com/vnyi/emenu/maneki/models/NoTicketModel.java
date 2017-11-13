package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;

import java.util.List;

/**
 * Created by Hungnd on 11/13/17.
 */

public class NoTicketModel {

    private List<ItemCategoryNoListNote> itemCategoryNoListNotes;

    public List<ItemCategoryNoListNote> getItemCategoryNoListNotes() {
        return itemCategoryNoListNotes;
    }

    public void setItemCategoryNoListNotes(List<ItemCategoryNoListNote> itemCategoryNoListNotes) {
        this.itemCategoryNoListNotes = itemCategoryNoListNotes;
    }

    @Override
    public String toString() {
        return "NoTicketModel{" +
                "itemCategoryNoListNotes=" + itemCategoryNoListNotes.toString() +
                '}';
    }
}
