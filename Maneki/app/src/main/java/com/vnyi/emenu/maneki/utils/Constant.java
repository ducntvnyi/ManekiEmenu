package com.vnyi.emenu.maneki.utils;

/**
 * Created by Hungnd on 11/1/17.
 */

public interface Constant {


    // index tab screen
    String KEY_INDEX = "index tab";
    int INDEX_MENU = 100;
    int INDEX_ORDER = INDEX_MENU >> 1;
    int INDEX_SALE_OFF = INDEX_MENU >> 2;
    int INDEX_CALL_WAITER = INDEX_MENU >> 3;
    int INDEX_CALL_PAYMENT = INDEX_MENU >> 4;
    int INDEX_USE_APP = INDEX_MENU >> 5;
    int INDEX_LANGUAGE = INDEX_MENU >> 6;
    int INDEX_CONFIG = INDEX_MENU >> 7;

    // Config type :: 	ConfigType (Short):1-POS,2-Emenu (Vd:2)
    int CONFIG_TYPE = 2;

    //SharedPreferences Key
    String LANG_KEY = "LANG_KEY";
    String LOCALE_EN = "en";
    String LOCALE_VI = "vi";
    String LOCALE_JP = "jp";


    // Key config url
    String KEY_CONFIG_URL = "key_config_url";

    // SYS_MESSAGE
    String MES_AUTO_ID = "MES_AUTOID";
    String MES_KEY_SEARCH = "MES_KEYSEARCH";
    String MESSAGER = "Messager";
    String TITLE = "Title";

    // Table key
    String TABLE_ID = "TableId";

    // key user
    String USER = "user";
    String MEMBER_CHAT = "member_chat";
    String IS_MEMBER_CHAT = "is_member_chat";
    String KEY_MEMBER_CHAT = "key_member_chat";
    // key ACTION
    String NEW_REQUEST = "new_request";
    String IS_APP_RUNNING = "is_app_running";

    // keys config
    String KEY_I_ORG_AUTOID = "I_ORG_AUTOID";
    String KEY_I_LEVELGROUP = "I_LEVELGROUP";
    String KEY_S_LINKDIRECTION = "S_LINKDIRECTION";
    String KEY_S_LINKPROMOTION = "S_LINKPROMOTION";
    String KEY_T_TABLENAME = "T_TABLENAME";
    String KEY_T_USERORDER = "T_USERORDER";
    String KEY_B_USECHOOSETABLE = "B_USECHOOSETABLE";
    String KEY_I_COLUMNTABLE = "I_COLUMNTABLE";
    String KEY_CONFIG_VALUE = "CONFIG_VALUE";
    String KEY_LINK_SERVER = "LINK_SERVER";
    String KEY_LIST_BRANCH = "list branch";
    String KEY_LIST_USER_ORDER = "list user order";
    String KEY_LIST_TABLE = "list table";
    String KEY_TICKET = "ticket";
    String KEY_TICKET_UPDATE_INFO = "ticket update info";
}
