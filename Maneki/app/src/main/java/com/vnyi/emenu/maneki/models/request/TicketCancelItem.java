package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketCancelItem {

    private String orderDetailId;
    private String userId;
    private String langId;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "TicketCancelItem{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", userId='" + userId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
