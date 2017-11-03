package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class TicketItemOrder {

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
    private String totalAmount;

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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TicketItemOrder{" +
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
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
