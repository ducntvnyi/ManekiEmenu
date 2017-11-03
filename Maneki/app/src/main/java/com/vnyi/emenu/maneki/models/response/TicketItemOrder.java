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
    
}
