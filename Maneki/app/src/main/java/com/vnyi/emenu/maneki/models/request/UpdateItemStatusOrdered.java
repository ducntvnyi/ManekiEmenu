package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class UpdateItemStatusOrdered {

    private String ticketId;
    private String rtkiAutoId;
    private String userId;
    private String postId;
    private String langId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getRtkiAutoId() {
        return rtkiAutoId;
    }

    public void setRtkiAutoId(String rtkiAutoId) {
        this.rtkiAutoId = rtkiAutoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "UpdateItemStatusOrdered{" +
                "ticketId='" + ticketId + '\'' +
                ", rtkiAutoId='" + rtkiAutoId + '\'' +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
