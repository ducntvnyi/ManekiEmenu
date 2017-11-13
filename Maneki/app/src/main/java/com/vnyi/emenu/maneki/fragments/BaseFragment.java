package com.vnyi.emenu.maneki.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;
import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;
import com.vnyi.emenu.maneki.models.response.TicketUpdateInfo;
import com.vnyi.emenu.maneki.models.response.TicketUpdateItem;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;


/**
 * Created by Hungnd on 11/1/17.
 */

public abstract class BaseFragment extends Fragment {

    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected ProgressDialogUtils progressDialog = null;
    protected MainActivity mActivity;
    protected Context mContext;

    @Nullable
    @BindView(R.id.rootView)
    View rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            LanguageUtils.configLanguage(mActivity, VnyiUtils.getLanguageApp(mActivity));
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "onActivityCreated", TAG, e.getMessage());
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
            VnyiUtils.LogException(getContext(), "onActivityCreated", TAG, e.getMessage());
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
            dismissDialog();
            progressDialog = new ProgressDialogUtils();
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show(mActivity);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "showDialog", TAG, e.getMessage());
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
            VnyiUtils.LogException(getContext(), "dismissDialog", TAG, e.getMessage());
        }
    }

    /**
     * show toast mesage
     *
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(mActivity, msg);
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

        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            String url = VnyiServices.URL_CONFIG;

            int userId = 0;
            int tableId = 0;
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

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
                    VnyiUtils.LogException(mContext, "onFinished", TAG, "==> ticketLoadInfo onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "ticketLoadInfo", TAG, e.getMessage());
        }
    }

    /**
     * Load Menu Left
     *
     * @param configValueModel
     * @param ticketId
     * @param consumer
     */
    public void getListItemCategoryNoTicket(ConfigValueModel configValueModel, int ticketId, Consumer<NoTicketModel> consumer) {

        try {
            int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

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
                    dismissDialog();
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

        int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
        int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);
        int objId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());

        if (!NetworkUtils.isNetworkAvailable(getContext())) return;

        String url = VnyiServices.URL_CONFIG;


        if (configValueModel != null && !configValueModel.getLinkServer().equals("")) {
            url = configValueModel.getLinkServer();
        }

        VnyiServices.requestGetItemCategoryDetail(url, postMasterPage, categoryId, ticketId, langId, objId, posId, new SoapListenerVyni() {

            @Override
            public void onStarted() {
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onStarted ");
//                showDialog();
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
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onFail " + ex.getMessage());
            }

            @Override
            public void onFinished() {
//                dismissDialog();
                VnyiUtils.LogException(TAG, "==> getListItemCategoryDetail onFinished ");
            }
        });
    }

    /**
     * update ticket before order item(ticketId=0) create bill
     *
     * @param configValueModel
     * @param consumer
     */
    public void requestTicketUpdateInfo(ConfigValueModel configValueModel, Consumer<TicketUpdateInfo> consumer) {
        VnyiUtils.LogException(mContext, "requestTicketUpdateInfo ", TAG, "--------------start------------");
        try {
            int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);
            int branchId = Integer.parseInt(configValueModel.getBranch().getConfigValue());
            int tableId = Integer.parseInt(configValueModel.getTableName().getConfigValue());

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

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
                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(mContext, "onFail", TAG, "==> requestTicketUpdateInfo onFail " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestTicketUpdateInfo onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "requestTicketUpdateInfo", TAG, e.getMessage());
        }
        VnyiUtils.LogException(mContext, "requestTicketUpdateInfo ", TAG, "--------------End------------");
    }

    /**
     * order item with quantity(+ or -)
     *
     * @param configValueModel
     * @param ticketId
     * @param orderDetailId
     * @param quantity
     * @param categoryDetail
     * @param consumer
     */
    public void requestPostTicketUpdateItem(ConfigValueModel configValueModel, int ticketId, int orderDetailId, int quantity, ItemCategoryDetail categoryDetail, Consumer<TicketUpdateItem> consumer) {

        int posId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.POST_ID);
        int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);
        int userId = Integer.parseInt(configValueModel.getUserOrder().getConfigValue());
        int itemChoiceAmount = 0;
        String itemRequestDetail = "";


        if (!NetworkUtils.isNetworkAvailable(getContext())) return;

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
                        VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onFail " + ex.getMessage());
                    }

                    @Override
                    public void onFinished() {
                        dismissDialog();
                        VnyiUtils.LogException(TAG, "==> requestPostTicketUpdateItem onFinished ");
                    }
                });
    }


    private void checkStatusBill(ConfigValueModel configValueModel, int ticketId) {
        VnyiUtils.LogException(mContext, "checkStatusBill ", TAG, "--------------Start------------");
        try {
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);
            int yourVersion = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.YOUR_VERSION);

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;
            String url = VnyiServices.URL_CONFIG;


            if (!configValueModel.getLinkServer().equals("")) {
                url = configValueModel.getLinkServer();
            }
            VnyiServices.requestGetCheckStatusBill(url, ticketId, langId, yourVersion, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> checkStatusBill onFinished ");

                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> checkStatusBill onSuccess ");
                    if (!TextUtils.isEmpty(soapResponse.getId())) {
                        VnyiPreference.getInstance(getContext()).putInt(VnyiApiServices.YOUR_VERSION, Integer.parseInt(soapResponse.getId()));
                    } else {
                        VnyiPreference.getInstance(getContext()).putInt(VnyiApiServices.YOUR_VERSION, 0);
                    }
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
        VnyiUtils.LogException(mContext, "checkStatusBill ", TAG, "--------------end------------");
    }


}
