package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketCheckStatusBill {

    private String ticketId;
    private String langId;
    private String yourVersion;

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

    public String getYourVersion() {
        return yourVersion;
    }

    public void setYourVersion(String yourVersion) {
        this.yourVersion = yourVersion;
    }

    @Override
    public String toString() {
        return "TicketCheckStatusBill{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                ", yourVersion='" + yourVersion + '\'' +
                '}';
    }
}
