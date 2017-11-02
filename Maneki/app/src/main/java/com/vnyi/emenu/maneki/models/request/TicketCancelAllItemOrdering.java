package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketCancelAllItemOrdering {

    private String ticketId;
    private String userId;
    private String langId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
        return "TicketCancelItem{" +
                "ticketId='" + ticketId + '\'' +
                ", userId='" + userId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
