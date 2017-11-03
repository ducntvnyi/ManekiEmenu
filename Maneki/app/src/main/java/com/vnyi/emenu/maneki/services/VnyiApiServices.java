package com.vnyi.emenu.maneki.services;

/**
 * Created by Hungnd on 11/1/17.
 */

public interface VnyiApiServices {

    // base url
    String NAME_SPACE = "http://tempuri.org/";
    String BASE_URL = "/TranferData/Emenu.asmx";
    String URL_CONFIG = "http://shctest.ezitouch.com:6868/";

    String KEY_MESSAGE = "KeyMessage";

    /* KEY CONFIG */
    String KEY_LINK_WEB = "S_LINKWEB";
    String KEY_ORG_AUTOID = "I_ORG_AUTOID"; // chi nhánh
    String KEY_COUNTER = "I_COUNTER"; // quầy
    String KEY_AREA = "I_AREA"; // quầy
    String KEY_USER_ID = "T_USERORDER"; // User ID
    String KEY_LANG_ID = "I_LANGID";
    String USER_ID = "UserId";
    String KEY_CODE = "KeyCode";
    String KEY_VALUE = "KeyValue";

    /*KEY PARAMS TABLE*/
    String TABLE = "Table";
    String TABLE_DETAIL = "Table1";
    String TABLE_SUB_DETAIL = "Table2";
    String TABLE_ID = "TableId";

    /* KEY PARAMS */
    String LINK_WEB = "link_web"; // link
    String KEY_STALL = "stall"; // quầy
    String LANG_ID = "LangId";
    String BRANCH_ID = "BranchId";
    String BRANCH = "Branch";

    String CONFIG_TYPE = "ConfigType";
    String MACHINE_ID = "MachineId";
    String MACHINE_NAME = "MachineName";
    String CUSTOMER_CODE = "CustomerCode";
    String AREA = "area";

    /*KEY PARAMS LOGIN*/
    String USER_NAME = "UserName";
    String PASSWORD = "Password";
    String FORM_ID = "FormId";
    String ROLE_ID = "RoleId";
    String ROLE_ID_VALUE = "btnUse";
    String POST_ID = "PosId";
    String IP_ADDRESS = "IpAddress";
    String IS_LOGIN = "IsLogin";
    String COUNTER_ID = "CounterId";
    String OBJ_ID = "ObjId";
    String AREA_ID = "AreaId";

    /* KEY TABLE TICKET */
    String TICKET_ID = "TicketId";
    String CUSTOMER_QUANTITY = "CustomerQty";
    String CUSTOMER_MEN_QUANTITY = "CustomerQtyMen";
    String CUSTOMER_CHILDREN_QUANTITY = "CustomerChildren";
    String CUSTOMER_FOREIGN_QUANTITY = "CustomerForeign";
    String CUSTOMER_VIP_CODE = "CustomerVipCode";
    String CUSTOMER_GROUP_ID = "CustomerGroupID";
    String CUSTOMER_TAX = "CustomerTax";
    String COMPANY_NAME = "CompanyName";
    String COMPANY_ADDRESS = "CompanyAddress";
    String TICKET_NOTE = "TicketNote";
    String DEVICE_NAME = "DeviceName";

    String TICKET_ID_MENU = "TicketID";

    /* KEY Item category get detail*/
    String POR_MASTER_PAGE = "ForMasterPage";
    String CATEGORY_ID = "CategoryId";
    String YOUR_VERSION = "YourVersion";

    /* KEY GET REQUEST LIST */
    String ITEM_ID = "ItemId";
    String TYPE = "Type";
    String ORDER_DETAIL_ID = "OrderDetailId";
    String UOM_ID = "UomId";
    String ITEM_QUANTITY = "ItemQuantity";
    String ITEM_PRICE = "ItemPrice";
    String ITEM_CHOICE_AMOUNT = "ItemChoiceAmount";
    String DISCOUNT_PER = "DiscountPer";
    String ITEM_REQUEST_DETAIL = "ItemRequestDetail";
    String REQUEST_DETAIL = "RequestDetail";

    /* KEY Change password */
    String NEW_PASSWORD = "NewPassword";
    String OLD_PASSWORD = "OldPassword";

    String TABLE_MODEL = "table_model";
    String RETURN_TYPE = "ReturnType";
    String KEY_SEARCh = "KeySearch";
    String OBJ_RELATE_ID = "ObjRealatedId";
    String RIS_ID = "RisId";
    String ITEM_DISCOUNT_AMOUNT = "ItemDiscountAmount";
    String ITEM_DISCOUNT_PER = "ItemDiscountPer";
    String IS_PROMOTION = "IsPromotion";
    String QUANTITY = "Quantity";
    String ITEM_NAME = "ItemName";
    String ITEM_NO = "ItemNo";
    String RTKI_AUTO_ID = "Rtki_AutoId";

    // SYS Error
    String MACHINE_SERIAL = "MachineSerial";
    String MACHINE_NAME_ = "MachineName";
    String LOGIN_NAME = "loginName";
    String CONSTRUCTOR_ERROR = "constructorError";
    String PROTOTYPE_ERROR = "prototypeError";
    String DESCRIPTION_ERROR = "descriptionError";
    String MESSAGE_ERROR = "messageError";
    String NAME_ERROR = "nameError";
    String NUMBER_ERROR = "numberError";
    String STACK_ERROR = "stackError";

    // key params
    String FOR_MASTER_PAGE = "ForMasterPage";
    String GET_TYPE = "GetType";
    String REA_AUTO_ID = "Rea_AutoId";
    String LIST_TYPE = "ListType";
    String IS_INVOICE = "IsInvoice";

    // functions
    String CONFIG_VALUE_LOAD = "ConfigValue_Load";
    String POST_TICKET_UPDATE_INFO = "Ticket_UpdateInfo";
    String POST_TICKET_LOAD_INFO = "Ticket_LoadInfo";
    String GET_LIST_TEM_CATEGORY_NO_TICKET = "ItemCategory_GetListNoTicket";
    String GET_ITEM_CATEGORY_GET_DETAIL = "ItemCategory_GetDetail";
    String GET_TICKET_ITEM_ORDER = "Ticket_GetItemOrder";
    String POST_TICKET_UPDATE_ITEM = "Ticket_UpdateItem";
    String POST_TICKET_SEND_ITEM_ORDER = "Ticket_SendItemOrder";
    String POST_TICKET_SEND_REQUEST_WAITER = "Ticket_SendRequestToWaiter";
    String REQUEST_GET_LIST = "Request_GetList";
    String POST_TICKET_PROCESSING_PAYMENT_INVOICE = "Ticket_ProcessingPaymentWithInvoice";
    String POST_TICKET_PROCESSING_PAYMENT = "Ticket_ProcessingPayment";
    String POST_TICKET_CANCEL_ITEM = "Ticket_CancelItem";
    String GET_LOAD_INFO_TICKET_PAYMENT = "Ticket_LoadInfoForPayment";
    String POST_UPDATE_ITEM_STATUS_ORDERED = "Ticket_UpdateItemStatusOrdered";
    String POST_TICKET_CANCEL_ALL_ITEM_ORDERING = "Ticket_CancelAllItemOrdering";
    String GET_TICKET_CHECK_STATUS_BILL = "Ticket_CheckStatusBill";
    String GET_CONFIG_VALUE_TABLE_NAME_BY_ID = "ConfigValue_GetTableNameById";
    String CONFIG_VALUE_GET_BRANCH = "ConfigValue_GetBranch";
    String GET_CONFIG_VALUE_USER_ORDER = "ConfigValue_GetUserOrder";
    String GET_LIST_TABLE = "Table_GetList";
    String CONFIG_VALUE_UPDATE_INFO = "ConfigValue_UpdateInfo";

    // emenuOrder
    String LOGIN_SYSTEM = "Srv_LoginSystem";
    String GET_AREA_LIST = "Area_GetList";
    String GET_ITEM_CATEGORY_GET_LIST = "ItemCategory_GetList"; // menu left
    String POST_USER_UPDATE_PASSWORD = "User_UpdatePassword";
    String CHECK_SECURITY_CHECK_ROLE = "Security_CheckRole";
    String POST_TICKET_UPDATE_ITEM_STATUS = "Ticket_UpdateItemStatus";
    String POST_SYS_LOG_ERROR_CLIENT = "Sys_LogErrorClient";

}

























