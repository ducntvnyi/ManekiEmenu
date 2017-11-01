package com.vnyi.emenu.maneki.services;

import com.qslib.soap.SoapListener;
import com.qslib.soap.SoapService;

/**
 * Created by Hungnd on 11/1/17.
 */

public class ManekiServices {

    /**
     *
     * @param url_config
     * @param configType
     * @param machineId
     * @param machineName
     * @param customerId
     * @param soapListener
     */
    public static void requestGetConfigValue(String url_config, int configType, String machineId, String machineName, String customerId, SoapListener soapListener) {
        try {
            // test data
            int configTypeEmenu = 4;
            configType = configTypeEmenu;
            String url = url_config + ManekiApiServices.BASE_URL;
            SoapService.getInstance(ManekiApiServices.NAME_SPACE, url,ManekiApiServices.CONFIG_VALUE_LOAD).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.CONFIG_TYPE, configType)
                    .addPropertySoapObject(ManekiApiServices.MACHINE_ID, machineId)
                    .addPropertySoapObject(ManekiApiServices.MACHINE_NAME, machineName)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, 1)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
