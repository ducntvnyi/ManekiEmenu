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

    private int position;
    private boolean isSelected;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getGroupParentID() {
        return groupParentID;
    }

    public void setGroupParentID(int groupParentID) {
        this.groupParentID = groupParentID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName1() {
        return groupName1;
    }

    public void setGroupName1(String groupName1) {
        this.groupName1 = groupName1;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public int getCountItem() {
        return countItem;
    }

    public void setCountItem(int countItem) {
        this.countItem = countItem;
    }

    public String getGroupImgUrl() {
        return groupImgUrl;
    }

    public void setGroupImgUrl(String groupImgUrl) {
        this.groupImgUrl = groupImgUrl;
    }

    public String getStyleID() {
        return styleID;
    }

    public void setStyleID(String styleID) {
        this.styleID = styleID;
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getIsBold() {
        return isBold;
    }

    public void setIsBold(String isBold) {
        this.isBold = isBold;
    }

    public String getIsItalic() {
        return isItalic;
    }

    public void setIsItalic(String isItalic) {
        this.isItalic = isItalic;
    }

    public String getIsUnderlined() {
        return isUnderlined;
    }

    public void setIsUnderlined(String isUnderlined) {
        this.isUnderlined = isUnderlined;
    }

    public String getIsStrikeThrough() {
        return isStrikeThrough;
    }

    public void setIsStrikeThrough(String isStrikeThrough) {
        this.isStrikeThrough = isStrikeThrough;
    }

    public String getIsShadow() {
        return isShadow;
    }

    public void setIsShadow(String isShadow) {
        this.isShadow = isShadow;
    }

    public String getIsOutLine() {
        return isOutLine;
    }

    public void setIsOutLine(String isOutLine) {
        this.isOutLine = isOutLine;
    }

    public String getBorderThick() {
        return borderThick;
    }

    public void setBorderThick(String borderThick) {
        this.borderThick = borderThick;
    }

    @Override
    public String toString() {
        return "ItemCategoryNoListNote{" +
                "groupID=" + groupID +
                ", groupParentID=" + groupParentID +
                ", groupName='" + groupName + '\'' +
                ", groupName1='" + groupName1 + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", displayOrder=" + displayOrder +
                ", groupLevel=" + groupLevel +
                ", groupPath='" + groupPath + '\'' +
                ", countItem=" + countItem +
                ", groupImgUrl='" + groupImgUrl + '\'' +
                ", styleID='" + styleID + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", foreColor='" + foreColor + '\'' +
                ", fontSize=" + fontSize +
                ", fontFamily='" + fontFamily + '\'' +
                ", isBold='" + isBold + '\'' +
                ", isItalic='" + isItalic + '\'' +
                ", isUnderlined='" + isUnderlined + '\'' +
                ", isStrikeThrough='" + isStrikeThrough + '\'' +
                ", isShadow='" + isShadow + '\'' +
                ", isOutLine='" + isOutLine + '\'' +
                ", borderThick='" + borderThick + '\'' +
                ", position=" + position +
                ", isSelected=" + isSelected +
                '}';
    }
}
