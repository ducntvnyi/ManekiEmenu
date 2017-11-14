package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.GetListAdapter;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.RequestGetList;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/6/17.
 */

public class CallWaiterFragment extends BaseFragment {

    private static final String TAG = CallWaiterFragment.class.getSimpleName();
    private List<RequestGetList> mRequestGetLists;
    private GetListAdapter mGetListAdapter;
    private ConfigValueModel mConfigValueModel;


    @BindView(R.id.llHeaderTable)
    LinearLayout llHeaderTable;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;
    @BindView(R.id.rvGetList)
    RecyclerView rvGetList;

    public static Fragment newInstance() {
        Fragment fragment = new CallWaiterFragment();
        return fragment;

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.dialog_call_waiter_fragment;
    }

    @Override
    public void initViews() {
        // onListener
        mRequestGetLists = new ArrayList<>();
        mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

        mGetListAdapter = new GetListAdapter(getContext(), mRequestGetLists, requestGetList -> {

        });
        mGetListAdapter.setGetList(mRequestGetLists);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvGetList.setAdapter(mGetListAdapter);
        rvGetList.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        // new ArrayList<>
        requestRequestGetList(mConfigValueModel, 2, 1);
    }

    protected void requestRequestGetList(ConfigValueModel configValueModel, int typeId, int itemId) {

        VnyiUtils.LogException(TAG, "--------------Start requestRequestGetList------------");
        try {
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestRequestGetList(url, typeId, itemId, langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestRequestGetList onFinished ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    VnyiUtils.LogException(TAG, "==> requestRequestGetList onSuccess ");

                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> requestRequestGetList onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                List<RequestGetList> requestGetLists = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<RequestGetList>>() {
                                });
                                List<RequestGetList> getLists = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE_DETAIL), new TypeReference<List<RequestGetList>>() {
                                });
                                if (requestGetLists != null) {
                                    mRequestGetLists.addAll(requestGetLists);
                                }
                                if (getLists != null) {
                                    mRequestGetLists.addAll(getLists);
                                }

                                VnyiUtils.LogException(TAG, "==> requestRequestGetList:" + mRequestGetLists.size());
                                mGetListAdapter.setGetList(mRequestGetLists);

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

    protected void requestTicketSendItemWaiter(ConfigValueModel configValueModel, int ticketId, String requestDetail) {
        VnyiUtils.LogException(TAG, "--------------start requestLoadInfoTicketPayment------------");

        try {
            int langId = VnyiPreference.getInstance(getContext()).getInt(VnyiApiServices.LANG_ID);

            String url = configValueModel.getLinkServer();

            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestTicketSendItemWaiter(url, ticketId, requestDetail, langId, new SoapListenerVyni() {
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


                        }
                    }
                    dismissDialog();

                }

                @Override
                public void onFail(Exception ex) {
                    VnyiUtils.LogException(getContext(), "onFail", TAG, "==> requestLoadInfoTicketPayment " + ex.getMessage());
                }

                @Override
                public void onFinished() {
                    VnyiUtils.LogException(TAG, "==> requestLoadInfoTicketPayment onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "catch", TAG, "==> requestLoadInfoTicketPayment " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "--------------end requestLoadInfoTicketPayment------------");
    }

    @OnClick(R.id.btnSendRequest)
    void onClickSendRequest() {
        if (mGetListAdapter.getItemCount() == 0) return;
        List<RequestGetList> requestGetLists = StreamSupport.stream(mGetListAdapter.getListRequest()).filter(RequestGetList::isChecked).collect(Collectors.toList());

        if (requestGetLists == null || requestGetLists.size() == 0) return;

        String requestDetail = StreamSupport.stream(requestGetLists).map(RequestGetList::getRequestNote).collect(Collectors.joining(", "));

        Log.e(TAG, "==> requestDetail:: " + requestDetail);
        int ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);
        if (TextUtils.isEmpty(requestDetail)) return;
        requestTicketSendItemWaiter(mConfigValueModel, ticketId, requestDetail);
    }

    @OnClick(R.id.btnCancelRquest)
    void onClickCancelRequest() {
        mActivity.changeTab(Constant.INDEX_MENU);
    }

}
