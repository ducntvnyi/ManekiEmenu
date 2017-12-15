package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by HungND on 12/15/17.
 */

public class TableObj {

    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("TableName")
    private String tableName;
    @JsonProperty("TotalOfBill")
    private int totalOfBill;
    @JsonProperty("NumberOfPrint")
    private int numberOfPrint;
    @JsonProperty("CusQty")
    private int cusQty;
    @JsonProperty("ChildQty")
    private int childQty;
    @JsonProperty("MaleQty")
    private int maleQty;
    @JsonProperty("ForeignQty")
    private int foreignQty;
    @JsonProperty("FirstOrder")
    private String firstOrder;
    @JsonProperty("LastOrder")
    private String lastOrder;
    @JsonProperty("PaymentAmount")
    private Double paymentAmount;
    @JsonProperty("TicketId")
    private int ticketId;
    @JsonProperty("TicketCode")
    private String ticketCode;
    @JsonProperty("AreaId")
    private int areaId;
    @JsonProperty("AreaName")
    private String areaName;
    @JsonProperty("TicketTimeIn")
    private String ticketTimeIn;
    @JsonProperty("BackgroundColor")
    private String backgroundColor;
    @JsonProperty("BackgroundSelected")
    private String backgroundSelected;
    @JsonProperty("IsSeleted")
    private boolean isSeleted;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTotalOfBill() {
        return totalOfBill;
    }

    public void setTotalOfBill(int totalOfBill) {
        this.totalOfBill = totalOfBill;
    }

    public int getNumberOfPrint() {
        return numberOfPrint;
    }

    public void setNumberOfPrint(int numberOfPrint) {
        this.numberOfPrint = numberOfPrint;
    }

    public int getCusQty() {
        return cusQty;
    }

    public void setCusQty(int cusQty) {
        this.cusQty = cusQty;
    }

    public int getChildQty() {
        return childQty;
    }

    public void setChildQty(int childQty) {
        this.childQty = childQty;
    }

    public int getMaleQty() {
        return maleQty;
    }

    public void setMaleQty(int maleQty) {
        this.maleQty = maleQty;
    }

    public int getForeignQty() {
        return foreignQty;
    }

    public void setForeignQty(int foreignQty) {
        this.foreignQty = foreignQty;
    }

    public String getFirstOrder() {
        return firstOrder;
    }

    public void setFirstOrder(String firstOrder) {
        this.firstOrder = firstOrder;
    }

    public String getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(String lastOrder) {
        this.lastOrder = lastOrder;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTicketTimeIn() {
        return ticketTimeIn;
    }

    public void setTicketTimeIn(String ticketTimeIn) {
        this.ticketTimeIn = ticketTimeIn;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundSelected() {
        return backgroundSelected;
    }

    public void setBackgroundSelected(String backgroundSelected) {
        this.backgroundSelected = backgroundSelected;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    @Override
    public String toString() {
        return "TableObj{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", totalOfBill=" + totalOfBill +
                ", numberOfPrint=" + numberOfPrint +
                ", cusQty=" + cusQty +
                ", childQty=" + childQty +
                ", maleQty=" + maleQty +
                ", foreignQty=" + foreignQty +
                ", firstOrder='" + firstOrder + '\'' +
                ", lastOrder='" + lastOrder + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", ticketId=" + ticketId +
                ", ticketCode='" + ticketCode + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", ticketTimeIn='" + ticketTimeIn + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", backgroundSelected='" + backgroundSelected + '\'' +
                ", isSeleted=" + isSeleted +
                '}';
    }
}
