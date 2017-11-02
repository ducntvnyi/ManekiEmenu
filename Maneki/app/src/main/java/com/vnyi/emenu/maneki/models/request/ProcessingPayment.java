package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class ProcessingPayment {

    private String ticketId;
    private String langId;

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

    @Override
    public String toString() {
        return "PaymentInvoice{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
