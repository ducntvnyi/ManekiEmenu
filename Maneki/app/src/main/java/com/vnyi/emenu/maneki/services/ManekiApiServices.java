package com.vnyi.emenu.maneki.services;

/**
 * Created by Hungnd on 11/1/17.
 */

public interface ManekiApiServices {

    // base url
    String NAME_SPACE = "http://tempuri.org/";
    String BASE_URL = "/TranferData/Emenu.asmx";
    String URL_CONFIG = "http://shctest.ezitouch.com:6868/";
    // keys
    String CONFIG_TYPE = "ConfigType";
    String MACHINE_ID = "MachineId";
    String MACHINE_NAME = "MachineName";
    String KEY_MESSAGE = "KeyMessage";
    String LANG_ID = "LangId";
    String BRANCH_ID = "BranchId";
    String BRANCH = "Branch";
    String USER_ID = "UserId";

    // functions
    String CONFIG_VALUE_LOAD = "ConfigValue_Load";
}
