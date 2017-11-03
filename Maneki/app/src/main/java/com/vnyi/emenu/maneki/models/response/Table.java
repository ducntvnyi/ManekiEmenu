package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class Table {

    @JsonProperty("RET_AUTOID")
    private int retAutoId;
    @JsonProperty("RET_DEFINEID")
    private String retDefineId;
}
