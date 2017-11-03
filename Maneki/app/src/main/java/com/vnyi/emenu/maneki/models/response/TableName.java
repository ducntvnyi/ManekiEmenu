package com.vnyi.emenu.maneki.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hungnd on 11/3/17.
 */

public class TableName {
    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("TableName")
    private String tableName;

}
