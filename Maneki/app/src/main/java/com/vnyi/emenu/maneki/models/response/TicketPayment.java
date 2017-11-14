package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/14/17.
 */

public class TicketPayment {
    @JsonProperty("ItemName")
    private String itemName;
    @JsonProperty("ItemQuantity")
    private double itemQuantity;
    @JsonProperty("ItemUomName")
    private String itemUomName;
    @JsonProperty("ItemPrice")
    private double itemPrice;
    @JsonProperty("DiscountPer")
    private double discountPer;
    @JsonProperty("ItemAmount")
    private double itemAmount;
    @JsonProperty("ItemImageUrl")
    private String itemImageUrl;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUomName() {
        return itemUomName;
    }

    public void setItemUomName(String itemUomName) {
        this.itemUomName = itemUomName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(double discountPer) {
        this.discountPer = discountPer;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    @Override
    public String toString() {
        return "TicketPayment{" +
                "itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", itemUomName='" + itemUomName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPer=" + discountPer +
                ", itemAmount=" + itemAmount +
                ", itemImageUrl='" + itemImageUrl + '\'' +
                '}';
    }
}
