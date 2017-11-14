package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.TicketItemOrder1;
import com.vnyi.emenu.maneki.models.response.TicketItemOrderMoney;

import java.util.List;

/**
 * Created by Hungnd on 11/14/17.
 */

public class TicketItemOrderModel {

    private List<TicketItemOrder1> ticketItemOrders;

    private TicketItemOrderMoney ticketItemOrderMoney;

    public TicketItemOrderMoney getTicketItemOrderMoney() {
        return ticketItemOrderMoney;
    }

    public void setTicketItemOrderMoney(TicketItemOrderMoney ticketItemOrderMoney) {
        this.ticketItemOrderMoney = ticketItemOrderMoney;
    }

    public List<TicketItemOrder1> getTicketItemOrders() {
        return ticketItemOrders;
    }

    public void setTicketItemOrders(List<TicketItemOrder1> ticketItemOrders) {
        this.ticketItemOrders = ticketItemOrders;
    }

    @Override
    public String toString() {
        return "TicketItemOrderModel{" +
                "ticketItemOrders=" + ticketItemOrders +
                ", ticketItemOrderMoney=" + ticketItemOrderMoney +
                '}';
    }
}
