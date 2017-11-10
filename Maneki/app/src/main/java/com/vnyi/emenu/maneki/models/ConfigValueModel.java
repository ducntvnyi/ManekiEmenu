package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.config.ConfigModel;

/**
 * Created by Hungnd on 11/9/17.
 */

public class ConfigValueModel {

    private String linkServer;
    private ConfigModel branch;
    private ConfigModel tableName;
    private ConfigModel userOrder;
    private ConfigModel loadListParent;
    private ConfigModel linkSaleOff;
    private ConfigModel linkUserApp;
    private ConfigModel changeTable;
    private ConfigModel numbTableShow;

    public String getLinkServer() {
        return linkServer;
    }

    public void setLinkServer(String linkServer) {
        this.linkServer = linkServer;
    }

    public ConfigModel getBranch() {
        return branch;
    }

    public void setBranch(ConfigModel branch) {
        this.branch = branch;
    }

    public ConfigModel getTableName() {
        return tableName;
    }

    public void setTableName(ConfigModel tableName) {
        this.tableName = tableName;
    }

    public ConfigModel getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(ConfigModel userOrder) {
        this.userOrder = userOrder;
    }

    public ConfigModel getLoadListParent() {
        return loadListParent;
    }

    public void setLoadListParent(ConfigModel loadListParent) {
        this.loadListParent = loadListParent;
    }

    public ConfigModel getLinkSaleOff() {
        return linkSaleOff;
    }

    public void setLinkSaleOff(ConfigModel linkSaleOff) {
        this.linkSaleOff = linkSaleOff;
    }

    public ConfigModel getLinkUserApp() {
        return linkUserApp;
    }

    public void setLinkUserApp(ConfigModel linkUserApp) {
        this.linkUserApp = linkUserApp;
    }

    public ConfigModel getChangeTable() {
        return changeTable;
    }

    public void setChangeTable(ConfigModel changeTable) {
        this.changeTable = changeTable;
    }

    public ConfigModel getNumbTableShow() {
        return numbTableShow;
    }

    public void setNumbTableShow(ConfigModel numbTableShow) {
        this.numbTableShow = numbTableShow;
    }

    @Override
    public String toString() {
        return "ConfigValueModel{" +
                "linkServer='" + linkServer + '\'' +
                ", branch=" + branch.toString() +
                ", tableName=" + tableName.toString() +
                ", userOrder=" + userOrder.toString() +
                ", loadListParent=" + loadListParent.toString() +
                ", linkSaleOff=" + linkSaleOff.toString() +
                ", linkUserApp=" + linkUserApp.toString() +
                ", changeTable=" + changeTable.toString() +
                ", numbTableShow=" + numbTableShow.toString() +
                '}';
    }
}
