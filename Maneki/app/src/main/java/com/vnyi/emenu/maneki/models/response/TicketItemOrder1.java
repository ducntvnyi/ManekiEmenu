package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class TicketItemOrder1 {

    @JsonProperty("TicketId")
    private int ticketId;
    @JsonProperty("ItemId")
    private int itemId;
    @JsonProperty("ItemName")
    private String itemName;
    @JsonProperty("ItemName1")
    private String itemName1;
    @JsonProperty("ItemName2")
    private String itemName2;
    @JsonProperty("UomName")
    private String uomName;
    @JsonProperty("ItemPrice")
    private double itemPrice;
    @JsonProperty("ItemRequestDetail")
    private String itemRequestDetail;
    @JsonProperty("ItemQuantity")
    private double itemQuantity;
    @JsonProperty("ItemImage")
    private String itemImage;
    @JsonProperty("ItemAmount")
    private double itemAmount;
    @JsonProperty("ItemStatus")
    private boolean itemStatus;
    @JsonProperty("UomId")
    private int uomId;
    @JsonProperty("OrderDetailId")
    private String orderDetailId;
    @JsonProperty("Rtki_AutoId")
    private int rtkiAutoId;
    @JsonProperty("ItemNo")
    private String itemNo;
    @JsonProperty("ItemSetMenu")
    private boolean itemSetMenu;
    @JsonProperty("ItemProStatus")
    private int itemProStatus;
    @JsonProperty("ItemProName")
    private String itemProName;
    @JsonProperty("ItemProColor")
    private String itemProColor;
    @JsonProperty("ItemVaTPer")
    private double itemVaTPer;
    @JsonProperty("ItemDiscountPer")
    private double itemDiscountPer;
    @JsonProperty("ItemDiscountAmount")
    private double itemDiscountAmount;
    @JsonProperty("ItemIsPromotion")
    private String itemIsPromotion;
    @JsonProperty("ItemDescription")
    private String itemDescription;
    @JsonProperty("ItemServicesChargePer")
    private double itemServicesChargePer;
    @JsonProperty("OrderTime")
    private String orderTime;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName1() {
        return itemName1;
    }

    public void setItemName1(String itemName1) {
        this.itemName1 = itemName1;
    }

    public String getItemName2() {
        return itemName2;
    }

    public void setItemName2(String itemName2) {
        this.itemName2 = itemName2;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemRequestDetail() {
        return itemRequestDetail;
    }

    public void setItemRequestDetail(String itemRequestDetail) {
        this.itemRequestDetail = itemRequestDetail;
    }

    public int getItemQuantity() {
        return (int) itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public boolean isItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(boolean itemStatus) {
        this.itemStatus = itemStatus;
    }

    public int getUomId() {
        return uomId;
    }

    public void setUomId(int uomId) {
        this.uomId = uomId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getRtkiAutoId() {
        return rtkiAutoId;
    }

    public void setRtkiAutoId(int rtkiAutoId) {
        this.rtkiAutoId = rtkiAutoId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public boolean isItemSetMenu() {
        return itemSetMenu;
    }

    public void setItemSetMenu(boolean itemSetMenu) {
        this.itemSetMenu = itemSetMenu;
    }

    public int getItemProStatus() {
        return itemProStatus;
    }

    public void setItemProStatus(int itemProStatus) {
        this.itemProStatus = itemProStatus;
    }

    public String getItemProName() {
        return itemProName;
    }

    public void setItemProName(String itemProName) {
        this.itemProName = itemProName;
    }

    public String getItemProColor() {
        return itemProColor;
    }

    public void setItemProColor(String itemProColor) {
        this.itemProColor = itemProColor;
    }

    public double getItemVaTPer() {
        return itemVaTPer;
    }

    public void setItemVaTPer(double itemVaTPer) {
        this.itemVaTPer = itemVaTPer;
    }

    public double getItemDiscountPer() {
        return itemDiscountPer;
    }

    public void setItemDiscountPer(double itemDiscountPer) {
        this.itemDiscountPer = itemDiscountPer;
    }

    public double getItemDiscountAmount() {
        return itemDiscountAmount;
    }

    public void setItemDiscountAmount(double itemDiscountAmount) {
        this.itemDiscountAmount = itemDiscountAmount;
    }

    public String getItemIsPromotion() {
        return itemIsPromotion;
    }

    public void setItemIsPromotion(String itemIsPromotion) {
        this.itemIsPromotion = itemIsPromotion;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemServicesChargePer() {
        return itemServicesChargePer;
    }

    public void setItemServicesChargePer(double itemServicesChargePer) {
        this.itemServicesChargePer = itemServicesChargePer;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "TicketItemOrder1{" +
                "ticketId=" + ticketId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemName1='" + itemName1 + '\'' +
                ", itemName2='" + itemName2 + '\'' +
                ", uomName='" + uomName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemRequestDetail='" + itemRequestDetail + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", itemImage='" + itemImage + '\'' +
                ", itemAmount=" + itemAmount +
                ", itemStatus=" + itemStatus +
                ", uomId=" + uomId +
                ", orderDetailId='" + orderDetailId + '\'' +
                ", rtkiAutoId=" + rtkiAutoId +
                ", itemNo='" + itemNo + '\'' +
                ", itemSetMenu=" + itemSetMenu +
                ", itemProStatus=" + itemProStatus +
                ", itemProName='" + itemProName + '\'' +
                ", itemProColor='" + itemProColor + '\'' +
                ", itemVaTPer=" + itemVaTPer +
                ", itemDiscountPer=" + itemDiscountPer +
                ", itemDiscountAmount=" + itemDiscountAmount +
                ", itemIsPromotion='" + itemIsPromotion + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemServicesChargePer=" + itemServicesChargePer +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
