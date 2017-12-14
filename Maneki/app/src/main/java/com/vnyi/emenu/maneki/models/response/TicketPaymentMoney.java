package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/14/17.
 */

public class TicketPaymentMoney {
    @JsonProperty("TicketId")
    private int ticketId;
    @JsonProperty("TicketCode")
    private String ticketCode;
    @JsonProperty("ItemAmount")
    private double itemAmount;
    @JsonProperty("DiscountPer")
    private double discountPer;
    @JsonProperty("DiscountTicket")
    private double discountTicket;
    @JsonProperty("DiscountItem")
    private double discountItem;
    @JsonProperty("DiscountSum")
    private double discountSum;
    @JsonProperty("VatPer")
    private double vatPer;
    @JsonProperty("VatAmount")
    private double vatAmount;
    @JsonProperty("ServicePer")
    private double servicePer;
    @JsonProperty("ServiceAmount")
    private double serviceAmount;
    @JsonProperty("ExcisePer")
    private double excisePer;
    @JsonProperty("ExciseAmount")
    private double exciseAmount;
    @JsonProperty("TotalAmount")
    private double totalAmount;
    @JsonProperty("VipCardCode")
    private String vipCardCode;
    @JsonProperty("CustomerCompany")
    private String customerCompany;
    @JsonProperty("CustomerTax")
    private String customerTax;
    @JsonProperty("CustomerAddress")
    private String customerAddress;
    @JsonProperty("TicketNode")
    private String ticketNode;
    @JsonProperty("TableName")
    private String tableName;

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

    public double getItemAmount() {
        return itemAmount;
    }

    public String getItemMoney() {
        String firstNumberAsString = String.format ("%.0f", itemAmount);
        return firstNumberAsString + " VND";

    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(double discountPer) {
        this.discountPer = discountPer;
    }

    public double getDiscountTicket() {
        return discountTicket;
    }

    public void setDiscountTicket(double discountTicket) {
        this.discountTicket = discountTicket;
    }

    public double getDiscountItem() {
        return discountItem;
    }
    public String getDiscount(){
        String firstNumberAsString = String.format ("%.0f", discountItem);
        return firstNumberAsString + " VND";
    }

    public void setDiscountItem(double discountItem) {
        this.discountItem = discountItem;
    }

    public double getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public double getVatPer() {
        return vatPer;
    }

    public void setVatPer(double vatPer) {
        this.vatPer = vatPer;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public String getVAT(){
        String firstNumberAsString = String.format ("%.0f", vatAmount);
        return firstNumberAsString + " VND";
    }
    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getServicePer() {
        return servicePer;
    }

    public void setServicePer(double servicePer) {
        this.servicePer = servicePer;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public double getExcisePer() {
        return excisePer;
    }

    public void setExcisePer(double excisePer) {
        this.excisePer = excisePer;
    }

    public double getExciseAmount() {
        return exciseAmount;
    }

    public void setExciseAmount(double exciseAmount) {
        this.exciseAmount = exciseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getTotalMoney() {
        String firstNumberAsString = String.format ("%.0f", totalAmount);
        return firstNumberAsString + " VND";
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getVipCardCode() {
        return vipCardCode;
    }

    public void setVipCardCode(String vipCardCode) {
        this.vipCardCode = vipCardCode;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerTax() {
        return customerTax;
    }

    public void setCustomerTax(String customerTax) {
        this.customerTax = customerTax;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getTicketNode() {
        return ticketNode;
    }

    public void setTicketNode(String ticketNode) {
        this.ticketNode = ticketNode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TicketPaymentMoney{" +
                "ticketId=" + ticketId +
                ", ticketCode='" + ticketCode + '\'' +
                ", itemAmount=" + itemAmount +
                ", discountPer=" + discountPer +
                ", discountTicket=" + discountTicket +
                ", discountItem=" + discountItem +
                ", discountSum=" + discountSum +
                ", vatPer=" + vatPer +
                ", vatAmount=" + vatAmount +
                ", servicePer=" + servicePer +
                ", serviceAmount=" + serviceAmount +
                ", excisePer=" + excisePer +
                ", exciseAmount=" + exciseAmount +
                ", totalAmount=" + totalAmount +
                ", vipCardCode='" + vipCardCode + '\'' +
                ", customerCompany='" + customerCompany + '\'' +
                ", customerTax='" + customerTax + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", ticketNode='" + ticketNode + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
