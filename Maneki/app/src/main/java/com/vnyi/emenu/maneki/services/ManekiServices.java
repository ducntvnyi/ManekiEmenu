package com.vnyi.emenu.maneki.services;

import com.qslib.soap.SoapListener;
import com.qslib.soap.SoapService;
import com.vnyi.emenu.maneki.models.ManekiRequest;
import com.vnyi.emenu.maneki.models.request.ConfigValueLoadRequest;
import com.vnyi.emenu.maneki.models.request.LoginRequest;

/**
 * Created by Hungnd on 11/1/17.
 */

public class ManekiServices {

    public static final String NAME_SPACE = "http://tempuri.org/";
    /* URL */
    public static final String URL_CONFIG = "http://prj.vnyi.com:81"; // fix url
    public static final String URL = "/TranferData/MobileOrder.asmx";

    /**
     * Post sys log error client
     *
     * @param url_config
     * @param machineSerial
     * @param machineName
     * @param objId
     * @param loginName
     * @param constructorError
     * @param prototypeError
     * @param descriptionError
     * @param msgError
     * @param nameError
     * @param numberError
     * @param stackError
     * @param langId
     * @param soapListener
     */
    public static void requestPostSysLogErrorClient(String url_config, String machineSerial, String machineName, int objId, String loginName, String constructorError,
                                                    String prototypeError, String descriptionError, String msgError, String nameError, String numberError, String stackError,
                                                    int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_SYS_LOG_ERROR_CLIENT).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.MACHINE_SERIAL, machineSerial)
                    .addPropertySoapObject(ManekiApiServices.MACHINE_NAME_, machineName)
                    .addPropertySoapObject(ManekiApiServices.OBJ_ID, objId)
                    .addPropertySoapObject(ManekiApiServices.LOGIN_NAME, loginName)
                    .addPropertySoapObject(ManekiApiServices.CONSTRUCTOR_ERROR, constructorError)
                    .addPropertySoapObject(ManekiApiServices.PROTOTYPE_ERROR, prototypeError)
                    .addPropertySoapObject(ManekiApiServices.DESCRIPTION_ERROR, descriptionError)
                    .addPropertySoapObject(ManekiApiServices.MESSAGE_ERROR, msgError)
                    .addPropertySoapObject(ManekiApiServices.NAME_ERROR, nameError)
                    .addPropertySoapObject(ManekiApiServices.NUMBER_ERROR, numberError)
                    .addPropertySoapObject(ManekiApiServices.STACK_ERROR, stackError)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post ticket update item status
     *
     * @param url_config
     * @param ticketId
     * @param rtkiAutoId
     * @param userId
     * @param postId
     * @param langId
     * @param soapListener
     */
    public static void requestPostTicketUpdateItemStatus(String url_config, int ticketId, int rtkiAutoId, int userId, int postId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_TICKET_UPDATE_ITEM_STATUS).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.RTKI_AUTO_ID, rtkiAutoId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, userId)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, postId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Check Security
     *
     * @param url_config
     * @param userName
     * @param password
     * @param branchId
     * @param formId
     * @param roleId
     * @param langId
     * @param soapListener
     */
    public static void requestCheckSecurityRole(String url_config, String userName, String password, int branchId, String formId, String roleId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.CHECK_SECURITY_CHECK_ROLE).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.USER_NAME, userName)
                    .addPropertySoapObject(ManekiApiServices.PASSWORD, password)
                    .addPropertySoapObject(ManekiApiServices.BRANCH, branchId)
                    .addPropertySoapObject(ManekiApiServices.FORM_ID, formId)
                    .addPropertySoapObject(ManekiApiServices.ROLE_ID, roleId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param url_config
     * @param ticketId
     * @param userId
     * @param posId
     * @param langId
     * @param soapListener
     */
    public static void requestPostTicketSendItemOrder(String url_config, int ticketId, int userId, int posId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_TICKET_SEND_ITEM_ORDER).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, userId)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, posId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param url_config
     * @param ticketId
     * @param userId
     * @param langId
     * @param soapListener
     */

    public static void requestPostTicketCancelAllItemOrdering(String url_config, int ticketId, int userId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_TICKET_CANCEL_ALL_ITEM_ORDERING).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, userId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post Change password
     *
     * @param url_config
     * @param newPassword
     * @param oldPassword
     * @param langId
     * @param soapListener
     */
    public static void requestPostUserUpdatePassword(String url_config, String userName, String newPassword, String oldPassword, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_USER_UPDATE_PASSWORD).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.USER_NAME, userName)
                    .addPropertySoapObject(ManekiApiServices.NEW_PASSWORD, newPassword)
                    .addPropertySoapObject(ManekiApiServices.OLD_PASSWORD, oldPassword)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Request list
     *
     * @param url_config
     * @param itemId
     * @param langId
     * @param soapListener
     */
    public static void requestRequestGetList(String url_config, int itemId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.REQUEST_GET_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TYPE, 2)
                    .addPropertySoapObject(ManekiApiServices.ITEM_ID, itemId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update ticket item
     *
     * @param url_config
     * @param ticketId
     * @param orderDetailId
     * @param itemId
     * @param uomId
     * @param itemQuantity
     * @param itemPrice
     * @param itemChoiceAmount
     * @param discountPer
     * @param langId
     * @param posId
     * @param userId
     * @param soapListener
     */
    public static void requestPostTicketUpdateItem(String url_config, int ticketId, int orderDetailId, int itemId, int uomId, String itemQuantity, double itemPrice,
                                                   int itemChoiceAmount, int discountPer, String itemRequestDetail, int langId, int posId, int userId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.POST_TICKET_UPDATE_ITEM).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.ORDER_DETAIL_ID, orderDetailId)
                    .addPropertySoapObject(ManekiApiServices.ITEM_ID, itemId)
                    .addPropertySoapObject(ManekiApiServices.UOM_ID, uomId)
                    .addPropertySoapObject(ManekiApiServices.ITEM_QUANTITY, itemQuantity)
                    .addPropertySoapObject(ManekiApiServices.ITEM_PRICE, itemPrice)
                    .addPropertySoapObject(ManekiApiServices.ITEM_CHOICE_AMOUNT, itemChoiceAmount)
                    .addPropertySoapObject(ManekiApiServices.DISCOUNT_PER, discountPer)
                    .addPropertySoapObject(ManekiApiServices.ITEM_REQUEST_DETAIL, itemRequestDetail)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, posId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, userId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list Item category for MENU LEFT
     *
     * @param url_config
     * @param ticketId
     * @param langId
     * @param posId
     * @param soapListener
     */
    public static void requestGetItemCategoryGetList(String url_config, int ticketId, int langId, int posId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.GET_ITEM_CATEGORY_GET_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID_MENU, ticketId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, posId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check status bill
     *
     * @param url_config
     * @param ticketId
     * @param LangId
     * @param yourVersion
     * @param soapListener
     */
    public static void requestGetCheckStatusBill(String url_config, int ticketId, int LangId, int yourVersion, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.GET_TICKET_CHECK_STATUS_BILL).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(ManekiApiServices.YOUR_VERSION, yourVersion)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list item category get detail
     *
     * @param url_config
     * @param postMasterPage
     * @param categoryId
     * @param ticketId
     * @param LangId
     * @param objId
     * @param posId
     * @param soapListener
     */
    public static void requestGetItemCategory(String url_config, boolean postMasterPage, int categoryId, int ticketId, int LangId, int objId, int posId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.GET_ITEM_CATEGORY_GET_DETAIL).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.POR_MASTER_PAGE, postMasterPage)
                    .addPropertySoapObject(ManekiApiServices.CATEGORY_ID, categoryId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(ManekiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, objId)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, posId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get List AREA
     *
     * @param url_config
     * @param branchId
     * @param LangId
     * @param counterId
     * @param userId
     * @param soapListener
     */

    public static void requestGetAreaList(String url_config, int branchId, int LangId, int counterId, int userId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.GET_AREA_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.BRANCH_ID, branchId)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(ManekiApiServices.COUNTER_ID, counterId)
                    .addPropertySoapObject(ManekiApiServices.USER_ID, userId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * CONFIG_VALUE_LOAD
     *
     * @param url_config
     * @param configValueLoadRequest
     * @param soapListener
     */

    public static void requestGetConfigValue(String url_config, ConfigValueLoadRequest configValueLoadRequest, SoapListener soapListener) {
        try {
            // test data
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.CONFIG_VALUE_LOAD).setSoapListener(soapListener)
                    .addListPropertySoapObject(ManekiRequest.getListConfigValueLoadRequest(configValueLoadRequest))

                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * request login
     * formId       : default = "Tbl_Srv_Module"
     * @param url_config
     * @param loginRequest
     * @param soapListener
     */
    public static void requestLogin(String url_config, LoginRequest loginRequest,  SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.LOGIN_SYSTEM).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.USER_NAME, loginRequest.getUserName())
                    .addPropertySoapObject(ManekiApiServices.PASSWORD, loginRequest.getPassword())
                    .addPropertySoapObject(ManekiApiServices.BRANCH_ID, loginRequest.getBranchId())
                    .addPropertySoapObject(ManekiApiServices.FORM_ID, loginRequest.getFormId())
                    .addPropertySoapObject(ManekiApiServices.ROLE_ID, loginRequest.getRoleId())
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, loginRequest.getLangId())
                    .addPropertySoapObject(ManekiApiServices.POST_ID, loginRequest.getPostId())
                    .addPropertySoapObject(ManekiApiServices.IP_ADDRESS, loginRequest.getIpAddress())
                    .addPropertySoapObject(ManekiApiServices.IS_LOGIN, loginRequest.isLogin())
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list branch
     *
     * @param url_config
     * @param soapListener
     */
    public static void requestConfigValueGetBranch(String url_config, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.CONFIG_VALUE_GET_BRANCH).setSoapListener(soapListener)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update config value server
     *
     * @param url_config
     * @param postId
     * @param keyCode
     * @param keyValue
     * @param langId
     * @param soapListener
     */
    public static void requestConfigValueUpdateInfo(String url_config, int postId, String keyCode, String keyValue, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, ManekiApiServices.CONFIG_VALUE_UPDATE_INFO).setSoapListener(soapListener)
                    .addPropertySoapObject(ManekiApiServices.POST_ID, postId)
                    .addPropertySoapObject(ManekiApiServices.KEY_CODE, keyCode)
                    .addPropertySoapObject(ManekiApiServices.KEY_VALUE, keyValue)
                    .addPropertySoapObject(ManekiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
