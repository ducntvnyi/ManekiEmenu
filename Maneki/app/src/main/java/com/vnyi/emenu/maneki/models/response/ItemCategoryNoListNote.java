package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class ItemCategoryNoListNote {
    @JsonProperty("GroupID")
    private int groupID;
    @JsonProperty("GroupParentID")
    private int groupParentID;
    @JsonProperty("GroupName")
    private String groupName;
    @JsonProperty("GroupName1")
    private String groupName1;
    @JsonProperty("GroupCode")
    private String groupCode;
    @JsonProperty("DisplayOrder")
    private int displayOrder;
    @JsonProperty("GroupLevel")
    private int groupLevel;
    @JsonProperty("GroupPath")
    private String groupPath;
    @JsonProperty("CountItem")
    private int countItem;
    @JsonProperty("GroupImgUrl")
    private String groupImgUrl;
    @JsonProperty("StyleID")
    private String styleID;
    @JsonProperty("BackgroundColor")
    private String backgroundColor;
    @JsonProperty("ForeColor")
    private String foreColor;
    @JsonProperty("FontSize")
    private int fontSize;
    @JsonProperty("FontFamily")
    private String fontFamily;
    @JsonProperty("IsBold")
    private String isBold;
    @JsonProperty("IsItalic")
    private String isItalic;
    @JsonProperty("IsUnderlined")
    private String isUnderlined;
    @JsonProperty("IsStrikeThrough")
    private String isStrikeThrough;
    @JsonProperty("IsShadow")
    private String isShadow;
    @JsonProperty("IsOutLine")
    private String isOutLine;
    @JsonProperty("BorderThick")
    private String borderThick;
}
