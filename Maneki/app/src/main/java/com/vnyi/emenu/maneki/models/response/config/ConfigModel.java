package com.vnyi.emenu.maneki.models.response.config;

/**
 * Created by hungnd on 11/7/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ConfigCode",
        "ConfigDefaultValue",
        "ConfigName",
        "ConfigValue",
        "ControlType"
})
public class ConfigModel implements Serializable {


    @JsonProperty("ConfigCode")
    private String configCode;
    @JsonProperty("ConfigDefaultValue")
    private String configDefaultValue;
    @JsonProperty("ConfigName")
    private String configName;
    @JsonProperty("ConfigValue")
    private String configValue;
    @JsonProperty("ControlType")
    private int controlType;

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigDefaultValue() {
        return configDefaultValue;
    }

    public void setConfigDefaultValue(String configDefaultValue) {
        this.configDefaultValue = configDefaultValue;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    @Override
    public String toString() {
        return "ConfigModel{" +
                "configCode='" + configCode + '\'' +
                ", configDefaultValue='" + configDefaultValue + '\'' +
                ", configName='" + configName + '\'' +
                ", configValue='" + configValue + '\'' +
                ", controlType=" + controlType +
                '}';
    }
}

