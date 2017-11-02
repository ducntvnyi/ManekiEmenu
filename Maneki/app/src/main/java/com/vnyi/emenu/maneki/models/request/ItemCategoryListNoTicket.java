package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class ItemCategoryListNoTicket {

    private String ticketId;
    private String langId;
    private String postId;
    private String tableId;
    private String branchId;

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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "ItemCategoryListNoTicket{" +
                "ticketId='" + ticketId + '\'' +
                ", langId='" + langId + '\'' +
                ", postId='" + postId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", branchId='" + branchId + '\'' +
                '}';
    }
}
