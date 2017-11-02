package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class RequestGetList {

    private String type;
    private String itemId;
    private String langId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    @Override
    public String toString() {
        return "RequestGetList{" +
                "type='" + type + '\'' +
                ", itemId='" + itemId + '\'' +
                ", langId='" + langId + '\'' +
                '}';
    }
}
