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
    private boolean isSmokingTable;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCustomerQty() {
        return customerQty;
    }

    public void setCustomerQty(int customerQty) {
        this.customerQty = customerQty;
    }

    public String getOpenBy() {
        return openBy;
    }

    public void setOpenBy(String openBy) {
        this.openBy = openBy;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public String getTableNote() {
        return tableNote;
    }

    public void setTableNote(String tableNote) {
        this.tableNote = tableNote;
    }

    public boolean isSmokingTable() {
        return isSmokingTable;
    }

    public void setSmokingTable(boolean smokingTable) {
        isSmokingTable = smokingTable;
    }

    @Override
    public String toString() {
        return "TicketLoadInfo{" +
                "ticketId=" + ticketId +
                ", customerName='" + customerName + '\'' +
                ", timeIn='" + timeIn + '\'' +
                ", totalAmount=" + totalAmount +
                ", customerQty=" + customerQty +
                ", openBy='" + openBy + '\'' +
                ", tableID=" + tableID +
                ", tableName='" + tableName + '\'' +
                ", numberOfSeat=" + numberOfSeat +
                ", tableNote='" + tableNote + '\'' +
                ", isSmokingTable=" + isSmokingTable +
                '}';
    }
}
