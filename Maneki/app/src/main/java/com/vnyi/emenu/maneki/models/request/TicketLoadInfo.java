package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketLoadInfo {

    private String ticketId;
    private String tableId;
    private String userId;
    private String langId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "TicketLoadInfo{" +
                "ticketId='" + ticketId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", userId='" + userId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
