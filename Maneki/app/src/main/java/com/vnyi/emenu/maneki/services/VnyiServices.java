package com.vnyi.emenu.maneki.services;


import com.qslib.soap.SoapListener;
import com.qslib.soap.SoapService;

/**
 * Created by Hungnd on 11/1/17.
 */

public class VnyiServices {

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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_SYS_LOG_ERROR_CLIENT).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.MACHINE_SERIAL, machineSerial)
                    .addPropertySoapObject(VnyiApiServices.MACHINE_NAME_, machineName)
                    .addPropertySoapObject(VnyiApiServices.OBJ_ID, objId)
                    .addPropertySoapObject(VnyiApiServices.LOGIN_NAME, loginName)
                    .addPropertySoapObject(VnyiApiServices.CONSTRUCTOR_ERROR, constructorError)
                    .addPropertySoapObject(VnyiApiServices.PROTOTYPE_ERROR, prototypeError)
                    .addPropertySoapObject(VnyiApiServices.DESCRIPTION_ERROR, descriptionError)
                    .addPropertySoapObject(VnyiApiServices.MESSAGE_ERROR, msgError)
                    .addPropertySoapObject(VnyiApiServices.NAME_ERROR, nameError)
                    .addPropertySoapObject(VnyiApiServices.NUMBER_ERROR, numberError)
                    .addPropertySoapObject(VnyiApiServices.STACK_ERROR, stackError)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
     * @param posId
     * @param langId
     * @param soapListener
     */
    public static void requestPostTicketUpdateItemStatus(String url_config, int ticketId, int rtkiAutoId, int userId, int posId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_UPDATE_ITEM_STATUS).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.RTKI_AUTO_ID, rtkiAutoId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.CHECK_SECURITY_CHECK_ROLE).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.USER_NAME, userName)
                    .addPropertySoapObject(VnyiApiServices.PASSWORD, password)
                    .addPropertySoapObject(VnyiApiServices.BRANCH, branchId)
                    .addPropertySoapObject(VnyiApiServices.FORM_ID, formId)
                    .addPropertySoapObject(VnyiApiServices.ROLE_ID, roleId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_SEND_ITEM_ORDER).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_CANCEL_ALL_ITEM_ORDERING).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_USER_UPDATE_PASSWORD).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.USER_NAME, userName)
                    .addPropertySoapObject(VnyiApiServices.NEW_PASSWORD, newPassword)
                    .addPropertySoapObject(VnyiApiServices.OLD_PASSWORD, oldPassword)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.REQUEST_GET_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TYPE, 2)
                    .addPropertySoapObject(VnyiApiServices.ITEM_ID, itemId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_UPDATE_ITEM).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.ORDER_DETAIL_ID, orderDetailId)
                    .addPropertySoapObject(VnyiApiServices.ITEM_ID, itemId)
                    .addPropertySoapObject(VnyiApiServices.UOM_ID, uomId)
                    .addPropertySoapObject(VnyiApiServices.ITEM_QUANTITY, itemQuantity)
                    .addPropertySoapObject(VnyiApiServices.ITEM_PRICE, itemPrice)
                    .addPropertySoapObject(VnyiApiServices.ITEM_CHOICE_AMOUNT, itemChoiceAmount)
                    .addPropertySoapObject(VnyiApiServices.DISCOUNT_PER, discountPer)
                    .addPropertySoapObject(VnyiApiServices.ITEM_REQUEST_DETAIL, itemRequestDetail)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_ITEM_CATEGORY_GET_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID_MENU, ticketId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_TICKET_CHECK_STATUS_BILL).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(VnyiApiServices.YOUR_VERSION, yourVersion)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_ITEM_CATEGORY_GET_DETAIL).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.POR_MASTER_PAGE, postMasterPage)
                    .addPropertySoapObject(VnyiApiServices.CATEGORY_ID, categoryId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, objId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_AREA_LIST).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, branchId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, LangId)
                    .addPropertySoapObject(VnyiApiServices.COUNTER_ID, counterId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get CONFIG_VALUE_LOAD
     *
     * @param configType
     * @param machineId
     * @param machineName
     * @param soapListener
     */

    public static void requestGetConfigValue(String url_config, int configType, String machineId, String machineName, String userId, String customerCode, SoapListener soapListener) {
        try {
            // test data
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.CONFIG_VALUE_LOAD).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.CONFIG_TYPE, configType)
                    .addPropertySoapObject(VnyiApiServices.MACHINE_ID, machineId)
                    .addPropertySoapObject(VnyiApiServices.MACHINE_NAME, machineName)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, 1)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.CUSTOMER_CODE, customerCode)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * request login
     *
     * @param url_config
     * @param userName
     * @param password
     * @param branchId
     * @param formId       : default = "Tbl_Srv_Module"
     * @param roleId
     * @param langId
     * @param posId
     * @param ipAddress
     * @param isLogin
     * @param soapListener
     */
    public static void requestLogin(String url_config, String userName, String password, int branchId, String formId, String roleId, int langId,
                                    int posId, String ipAddress, boolean isLogin, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.LOGIN_SYSTEM).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.USER_NAME, userName)
                    .addPropertySoapObject(VnyiApiServices.PASSWORD, password)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, branchId)
                    .addPropertySoapObject(VnyiApiServices.FORM_ID, formId)
                    .addPropertySoapObject(VnyiApiServices.ROLE_ID, roleId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.IP_ADDRESS, ipAddress)
                    .addPropertySoapObject(VnyiApiServices.IS_LOGIN, isLogin)
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
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.CONFIG_VALUE_GET_BRANCH).setSoapListener(soapListener)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update config value server
     *
     * @param url_config
     * @param posId
     * @param keyCode
     * @param keyValue
     * @param langId
     * @param soapListener
     */
    public static void requestConfigValueUpdateInfo(String url_config, int posId, String keyCode, String keyValue, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.CONFIG_VALUE_UPDATE_INFO).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.KEY_CODE, keyCode)
                    .addPropertySoapObject(VnyiApiServices.KEY_VALUE, keyValue)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load info ticket
     *
     * @param url_config
     * @param ticketId
     * @param tableId
     * @param userId
     * @param langId
     * @param soapListener
     */

    public static void requestTicketLoadInfo(String url_config, int ticketId, int tableId, String userId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_LOAD_INFO).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.TABLE_ID, tableId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list item category no ticket
     *
     * @param url_config
     * @param ticketId
     * @param tableId
     * @param posId
     * @param langId
     * @param soapListener
     */

    public static void requestGetListItemCategoryNoTicket(String url_config, int ticketId, int tableId, String posId, int langId, int branchId, SoapListener soapListener) {

        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_LIST_TEM_CATEGORY_NO_TICKET).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.TABLE_ID, tableId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, branchId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get item order ticket
     *
     * @param url_config
     * @param ticketId
     * @param getType
     * @param userId
     * @param langId
     * @param branchId
     * @param soapListener
     */
    public static void requestGetTicketItemOrder(String url_config, int ticketId, int getType, String userId, int langId, int branchId, SoapListener soapListener) {

        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_TICKET_ITEM_ORDER).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.GET_TYPE, getType)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, branchId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to waiter
     *
     * @param url_config
     * @param ticketId
     * @param requestDetail
     * @param langId
     * @param soapListener
     */
    public static void requestTicketSendItemWaiter(String url_config, int ticketId, String requestDetail, int langId, SoapListener soapListener) {

        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_SEND_REQUEST_WAITER).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.REQUEST_DETAIL, requestDetail)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * processing payment invoice
     *
     * @param url_config
     * @param ticketId
     * @param isInvoice
     * @param langId
     * @param soapListener
     */
    public static void requestTicketProcessingPaymentInvoice(String url_config, int ticketId, boolean isInvoice, int langId, SoapListener soapListener) {

        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_PROCESSING_PAYMENT_INVOICE).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.IS_INVOICE, isInvoice)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * processing payment
     *
     * @param url_config
     * @param ticketId
     * @param langId
     * @param soapListener
     */
    public static void requestTicketProcessingPayment(String url_config, int ticketId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_PROCESSING_PAYMENT).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  cancel item ticket
     *
     * @param url_config
     * @param orderDetailId
     * @param userId
     * @param langId
     * @param soapListener
     */
    public static void requestTicketCancelItem(String url_config, int orderDetailId, boolean userId, int langId, SoapListener soapListener) {

        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_TICKET_CANCEL_ITEM).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.ORDER_DETAIL_ID, orderDetailId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load info ticket payment
     *
     * @param url_config
     * @param ticketId
     * @param langId
     * @param soapListener
     */
    public static void requestLoadInfoTicketPayment(String url_config, int ticketId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_LOAD_INFO_TICKET_PAYMENT).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update item status ordered
     *
     * @param url_config
     * @param ticketId
     * @param rtkiAutoId
     * @param userId
     * @param posId
     * @param langId
     * @param soapListener
     */
    public static void requestUpdateItemStatusOrdered(String url_config, int ticketId, int rtkiAutoId, int userId, int posId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_UPDATE_ITEM_STATUS_ORDERED).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TICKET_ID, ticketId)
                    .addPropertySoapObject(VnyiApiServices.RTKI_AUTO_ID, rtkiAutoId)
                    .addPropertySoapObject(VnyiApiServices.USER_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.POST_ID, posId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  config value table name by id
     *
     * @param url_config
     * @param tableId
     * @param soapListener
     */
    public static void requestConfigValueTableNameById(String url_config, int tableId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_CONFIG_VALUE_TABLE_NAME_BY_ID).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.TABLE_ID, tableId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * config value user order
     *
     * @param url_config
     * @param branchId
     * @param langId
     * @param soapListener
     */
    public static void requestConfigValueUserOrder(String url_config, int branchId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.GET_CONFIG_VALUE_USER_ORDER).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, branchId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get list table
     *
     * @param url_config
     * @param reaAutoId
     * @param listType
     * @param userId
     * @param langId
     * @param soapListener
     */
    public static void requestGetListTable(String url_config, int reaAutoId, int listType, int userId, int langId, SoapListener soapListener) {
        try {
            String url = url_config + URL;
            SoapService.getInstance(NAME_SPACE, url, VnyiApiServices.POST_UPDATE_ITEM_STATUS_ORDERED).setSoapListener(soapListener)
                    .addPropertySoapObject(VnyiApiServices.REA_AUTO_ID, reaAutoId)
                    .addPropertySoapObject(VnyiApiServices.LIST_TYPE, listType)
                    .addPropertySoapObject(VnyiApiServices.BRANCH_ID, userId)
                    .addPropertySoapObject(VnyiApiServices.LANG_ID, langId)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
