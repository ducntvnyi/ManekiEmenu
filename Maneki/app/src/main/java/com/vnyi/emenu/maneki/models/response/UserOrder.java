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
}
