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
}
