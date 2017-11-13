package com.vnyi.emenu.maneki.models;

/**
 * Created by Hungnd on 11/13/17.
 */

public class TicketModel {

    private int ticketId;
    private int tableId;
    private int langId;
    private int posId;
    private int userId;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TicketModel{" +
                "ticketId=" + ticketId +
                ", tableId=" + tableId +
                ", langId=" + langId +
                ", posId=" + posId +
                ", userId=" + userId +
                '}';
    }
}
