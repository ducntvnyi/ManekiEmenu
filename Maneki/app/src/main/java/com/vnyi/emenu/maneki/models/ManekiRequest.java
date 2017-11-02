package com.vnyi.emenu.maneki.models;

import com.qslib.soap.SoapRequest;
import com.vnyi.emenu.maneki.models.request.ConfigValueLoadRequest;
import com.vnyi.emenu.maneki.models.request.UpdateInfo;
import com.vnyi.emenu.maneki.services.ManekiApiServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hungnd on 11/2/17.
 */

public class ManekiRequest {


    public static List<SoapRequest> getListConfigValueLoadRequest(ConfigValueLoadRequest configValueLoadRequest) {
        List<SoapRequest> requestList = new ArrayList<>();

        requestList.add(new SoapRequest(ManekiApiServices.CONFIG_TYPE, configValueLoadRequest.getConfigType()));
        requestList.add(new SoapRequest(ManekiApiServices.MACHINE_ID, configValueLoadRequest.getMachineId()));
        requestList.add(new SoapRequest(ManekiApiServices.MACHINE_NAME, configValueLoadRequest.getMachineName()));
        requestList.add(new SoapRequest(ManekiApiServices.LANG_ID, 1));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_CODE, configValueLoadRequest.getCustomerCode()));
        return requestList;
    }

    public static List<SoapRequest> ticketUpdateInfoRequest(UpdateInfo updateInfo) {
        List<SoapRequest> requestList = new ArrayList<>();

        requestList.add(new SoapRequest(ManekiApiServices.TICKET_ID, updateInfo.getTicketId()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_QUANTITY, updateInfo.getCustomerQty()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_MEN_QUANTITY, updateInfo.getCustomerQtyMen()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_CHILDREN_QUANTITY, updateInfo.getCustomerQtyChildren()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_FOREIGN_QUANTITY, updateInfo.getCustomerQtyForgeign()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_VIP_CODE, updateInfo.getCustomerVipCode()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_GROUP_ID, updateInfo.getCustomerGroupID()));
        requestList.add(new SoapRequest(ManekiApiServices.TABLE_ID, updateInfo.getTableId()));
        requestList.add(new SoapRequest(ManekiApiServices.POST_ID, updateInfo.getPosId()));
        requestList.add(new SoapRequest(ManekiApiServices.BRANCH_ID, updateInfo.getBranchId()));
        requestList.add(new SoapRequest(ManekiApiServices.LANG_ID, updateInfo.getLangId()));
        requestList.add(new SoapRequest(ManekiApiServices.DEVICE_NAME, updateInfo.getDeviceName()));
        requestList.add(new SoapRequest(ManekiApiServices.CUSTOMER_TAX, updateInfo.getCustomerTax()));
        requestList.add(new SoapRequest(ManekiApiServices.COMPANY_NAME, updateInfo.getCompanyName()));
        requestList.add(new SoapRequest(ManekiApiServices.COMPANY_ADDRESS, updateInfo.getCompanyAddress()));
        requestList.add(new SoapRequest(ManekiApiServices.TICKET_NOTE, updateInfo.getTicketNote()));
        return requestList;
    }
}
