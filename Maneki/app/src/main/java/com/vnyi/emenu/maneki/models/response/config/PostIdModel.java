package com.vnyi.emenu.maneki.models.response.config;

/**
 * Created by hungnd on 11/7/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "PosId"
})
public class PostIdModel {

    @JsonProperty("PosId")
    private Integer posId;

    /**
     *
     * @return
     * The posId
     */
    @JsonProperty("PosId")
    public Integer getPosId() {
        return posId;
    }

    /**
     *
     * @param posId
     * The PosId
     */
    @JsonProperty("PosId")
    public void setPosId(Integer posId) {
        this.posId = posId;
    }

}
