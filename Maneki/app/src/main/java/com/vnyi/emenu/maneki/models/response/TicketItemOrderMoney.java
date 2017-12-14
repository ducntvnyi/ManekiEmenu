package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;

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
    public String getItemMoney(){
        String firstNumberAsString =   String.format ("%.0f", itemAmount);
        return firstNumberAsString + " VND";
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getDiscount(){
        String firstNumberAsString = String.format ("%.0f", discountAmount);
        return firstNumberAsString + " VND";
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getvATAmount() {
        return vATAmount;
    }
    public String getVat(){
        String firstNumberAsString = String.format ("%.0f", vATAmount);
        return firstNumberAsString + " VND";
    }

    public void setvATAmount(double vATAmount) {
        this.vATAmount = vATAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public String getTotalMoney(){
        String firstNumberAsString = String.format ("%.0f", totalAmount);
        return firstNumberAsString + " VND";
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
