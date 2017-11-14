package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.TicketPayment;
import com.vnyi.emenu.maneki.models.response.TicketPaymentMoney;

import java.util.List;

/**
 * Created by Hungnd on 11/14/17.
 */

public class TicketPaymentModel {

    private List<TicketPayment> ticketPayments;

    private TicketPaymentMoney ticketPaymentMoney;

    public List<TicketPayment> getTicketPayments() {
        return ticketPayments;
    }

    public void setTicketPayments(List<TicketPayment> ticketPayments) {
        this.ticketPayments = ticketPayments;
    }

    public TicketPaymentMoney getTicketPaymentMoney() {
        return ticketPaymentMoney;
    }

    public void setTicketPaymentMoney(TicketPaymentMoney ticketPaymentMoney) {
        this.ticketPaymentMoney = ticketPaymentMoney;
    }

    @Override
    public String toString() {
        return "TicketPaymentModel{" +
                "ticketPayments=" + ticketPayments.toString() +
                ", ticketPaymentMoney=" + ticketPaymentMoney +
                '}';
    }
}
