package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class TicketUpdateInfoRequest {

    private String ticketId;
    private String customerQty;
    private String customerQtyMen;
    private String customerQtyChildren;
    private String customerQtyForgeign;
    private String vipCardCode;
    private String tableId;
    private String posId;
    private String branchId;
    private String langId;
    private String deviceName;
    private String customerTax;
    private String companyName;
    private String companyAddress;
    private String ticketNote;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerQty() {
        return customerQty;
    }

    public void setCustomerQty(String customerQty) {
        this.customerQty = customerQty;
    }

    public String getCustomerQtyMen() {
        return customerQtyMen;
    }

    public void setCustomerQtyMen(String customerQtyMen) {
        this.customerQtyMen = customerQtyMen;
    }

    public String getCustomerQtyChildren() {
        return customerQtyChildren;
    }

    public void setCustomerQtyChildren(String customerQtyChildren) {
        this.customerQtyChildren = customerQtyChildren;
    }

    public String getCustomerQtyForgeign() {
        return customerQtyForgeign;
    }

    public void setCustomerQtyForgeign(String customerQtyForgeign) {
        this.customerQtyForgeign = customerQtyForgeign;
    }

    public String getVipCardCode() {
        return vipCardCode;
    }

    public void setVipCardCode(String vipCardCode) {
        this.vipCardCode = vipCardCode;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCustomerTax() {
        return customerTax;
    }

    public void setCustomerTax(String customerTax) {
        this.customerTax = customerTax;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getTicketNote() {
        return ticketNote;
    }

    public void setTicketNote(String ticketNote) {
        this.ticketNote = ticketNote;
    }

    @Override
    public String toString() {
        return "TicketUpdateInfoRequest{" +
                "ticketId='" + ticketId + '\'' +
                ", customerQty='" + customerQty + '\'' +
                ", customerQtyMen='" + customerQtyMen + '\'' +
                ", customerQtyChildren='" + customerQtyChildren + '\'' +
                ", customerQtyForgeign='" + customerQtyForgeign + '\'' +
                ", vipCardCode='" + vipCardCode + '\'' +
                ", tableId='" + tableId + '\'' +
                ", posId='" + posId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", langId='" + langId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", customerTax='" + customerTax + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", ticketNote='" + ticketNote + '\'' +
                '}';
    }
}
