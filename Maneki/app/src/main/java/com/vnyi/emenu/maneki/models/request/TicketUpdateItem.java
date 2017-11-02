package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketUpdateItem {

    private String ticketId;
    private String langId;
    private String postId;
    private String tableId;
    private String OrderDetailId;
    private String ItemId;
    private String UomId;
    private String ItemQuantity;
    private String ItemChoiceAmount;
    private String DiscountPer;
    private String ItemPrice;
    private String ItemRequestDetail;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getUomId() {
        return UomId;
    }

    public void setUomId(String uomId) {
        UomId = uomId;
    }

    public String getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public String getItemChoiceAmount() {
        return ItemChoiceAmount;
    }

    public void setItemChoiceAmount(String itemChoiceAmount) {
        ItemChoiceAmount = itemChoiceAmount;
    }

    public String getDiscountPer() {
        return DiscountPer;
    }

    public void setDiscountPer(String discountPer) {
        DiscountPer = discountPer;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemRequestDetail() {
        return ItemRequestDetail;
    }

    public void setItemRequestDetail(String itemRequestDetail) {
        ItemRequestDetail = itemRequestDetail;
    }

    @Override
    public String toString() {
        return "TicketUpdateItem{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                ", postId='" + postId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", OrderDetailId='" + OrderDetailId + '\'' +
                ", ItemId='" + ItemId + '\'' +
                ", UomId='" + UomId + '\'' +
                ", ItemQuantity='" + ItemQuantity + '\'' +
                ", ItemChoiceAmount='" + ItemChoiceAmount + '\'' +
                ", DiscountPer='" + DiscountPer + '\'' +
                ", ItemPrice='" + ItemPrice + '\'' +
                ", ItemRequestDetail='" + ItemRequestDetail + '\'' +
                '}';
    }
}
