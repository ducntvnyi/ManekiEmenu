package com.vnyi.emenu.maneki.models.response.config;

/**
 * Created by hungnd on 11/7/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vnyi.emenu.maneki.utils.Constant;

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

    private int typeValue;


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

    public int getTypeValue() {
        String configCode = getConfigCode().toUpperCase();

        if (configCode.equals(Constant.KEY_I_ORG_AUTOID)) {
            typeValue = 0;
        } else if (configCode.equals(Constant.KEY_I_LEVELGROUP)) {
            typeValue = 1;
        } else if (configCode.equals(Constant.KEY_S_LINKDIRECTION)) {
            typeValue = 2;
        } else if (configCode.equals(Constant.KEY_S_LINKPROMOTION)) {
            typeValue = 3;
        } else if (configCode.equals(Constant.KEY_T_TABLENAME)) {
            typeValue = 4;
        } else if (configCode.equals(Constant.KEY_T_USERORDER)) {
            typeValue = 5;
        } else if (configCode.equals(Constant.KEY_B_USECHOOSETABLE)) {
            typeValue = 6;
        } else if (configCode.equals(Constant.KEY_I_COLUMNTABLE)) {
            typeValue = 7;
        }
        return typeValue;
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

