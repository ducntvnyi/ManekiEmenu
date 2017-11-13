package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;

import java.util.List;

/**
 * Created by Hungnd on 11/13/17.
 */

public class TicketLoadInfoModel {

    private List<TicketLoadInfo> ticketLoadInfoList;

    public List<TicketLoadInfo> getTicketLoadInfoList() {
        return ticketLoadInfoList;
    }

    public void setTicketLoadInfoList(List<TicketLoadInfo> ticketLoadInfoList) {
        this.ticketLoadInfoList = ticketLoadInfoList;
    }

    @Override
    public String toString() {
        return "TicketLoadInfoModel{" +
                "ticketLoadInfoList=" + ticketLoadInfoList.toString() +
                '}';
    }
}
