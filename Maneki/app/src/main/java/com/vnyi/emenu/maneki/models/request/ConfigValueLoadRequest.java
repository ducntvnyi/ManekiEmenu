package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class ConfigValueLoadRequest {

    private String configType;
    private String machineId;
    private String machineName;
    private String langId;
    private String userId;
    private String customerCode;

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Override
    public String toString() {
        return "ConfigValueLoadRequest{" +
                "configType='" + configType + '\'' +
                ", machineId='" + machineId + '\'' +
                ", machineName='" + machineName + '\'' +
                ", langId='" + langId + '\'' +
                ", userId='" + userId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                '}';
    }
}
