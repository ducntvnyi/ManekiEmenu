package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketSendRequestWaiter {

    private String ticketId;
    private String requestDetail;
    private String langId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "TicketSendRequestWaiter{" +
                "ticketId='" + ticketId + '\'' +
                ", requestDetail='" + requestDetail + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
