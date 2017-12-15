package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by HungND on 12/15/17.
 */

public class TableMap {

    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("TableName")
    private String tableName;
    @JsonProperty("RequestDetail")
    private String requestDetail;
    @JsonProperty("RequestTime")
    private String requestTime;
    @JsonProperty("RequestCategory")
    private int requestCategory;
    @JsonProperty("RequestCategoryName")
    private String requestCategoryName;
    @JsonProperty("AreaId")
    private int areaId;
    @JsonProperty("AreaName")
    private String areaName;
    @JsonProperty("RequestAreaCount")
    private int requestAreaCount;
    @JsonProperty("RequestTableCount")
    private int requestTableCount;
    @JsonProperty("IsDelayed")
    private int isDelayed;
    @JsonProperty("Status")
    private int status;
    @JsonProperty("ColorStatus")
    private String colorStatus;
    @JsonProperty("Priority")
    private int priority;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(int requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getRequestCategoryName() {
        return requestCategoryName;
    }

    public void setRequestCategoryName(String requestCategoryName) {
        this.requestCategoryName = requestCategoryName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getRequestAreaCount() {
        return requestAreaCount;
    }

    public void setRequestAreaCount(int requestAreaCount) {
        this.requestAreaCount = requestAreaCount;
    }

    public int getRequestTableCount() {
        return requestTableCount;
    }

    public void setRequestTableCount(int requestTableCount) {
        this.requestTableCount = requestTableCount;
    }

    public int getIsDelayed() {
        return isDelayed;
    }

    public void setIsDelayed(int isDelayed) {
        this.isDelayed = isDelayed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(String colorStatus) {
        this.colorStatus = colorStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TableMap{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", requestDetail='" + requestDetail + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", requestCategory=" + requestCategory +
                ", requestCategoryName='" + requestCategoryName + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", requestAreaCount=" + requestAreaCount +
                ", requestTableCount=" + requestTableCount +
                ", isDelayed=" + isDelayed +
                ", status=" + status +
                ", colorStatus='" + colorStatus + '\'' +
                ", priority=" + priority +
                ", isSelected=" + isSelected +
                '}';
    }
}
