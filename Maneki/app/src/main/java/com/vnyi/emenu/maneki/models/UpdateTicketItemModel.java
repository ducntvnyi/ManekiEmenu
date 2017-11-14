package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;

import java.util.List;

/**
 * Created by Hungnd on 11/14/17.
 */

public class UpdateTicketItemModel {

    private int ticketId;
    private ConfigValueModel configValueModel;
    private List<ItemCategoryDetail> itemCategoryDetails;

    public UpdateTicketItemModel() {

    }

    public UpdateTicketItemModel(int ticketId, ConfigValueModel configValueModel, List<ItemCategoryDetail> itemCategoryDetails) {
        this.ticketId = ticketId;
        this.configValueModel = configValueModel;
        this.itemCategoryDetails = itemCategoryDetails;
    }

    public ConfigValueModel getConfigValueModel() {
        return configValueModel;
    }

    public void setConfigValueModel(ConfigValueModel configValueModel) {
        this.configValueModel = configValueModel;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public List<ItemCategoryDetail> getItemCategoryDetails() {
        return itemCategoryDetails;
    }

    public void setItemCategoryDetails(List<ItemCategoryDetail> itemCategoryDetails) {
        this.itemCategoryDetails = itemCategoryDetails;
    }

    @Override
    public String toString() {
        return "UpdateTicketItemModel{" +
                "configValueModel=" + configValueModel.toString() +
                "ticketId=" + ticketId +
                ", itemCategoryDetails=" + itemCategoryDetails.toString() +
                '}';
    }
}
