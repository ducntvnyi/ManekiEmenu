package com.vnyi.emenu.maneki.models.request;

/**
 * Created by Hungnd on 11/2/17.
 */

public class UpdateInfo {


    private String ticketId;
    private String customerQty;
    private String CustomerQtyMen;
    private String CustomerQtyChildren;
    private String CustomerQtyForgeign;
    private String CustomerVipCode;
    private String CustomerGroupID;
    private String TableId;
    private String PosId;
    private String LangId;
    private String BranchId;
    private String DeviceName;
    private String CustomerTax;
    private String CompanyName;
    private String CompanyAddress;
    private String TicketNote;

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
        return CustomerQtyMen;
    }

    public void setCustomerQtyMen(String customerQtyMen) {
        CustomerQtyMen = customerQtyMen;
    }

    public String getCustomerQtyChildren() {
        return CustomerQtyChildren;
    }

    public void setCustomerQtyChildren(String customerQtyChildren) {
        CustomerQtyChildren = customerQtyChildren;
    }

    public String getCustomerQtyForgeign() {
        return CustomerQtyForgeign;
    }

    public void setCustomerQtyForgeign(String customerQtyForgeign) {
        CustomerQtyForgeign = customerQtyForgeign;
    }

    public String getCustomerVipCode() {
        return CustomerVipCode;
    }

    public void setCustomerVipCode(String customerVipCode) {
        CustomerVipCode = customerVipCode;
    }

    public String getCustomerGroupID() {
        return CustomerGroupID;
    }

    public void setCustomerGroupID(String customerGroupID) {
        CustomerGroupID = customerGroupID;
    }

    public String getTableId() {
        return TableId;
    }

    public void setTableId(String tableId) {
        TableId = tableId;
    }

    public String getPosId() {
        return PosId;
    }

    public void setPosId(String posId) {
        PosId = posId;
    }

    public String getLangId() {
        return LangId;
    }

    public void setLangId(String langId) {
        LangId = langId;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getCustomerTax() {
        return CustomerTax;
    }

    public void setCustomerTax(String customerTax) {
        CustomerTax = customerTax;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getTicketNote() {
        return TicketNote;
    }

    public void setTicketNote(String ticketNote) {
        TicketNote = ticketNote;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "ticketId='" + ticketId + '\'' +
                ", customerQty='" + customerQty + '\'' +
                ", CustomerQtyMen='" + CustomerQtyMen + '\'' +
                ", CustomerQtyChildren='" + CustomerQtyChildren + '\'' +
                ", CustomerQtyForgeign='" + CustomerQtyForgeign + '\'' +
                ", CustomerVipCode='" + CustomerVipCode + '\'' +
                ", CustomerGroupID='" + CustomerGroupID + '\'' +
                ", TableId='" + TableId + '\'' +
                ", PosId='" + PosId + '\'' +
                ", LangId='" + LangId + '\'' +
                ", BranchId='" + BranchId + '\'' +
                ", DeviceName='" + DeviceName + '\'' +
                ", CustomerTax='" + CustomerTax + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", CompanyAddress='" + CompanyAddress + '\'' +
                ", TicketNote='" + TicketNote + '\'' +
                '}';
    }
}
