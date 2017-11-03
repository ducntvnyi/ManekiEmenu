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

}
