package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/14/17.
 */

public class TicketItemOrderMoney {


    @JsonProperty("ItemAmount")
    private double itemAmount;
    @JsonProperty("DiscountAmount")
    private double discountAmount;
    @JsonProperty("VATAmount")
    private double vATAmount;
    @JsonProperty("TotalAmount")
    private double totalAmount;

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getvATAmount() {
        return vATAmount;
    }

    public void setvATAmount(double vATAmount) {
        this.vATAmount = vATAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TicketItemOrderMoney{" +
                "itemAmount=" + itemAmount +
                ", discountAmount=" + discountAmount +
                ", vATAmount=" + vATAmount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
