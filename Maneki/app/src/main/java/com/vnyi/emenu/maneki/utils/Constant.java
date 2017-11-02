package com.vnyi.emenu.maneki.utils;

/**
 * Created by Hungnd on 11/1/17.
 */

public interface Constant {



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
}
