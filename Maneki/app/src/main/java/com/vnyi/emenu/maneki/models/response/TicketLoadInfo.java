package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class TicketLoadInfo {

    @JsonProperty("TicketId")
    private int ticketId;
    @JsonProperty("CustomerName")
    private String customerName;
    @JsonProperty("TimeIn")
    private String timeIn;
    @JsonProperty("TotalAmount")
    private double totalAmount;
    @JsonProperty("CustomerQty")
    private int customerQty;
    @JsonProperty("OpenBy")
    private String openBy;
    @JsonProperty("TableID")
    private int tableID;
    @JsonProperty("TableName")
    private String tableName;
    @JsonProperty("NumberOfSeat")
    private int numberOfSeat;
    @JsonProperty("TableNote")
    private String tableNote;
    @JsonProperty("IsSmokingTable")
    private Boolean isSmokingTable;
}
