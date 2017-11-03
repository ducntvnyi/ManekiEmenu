package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class ItemCategoryDetail {
    @JsonProperty("ItemId")
    private int itemId;
    @JsonProperty("ItemNo")
    private String itemNo;
    @JsonProperty("ItemName")
    private String itemName;
    @JsonProperty("ItemName1")
    private String itemName1;
    @JsonProperty("ItemNameLang")
    private String itemNameLang;
    @JsonProperty("UomName")
    private String uomName;
    @JsonProperty("UomId")
    private int uomId;
    @JsonProperty("ItemUrlImage")
    private String itemUrlImage;
    @JsonProperty("ItemDisplayOrder")
    private String itemDisplayOrder;
    @JsonProperty("ItemSetMenu")
    private Boolean itemSetMenu;
    @JsonProperty("ItemPrice")
    private double itemPrice;
    @JsonProperty("ItemDiscountPer")
    private int itemDiscountPer;
    @JsonProperty("ItemDescription")
    private String itemDescription;
    @JsonProperty("CurName")
    private String curName;
    @JsonProperty("ItemGroupId")
    private int itemGroupId;
    @JsonProperty("ItemGroupName")
    private String itemGroupName;
    @JsonProperty("GroupCount")
    private int groupCount;
    @JsonProperty("OrderDetailId")
    private String orderDetailId;
    @JsonProperty("OrderedQuantity")
    private double orderedQuantity;
    @JsonProperty("MoreRequestDetail")
    private String moreRequestDetail;
    @JsonProperty("BackgroundColor")
    private String backgroundColor;
    @JsonProperty("ForeColor")
    private String foreColor;

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

    public String getItemNameLang() {
        return itemNameLang;
    }

    public void setItemNameLang(String itemNameLang) {
        this.itemNameLang = itemNameLang;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public int getUomId() {
        return uomId;
    }

    public void setUomId(int uomId) {
        this.uomId = uomId;
    }

    public String getItemUrlImage() {
        return itemUrlImage;
    }

    public void setItemUrlImage(String itemUrlImage) {
        this.itemUrlImage = itemUrlImage;
    }

    public String getItemDisplayOrder() {
        return itemDisplayOrder;
    }

    public void setItemDisplayOrder(String itemDisplayOrder) {
        this.itemDisplayOrder = itemDisplayOrder;
    }

    public Boolean getItemSetMenu() {
        return itemSetMenu;
    }

    public void setItemSetMenu(Boolean itemSetMenu) {
        this.itemSetMenu = itemSetMenu;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemDiscountPer() {
        return itemDiscountPer;
    }

    public void setItemDiscountPer(int itemDiscountPer) {
        this.itemDiscountPer = itemDiscountPer;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public int getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(int itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public String getItemGroupName() {
        return itemGroupName;
    }

    public void setItemGroupName(String itemGroupName) {
        this.itemGroupName = itemGroupName;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public double getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(double orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public String getMoreRequestDetail() {
        return moreRequestDetail;
    }

    public void setMoreRequestDetail(String moreRequestDetail) {
        this.moreRequestDetail = moreRequestDetail;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getForeColor() {
        return foreColor;
    }

    public void setForeColor(String foreColor) {
        this.foreColor = foreColor;
    }

    @Override
    public String toString() {
        return "ItemCategoryDetail{" +
                "itemId=" + itemId +
                ", itemNo='" + itemNo + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemName1='" + itemName1 + '\'' +
                ", itemNameLang='" + itemNameLang + '\'' +
                ", uomName='" + uomName + '\'' +
                ", uomId=" + uomId +
                ", itemUrlImage='" + itemUrlImage + '\'' +
                ", itemDisplayOrder='" + itemDisplayOrder + '\'' +
                ", itemSetMenu=" + itemSetMenu +
                ", itemPrice=" + itemPrice +
                ", itemDiscountPer=" + itemDiscountPer +
                ", itemDescription='" + itemDescription + '\'' +
                ", curName='" + curName + '\'' +
                ", itemGroupId=" + itemGroupId +
                ", itemGroupName='" + itemGroupName + '\'' +
                ", groupCount=" + groupCount +
                ", orderDetailId='" + orderDetailId + '\'' +
                ", orderedQuantity=" + orderedQuantity +
                ", moreRequestDetail='" + moreRequestDetail + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", foreColor='" + foreColor + '\'' +
                '}';
    }
}
