package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class RequestGetList {
    @JsonProperty("RequestNote")
    private String requestNote;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getRequestNote() {
        return requestNote;
    }

    public void setRequestNote(String requestNote) {
        this.requestNote = requestNote;
    }

    @Override
    public String toString() {
        return "RequestGetList{" +
                "requestNote='" + requestNote + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
