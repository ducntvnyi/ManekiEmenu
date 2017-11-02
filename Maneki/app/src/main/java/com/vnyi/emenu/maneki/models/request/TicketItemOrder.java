package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketItemOrder {

    private String ticketId;
    private String langId;
    private String postId;
    private String tableId;
    private String getType;
    private String areaId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getGetType() {
        return getType;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "TicketItemOrder{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                ", postId='" + postId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", getType='" + getType + '\'' +
                ", areaId='" + areaId + '\'' +
                '}';
    }
}
