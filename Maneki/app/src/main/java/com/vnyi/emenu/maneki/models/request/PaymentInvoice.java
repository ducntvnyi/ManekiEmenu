package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class PaymentInvoice {

    private String ticketId;
    private String langId;
    private String isInvoice;

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

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    @Override
    public String toString() {
        return "PaymentInvoice{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                ", isInvoice='" + isInvoice + '\'' +
                '}';
    }
}
