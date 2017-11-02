package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketSendItemOrder {

    private String ticketId;
    private String userId;
    private String posId;
    private String langId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
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
                ", posId='" + posId + '\'' +
                ", userId='" + userId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
