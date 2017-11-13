package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/13/17.
 */

public class TicketUpdateItem {

    @JsonProperty("TicketId")
    private int ticketId;
    @JsonProperty("ItemId")
    private int itemId;
    @JsonProperty("ItemNo")
    private String itemNo;
    @JsonProperty("ItemName")
    private String itemName;
    @JsonProperty("ItemName1")
    private String itemName1;
    @JsonProperty("ItemName2")
    private String itemName2;
    @JsonProperty("UomId")
    private int uomId;
    @JsonProperty("UomName")
    private String uomName;
    @JsonProperty("ItemRequestDetail")
    private String itemRequestDetail;
    @JsonProperty("ItemPrice")
    private double itemPrice;
    @JsonProperty("ItemQuantity")
    private double itemQuantity;
    @JsonProperty("DiscountPer")
    private double discountPer;
    @JsonProperty("ItemDiscountDetail")
    private double itemDiscountDetail;
    @JsonProperty("ItemChoiceAmount")
    private double itemChoiceAmount;
    @JsonProperty("ItemAmount")
    private double itemAmount;
    @JsonProperty("ItemDiscountTicket")
    private double itemDiscountTicket;
    @JsonProperty("ItemTotalDiscount")
    private double itemTotalDiscount;
    @JsonProperty("ItemServiceChargePer")
    private double itemServiceChargePer;
    @JsonProperty("ItemServiceChargeAmount")
    private double itemServiceChargeAmount;
    @JsonProperty("ItemExciseTax")
    private double itemExciseTax;
    @JsonProperty("ItemExciseTaxAmount")
    private double itemExciseTaxAmount;
    @JsonProperty("ItemVATper")
    private double itemVATper;
    @JsonProperty("ItemVATAmount")
    private double itemVATAmount;
    @JsonProperty("ItemTotalAmount")
    private double itemTotalAmount;
    @JsonProperty("ItemStatus")
    private boolean itemStatus;
    @JsonProperty("OrderDetailId")
    private int orderDetailId;
    @JsonProperty("Rtki_AutoId")
    private String rtkiAutoId;
    @JsonProperty("ItemSetMenu")
    private boolean itemSetMenu;
    @JsonProperty("ItemImage")
    private String itemImage;
    @JsonProperty("ItemProStatus")
    private String itemProStatus;
    @JsonProperty("ItemProName")
    private String itemProName;
    @JsonProperty("ItemProColor")
    private String itemProColor;
    @JsonProperty("TotalQtyOrdering")
    private double totalQtyOrdering;
    @JsonProperty("TotalItemAmount")
    private double totalItemAmount;

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

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
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

    public int getUomId() {
        return uomId;
    }

    public void setUomId(int uomId) {
        this.uomId = uomId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getItemRequestDetail() {
        return itemRequestDetail;
    }

    public void setItemRequestDetail(String itemRequestDetail) {
        this.itemRequestDetail = itemRequestDetail;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(double discountPer) {
        this.discountPer = discountPer;
    }

    public double getItemDiscountDetail() {
        return itemDiscountDetail;
    }

    public void setItemDiscountDetail(double itemDiscountDetail) {
        this.itemDiscountDetail = itemDiscountDetail;
    }

    public double getItemChoiceAmount() {
        return itemChoiceAmount;
    }

    public void setItemChoiceAmount(double itemChoiceAmount) {
        this.itemChoiceAmount = itemChoiceAmount;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getItemDiscountTicket() {
        return itemDiscountTicket;
    }

    public void setItemDiscountTicket(double itemDiscountTicket) {
        this.itemDiscountTicket = itemDiscountTicket;
    }

    public double getItemTotalDiscount() {
        return itemTotalDiscount;
    }

    public void setItemTotalDiscount(double itemTotalDiscount) {
        this.itemTotalDiscount = itemTotalDiscount;
    }

    public double getItemServiceChargePer() {
        return itemServiceChargePer;
    }

    public void setItemServiceChargePer(double itemServiceChargePer) {
        this.itemServiceChargePer = itemServiceChargePer;
    }

    public double getItemServiceChargeAmount() {
        return itemServiceChargeAmount;
    }

    public void setItemServiceChargeAmount(double itemServiceChargeAmount) {
        this.itemServiceChargeAmount = itemServiceChargeAmount;
    }

    public double getItemExciseTax() {
        return itemExciseTax;
    }

    public void setItemExciseTax(double itemExciseTax) {
        this.itemExciseTax = itemExciseTax;
    }

    public double getItemExciseTaxAmount() {
        return itemExciseTaxAmount;
    }

    public void setItemExciseTaxAmount(double itemExciseTaxAmount) {
        this.itemExciseTaxAmount = itemExciseTaxAmount;
    }

    public double getItemVATper() {
        return itemVATper;
    }

    public void setItemVATper(double itemVATper) {
        this.itemVATper = itemVATper;
    }

    public double getItemVATAmount() {
        return itemVATAmount;
    }

    public void setItemVATAmount(double itemVATAmount) {
        this.itemVATAmount = itemVATAmount;
    }

    public double getItemTotalAmount() {
        return itemTotalAmount;
    }

    public void setItemTotalAmount(double itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public boolean isItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(boolean itemStatus) {
        this.itemStatus = itemStatus;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getRtkiAutoId() {
        return rtkiAutoId;
    }

    public void setRtkiAutoId(String rtkiAutoId) {
        this.rtkiAutoId = rtkiAutoId;
    }

    public boolean isItemSetMenu() {
        return itemSetMenu;
    }

    public void setItemSetMenu(boolean itemSetMenu) {
        this.itemSetMenu = itemSetMenu;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemProStatus() {
        return itemProStatus;
    }

    public void setItemProStatus(String itemProStatus) {
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

    public double getTotalQtyOrdering() {
        return totalQtyOrdering;
    }

    public void setTotalQtyOrdering(double totalQtyOrdering) {
        this.totalQtyOrdering = totalQtyOrdering;
    }

    public double getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(double totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    @Override
    public String toString() {
        return "TicketUpdateItem{" +
                "ticketId=" + ticketId +
                ", itemId=" + itemId +
                ", itemNo='" + itemNo + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemName1='" + itemName1 + '\'' +
                ", itemName2='" + itemName2 + '\'' +
                ", uomId=" + uomId +
                ", uomName='" + uomName + '\'' +
                ", itemRequestDetail='" + itemRequestDetail + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                ", discountPer=" + discountPer +
                ", itemDiscountDetail=" + itemDiscountDetail +
                ", itemChoiceAmount=" + itemChoiceAmount +
                ", itemAmount=" + itemAmount +
                ", itemDiscountTicket=" + itemDiscountTicket +
                ", itemTotalDiscount=" + itemTotalDiscount +
                ", itemServiceChargePer=" + itemServiceChargePer +
                ", itemServiceChargeAmount=" + itemServiceChargeAmount +
                ", itemExciseTax=" + itemExciseTax +
                ", itemExciseTaxAmount=" + itemExciseTaxAmount +
                ", itemVATper=" + itemVATper +
                ", itemVATAmount=" + itemVATAmount +
                ", itemTotalAmount=" + itemTotalAmount +
                ", itemStatus=" + itemStatus +
                ", orderDetailId=" + orderDetailId +
                ", rtkiAutoId='" + rtkiAutoId + '\'' +
                ", itemSetMenu=" + itemSetMenu +
                ", itemImage='" + itemImage + '\'' +
                ", itemProStatus='" + itemProStatus + '\'' +
                ", itemProName='" + itemProName + '\'' +
                ", itemProColor='" + itemProColor + '\'' +
                ", totalQtyOrdering=" + totalQtyOrdering +
                ", totalItemAmount=" + totalItemAmount +
                '}';
    }
}
