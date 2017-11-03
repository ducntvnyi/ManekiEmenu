package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class UserOrder {

    @JsonProperty("ObjAutoId")
    private int objAutoId;
    @JsonProperty("ObjName")
    private String objName;

    public int getObjAutoId() {
        return objAutoId;
    }

    public void setObjAutoId(int objAutoId) {
        this.objAutoId = objAutoId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "objAutoId=" + objAutoId +
                ", objName='" + objName + '\'' +
                '}';
    }
}
