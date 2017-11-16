package com.vnyi.emenu.maneki.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.ProgressDialogUtils;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.activities.MainActivity;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.ItemCategoryDetailModel;
import com.vnyi.emenu.maneki.models.NoTicketModel;
import com.vnyi.emenu.maneki.models.TicketItemOrderModel;
import com.vnyi.emenu.maneki.models.TicketPaymentModel;
import com.vnyi.emenu.maneki.models.UpdateTicketItemModel;
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;
import com.vnyi.emenu.maneki.models.response.TableName;
import com.vnyi.emenu.maneki.models.response.TicketItemOrder1;
import com.vnyi.emenu.maneki.models.response.TicketItemOrderMoney;
import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;
import com.vnyi.emenu.maneki.models.response.TicketPayment;
import com.vnyi.emenu.maneki.models.response.TicketPaymentMoney;
import com.vnyi.emenu.maneki.models.response.TicketUpdateInfo;
import com.vnyi.emenu.maneki.models.response.TicketUpdateItem;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;


/**
 * Created by Hungnd on 11/1/17.
 */

public abstract class BaseFragment extends Fragment {

    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected ProgressDialogUtils progressDialog = null;
    protected MainActivity mActivity;
    protected Context mContext;
    protected String tableName = "";
    @Nullable
    @BindView(R.id.rootView)
    View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            LanguageUtils.configLanguage(getActivity(), VnyiUtils.getLanguageApp(getActivity()));
        } catch (Exception e) {
            VnyiUtils.LogException(getActivity(), "onActivityCreated", TAG, e.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(getFragmentLayoutId(), container, false);
        ButterKnife.bind(this, this.rootView);
        initViews();
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            this.mActivity = (MainActivity) getActivity();

//            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        } catch (Exception e) {
            VnyiUtils.LogException(getActivity(), "onActivityCreated", TAG, e.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    /**
     * show dialog
     */
    protected void showDialog() {
        try {
            // dismiss dialog
            if (getActivity() == null) mActivity = (MainActivity) getActivity();
            dismissDialog();
            progressDialog = new ProgressDialogUtils();
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show(getActivity());
        } catch (Exception e) {
            VnyiUtils.LogException(getActivity(), "showDialog", TAG, e.getMessage());
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            VnyiUtils.LogException(getActivity(), "dismissDialog", TAG, e.getMessage());
        }
    }

    /**
     * show toast mesage
     *
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    public abstract int getFragmentLayoutId();

    public abstract void initViews();

    public abstract void initData();


    /**
     * menu
     * create bill or get bill
     *
     * @param configValueModel
     * @param ticketId
     * @paramconsumer
     */
    public void ticketLoadInfo(ConfigValueModel configValueModel, int ticketId, Consumer<TicketLoadInfo> consumer) {
        VnyiUtils.LogException(TAG, "<<<--------------start ticketLoadInfo------------>>>");
        try {
            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            String url = VnyiServices.URL_CONFIG;

            int userId = 0;
            int tableId = 0;
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);

            if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
                url = configValueModel.getLinkServer();
                tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());
                userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
            }

            VnyiServices.requestTicketLoadInfo(url, ticketId, tableId, userId, langId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> ticketLoadInfo onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> ticketLoadInfo onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TicketLoadInfo> ticketLoadInfoList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketLoadInfo>>() {
                                });
                                if (ticketLoadInfoList != null && ticketLoadInfoList.size() > 0) {
                                    consumer.accept(ticketLoadInfoList.get(0));
                                }
                            } catch (JSONException e) {
                                VnyiUtils.LogException(mContext, "onSuccess", TAG, "==> jsonObject passed error:  " + e.getMessage());
                            }

                        }
                    }
                    //                dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> ticketLoadInfo onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    //                dismissDialog();
                    VnyiUtils.LogException(TAG, "==> ticketLoadInfo onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "ticketLoadInfo", TAG, e.getMessage());
        }
        VnyiUtils.LogException(TAG, "<<<--------------end ticketLoadInfo------------>>>");
    }

    /**
     * Load Menu Left
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    public void getListItemCategoryNoTicket(ConfigValueModel configValueModel, int ticketId, Consumer<NoTicketModel> consumer) {
        VnyiUtils.LogException(TAG, "<<<--------------start getListItemCategoryNoTicket------------>>>");
        try {
            int posId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            String url = VnyiServices.URL_CONFIG;
            int tableId = 0;
            int branchId = 0;

            if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
                url = configValueModel.getLinkServer();
                tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());
                branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            }

            VnyiServices.requestGetListItemCategoryNoTicket(url, ticketId, tableId, posId, langId, branchId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> getListItemCategoryNoTicket onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> getListItemCategoryNoTicket onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> getListItemCategoryNoTicket onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<ItemCategoryNoListNote> categoryNoListNotes = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<ItemCategoryNoListNote>>() {
                                });

                                if (categoryNoListNotes != null && categoryNoListNotes.size() > 0) {
                                    NoTicketModel noTicketModel = new NoTicketModel();
                                    noTicketModel.setItemCategoryNoListNotes(categoryNoListNotes);
                                    consumer.accept(noTicketModel);
                                }
                            } catch (JSONException e) {
                                VnyiUtils.LogException(mContext, "onSuccess", TAG, "==> jsonObject passed error:  " + e.getMessage());
                            }

                        }
                    }
                    //                dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
//                    dismissDialog();
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> getListItemCategoryNoTicket onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    //                dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getListItemCategoryNoTicket onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "getListItemCategoryNoTicket", TAG, e.getMessage());
        }
        VnyiUtils.LogException(TAG, "<<<--------------end getListItemCategoryNoTicket------------>>>");
    }

    /**
     * load Menu right
     *
     * @param configValueModel
     * @param postMasterPage
     * @param categoryId
     * @param ticketId
     * @param consumer
     */
    public void getListItemCategoryDetail(ConfigValueModel configValueModel, boolean postMasterPage, int categoryId,
                                          int ticketId, Consumer<ItemCategoryDetailModel> consumer) {
        VnyiUtils.LogException(TAG, "<<<--------------start getListItemCategoryDetail------------>>>");
        int posId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.POST_ID);
        int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
        int objId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());

        if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

        String url = VnyiServices.URL_CONFIG;


        if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
            url = configValueModel.getLinkServer();
        }

        VnyiServices.requestGetItemCategoryDetail(url, postMasterPage, categoryId, ticketId, langId, objId, posId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onStarted ");
                showDialog();
            }

            @Override
            public void onSuccess(SoapResponse soapResponse) {
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onSuccess ");
                if (soapResponse == null) return;

                if (soapResponse.getStatus().toLowerCase().equals("true")) {
                    if (soapResponse.getResult() != null) {
                        VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onSuccess:: " + soapResponse.toString());
                        try {
                            JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                            List<ItemCategoryDetail> itemCategoryDetails = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<ItemCategoryDetail>>() {
                            });

                            if (itemCategoryDetails != null && itemCategoryDetails.size() > 0) {
                                ItemCategoryDetailModel categoryDetailModel = new ItemCategoryDetailModel();
                                categoryDetailModel.setItemCategoryDetails(itemCategoryDetails);
                                VnyiUtils.LogException(TAG, "==> categoryDetailModel" + categoryDetailModel.toString());
                                consumer.accept(categoryDetailModel);
                            }
                        } catch (JSONException e) {
                            VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                        }

                    }
                }
                dismissDialog();

            }

            @Override
            public void onFail(Exception ex) {
                dismissDialog();
                VnyiUtils.LogException(mContext, "onFail", TAG, "==> getListItemCategoryDetail onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
//                dismissDialog();
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onFinished ");
            }
        });
        VnyiUtils.LogException(TAG, "<<<--------------end getListItemCategoryDetail------------>>>");
    }

    /**
     * update ticket before order item(ticketId=0) create bill
     *
     * @param configValueModel
     * @param consumer
     */
    public void requestTicketUpdateInfo(ConfigValueModel configValueModel, Consumer<TicketUpdateInfo> consumer) {
        VnyiUtils.LogException(TAG, "--------------start requestTicketUpdateInfo------------");
        try {
            int posId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            int tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            String url = VnyiServices.URL_CONFIG;


            if (!configValueModel.getLinkServer().equals("")) {
                url = configValueModel.getLinkServer();
            }

            VnyiServices.requestTicketUpdateInfo(url, 0, tableId, branchId, posId, langId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestTicketUpdateInfo onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestTicketUpdateInfo onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> requestTicketUpdateInfo onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TicketUpdateInfo> updateInfoList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketUpdateInfo>>() {
                                });

                                if (updateInfoList != null && updateInfoList.size() > 0) {

                                    consumer.accept(updateInfoList.get(0));
                                }
                            } catch (JSONException e) {
                                VnyiUtils.LogException(mContext, "jsonObject passed error: ", TAG, "==> " + e.getMessage());
                            }

                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestTicketUpdateInfo onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
//                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestTicketUpdateInfo onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "requestTicketUpdateInfo", TAG, e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestTicketUpdateInfo------------");
    }

    /**
     * order item with quantity(+ or -)
     *
     * @param configValueModel
     * @param ticketId
     * @param quantity
     * @param categoryDetail
     * @param consumer
     */
    public void requestPostTicketUpdateItem(ConfigValueModel configValueModel, int ticketId, int quantity, ItemCategoryDetail categoryDetail, Consumer<TicketUpdateItem> consumer) {

        VnyiUtils.LogException(TAG, "--------------start requestPostTicketUpdateItem------------");
        VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem: orderDetailId:: " + categoryDetail.getOrderDetailId() + " - quantity " + quantity + " - getItemId:" + categoryDetail.getItemId());
        try {
            int posId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
            int itemChoiceAmount = 0;
            String itemRequestDetail = "";
            int orderDetailId = Integer.parseInt(categoryDetail.getOrderDetailId());


            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            String url = VnyiServices.URL_CONFIG;


            if (!configValueModel.getLinkServer().equals("")) {
                url = configValueModel.getLinkServer();
            }

            VnyiServices.requestPostTicketUpdateItem(url, ticketId, orderDetailId, categoryDetail.getItemId(), categoryDetail.getUomId(), quantity,
                    categoryDetail.getItemPrice(), itemChoiceAmount, categoryDetail.getItemDiscountPer(), itemRequestDetail, langId, posId, userId, new SoapListenerVyni() {

                        @Override
                        public void onStarted() {
                            VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onStarted ");
                            showDialog();
                        }

                        @Override
                        public void onSuccess(SoapResponse soapResponse) {
                            VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onSuccess ");
                            if (soapResponse == null) return;

                            if (soapResponse.getStatus().toLowerCase().equals("true")) {
                                if (soapResponse.getResult() != null) {
                                    VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onSuccess:: " + soapResponse.toString());
                                    try {
                                        JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                        List<TicketUpdateItem> updateInfoList = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketUpdateItem>>() {
                                        });

                                        if (updateInfoList != null && updateInfoList.size() > 0) {
                                            consumer.accept(updateInfoList.get(0));
                                        }
                                    } catch (JSONException e) {
                                        VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                                    }

                                }
                            }
                            dismissDialog();

                        }

                        @Override
                        public void onFail(Exception ex) {
                            dismissDialog();
                            VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestPostTicketUpdateItem onFail " + ex.getMessage());
                        }

                        @Override
                        public void onFinished() {
                            dismissDialog();
                            VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onFinished ");
                        }
                    });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "requestPostTicketUpdateItem", TAG, e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestPostTicketUpdateItem------------");
    }

    /**
     * @param configValueModel
     * @param ticketId
     */
    public void checkStatusBill(ConfigValueModel configValueModel, int ticketId, Consumer<Boolean> consumer) {
        VnyiUtils.LogException(TAG, "--------------start checkStatusBill------------");
        try {
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);
            int yourVersion = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.YOUR_VERSION);

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            String url = configValueModel.getLinkServer();

            VnyiServices.requestGetCheckStatusBill(url, ticketId, langId, yourVersion, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> checkStatusBill onFinished ");

                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> checkStatusBill onSuccess ");
                    if (soapResponse == null) return;
                    if (!TextUtils.isEmpty(soapResponse.getId())) {
                        VnyiPreference.getInstance(getActivity()).putInt(VnyiApiServices.YOUR_VERSION, Integer.parseInt(soapResponse.getId()));
                    } else {
                        VnyiPreference.getInstance(getActivity()).putInt(VnyiApiServices.YOUR_VERSION, 0);
                    }

                    consumer.accept(soapResponse.getStatus().toLowerCase().equals("true"));

                }

                @Override
                public void onFail(Exception ex) {
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> checkStatusBill onFail ");
                }

                @Override
                public void onFinished() {
                    VnyiUtils.LogException(TAG, "==> checkStatusBill onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "onFail", TAG, "==> checkStatusBill " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end checkStatusBill------------");
    }

    protected void requestGetTicketItemOrder(ConfigValueModel configValueModel, int ticketId, int getType, Consumer<TicketItemOrderModel> consumer) {

        VnyiUtils.LogException(TAG, "--------------start requestGetTicketItemOrder------------");

        try {
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
            int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestGetTicketItemOrder(url, ticketId, getType, userId, langId, branchId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TicketItemOrder1> ticketItemOrders = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketItemOrder1>>() {
                                });
                                List<TicketItemOrderMoney> itemOrderMoneys = JacksonUtils.convertJsonToObject(configValueObject.getString("Table3"), new TypeReference<List<TicketItemOrderMoney>>() {
                                });
                                TicketItemOrderModel itemOrderModel = new TicketItemOrderModel();
                                if (ticketItemOrders != null && ticketItemOrders.size() > 0) {
                                    itemOrderModel.setTicketItemOrders(ticketItemOrders);
                                }
                                if (itemOrderMoneys != null && itemOrderMoneys.size() > 0) {
                                    itemOrderModel.setTicketItemOrderMoney(itemOrderMoneys.get(0));
                                }
                                consumer.accept(itemOrderModel);
                            } catch (JSONException e) {
                                VnyiUtils.LogException(mContext, "jsonObject passed error: ", TAG, "==> " + e.getMessage());
                            }

                        }
                    }
                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestGetTicketItemOrder " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestGetTicketItemOrder " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestGetTicketItemOrder------------");
    }


    protected void requestLoadInfoTicketPayment(ConfigValueModel configValueModel, int ticketId, Consumer<TicketPaymentModel> consumer) {

        VnyiUtils.LogException(TAG, "--------------start requestLoadInfoTicketPayment------------");

        try {
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestLoadInfoTicketPayment(url, ticketId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestLoadInfoTicketPayment onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestLoadInfoTicketPayment onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> requestLoadInfoTicketPayment onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TicketPaymentMoney> paymentMoneys = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TicketPaymentMoney>>() {
                                });
                                List<TicketPayment> ticketPayments = JacksonUtils.convertJsonToObject(configValueObject.getString("Table1"), new TypeReference<List<TicketPayment>>() {
                                });
                                TicketPaymentModel paymentModel = new TicketPaymentModel();
                                if (paymentMoneys != null && paymentMoneys.size() > 0) {
                                    paymentModel.setTicketPaymentMoney(paymentMoneys.get(0));
                                }
                                if (ticketPayments != null && ticketPayments.size() > 0) {
                                    paymentModel.setTicketPayments(ticketPayments);
                                }
                                consumer.accept(paymentModel);
                            } catch (JSONException e) {
                                VnyiUtils.LogException(mContext, "jsonObject passed error: ", TAG, "==> " + e.getMessage());
                            }

                        }
                    }
                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestLoadInfoTicketPayment " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    VnyiUtils.LogException(TAG, "==> requestLoadInfoTicketPayment onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestLoadInfoTicketPayment " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestLoadInfoTicketPayment------------");
    }


    class UpdateItemTask extends AsyncTask<Void, Void, Void> {

        private Consumer<UpdateTicketItemModel> mUpdateItemTaskConsumer;
        private UpdateTicketItemModel mUpdateTicketItemModel;
        private List<ItemCategoryDetail> mItemCategoryDetails;

        public UpdateItemTask(UpdateTicketItemModel updateTicketItemModel, List<ItemCategoryDetail> itemCategoryDetails, Consumer<UpdateTicketItemModel> updateItemTaskConsumer) {
            this.mUpdateTicketItemModel = updateTicketItemModel;
            this.mUpdateItemTaskConsumer = updateItemTaskConsumer;
            this.mItemCategoryDetails = itemCategoryDetails;
            Log.d(TAG, "==> mItemCategoryDetails" + mItemCategoryDetails.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "==> UpdateItemTask onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e(TAG, "==> UpdateItemTask doInBackground");
            for (ItemCategoryDetail categoryDetail : mUpdateTicketItemModel.getItemCategoryDetails()) {
                requestPostTicketUpdateItem(mUpdateTicketItemModel.getConfigValueModel(),
                        mUpdateTicketItemModel.getTicketId(),
                        (int) (categoryDetail.getOrderedQuantity() + 1),
                        categoryDetail, ticketUpdateInfo -> {
                            Log.i(TAG, "==> requestPostTicketUpdateItem orderId 1: " + categoryDetail.getOrderDetailId());
                            Log.i(TAG, "==>requestPostTicketUpdateItem  orderId 2: " + ticketUpdateInfo.getOrderDetailId());
                            StreamSupport.stream(mItemCategoryDetails).forEach(item -> item.setOrderDetailId(item.getItemId() == ticketUpdateInfo.getItemId() ?
                                    ticketUpdateInfo.getOrderDetailId() + "" : item.getOrderDetailId()));
                        });

            }
            Log.d(TAG, "==> mItemCategoryDetails doInBackground::" + mItemCategoryDetails.toString());
            mUpdateTicketItemModel.setItemCategoryDetails(mItemCategoryDetails);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "==> mItemCategoryDetails onPostExecute::" + mUpdateTicketItemModel.getItemCategoryDetails().toString());
            mUpdateItemTaskConsumer.accept(mUpdateTicketItemModel);
//            Log.e(TAG, "==> UpdateItemTask onPostExecute");
        }
    }

    /**
     * huỷ order
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    protected void requestPostTicketCancelAllItemOrdering(ConfigValueModel configValueModel, int ticketId, Consumer<Boolean> consumer) {

        VnyiUtils.LogException(TAG, "--------------Start requestPostTicketCancelAllItemOrdering------------");
        try {
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestPostTicketCancelAllItemOrdering(url, ticketId, userId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onSuccess ");


                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        consumer.accept(true);
                    } else {
                        consumer.accept(false);
                    }

                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestGetTicketItemOrder " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestGetTicketItemOrder onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestGetTicketItemOrder " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestPostTicketCancelAllItemOrdering------------");
    }

    /**
     * send item order
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    protected void requestPostTicketSendItemOrder(ConfigValueModel configValueModel, int ticketId, Consumer<Boolean> consumer) {

        VnyiUtils.LogException(TAG, "--------------Start requestPostTicketSendItemOrder------------");
        try {
            int posId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestPostTicketSendItemOrder(url, ticketId, userId, posId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestPostTicketSendItemOrder onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestPostTicketSendItemOrder onSuccess ");


                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        consumer.accept(true);
                    } else {
                        consumer.accept(false);
                    }


                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    consumer.accept(false);
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestPostTicketSendItemOrder " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestPostTicketSendItemOrder onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestPostTicketSendItemOrder " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestPostTicketSendItemOrder------------");
    }

    /**
     * payment or
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    protected void requestTicketProcessingPayment(ConfigValueModel configValueModel, int ticketId, Consumer<Boolean> consumer) {

        VnyiUtils.LogException(TAG, "--------------Start requestTicketProcessingPayment------------");
        try {

            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestTicketProcessingPayment(url, ticketId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestTicketProcessingPayment onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestTicketProcessingPayment onSuccess ");

                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        consumer.accept(true);
                    } else {
                        consumer.accept(false);
                    }

                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    consumer.accept(false);
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestTicketProcessingPayment " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestTicketProcessingPayment onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestTicketProcessingPayment " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestTicketProcessingPayment------------");
    }


    /**
     * cancel item order
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    protected void requestTicketCancelItem(ConfigValueModel configValueModel, int ticketId, Consumer<Boolean> consumer) {

        VnyiUtils.LogException(TAG, "--------------Start requestTicketCancelItem------------");
        try {

            int langId = VnyiPreference.getInstance(getActivity()).getInt(VnyiApiServices.LANG_ID);
            int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getActivity())) return;

            VnyiServices.requestTicketCancelItem(url, ticketId, userId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestTicketCancelItem onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestTicketCancelItem onSuccess ");

                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        consumer.accept(true);
                    } else {
                        consumer.accept(false);
                    }

                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    consumer.accept(false);
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestTicketCancelItem " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestTicketCancelItem onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "catch", TAG, "==> requestTicketCancelItem " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestTicketCancelItem------------");
    }




    protected String getTableName(int tableId) {
        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return tableName;
            String url = VnyiServices.URL_CONFIG;

            ConfigValueModel configValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

            if (configValueModel != null && !configValueModel.getLinkServer().equals(""))
                url = configValueModel.getLinkServer();

            VnyiServices.requestConfigValueTableNameById(url, tableId, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> getTableName onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> getTableName onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<TableName> tableNames = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<TableName>>() {
                                });

                                if (tableNames != null || tableNames.size() > 0) {
                                    tableName = tableNames.get(0).getTableName();
                                    VnyiPreference.getInstance(getContext()).putString(Constant.KEY_TABLE_NAME, tableName);

                                }
                            } catch (JSONException e) {
                                VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                            }

                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> getTableName onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " getTableName", TAG, e.getMessage());
        }
        return tableName;
    }


}


