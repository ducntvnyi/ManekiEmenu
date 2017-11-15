package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.GetListAdapter;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.RequestGetList;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/6/17. 
 */

public class DialogCallWaiterFragment extends BaseMainDialogFragment {

    private static final String TAG = DialogCallWaiterFragment.class.getSimpleName();
    @BindView(R.id.rvGetList)
    RecyclerView rvGetList;

    private List<RequestGetList> mRequestGetLists;
    private GetListAdapter mGetListAdapter;
    private ConfigValueModel mConfigValueModel;

    public static DialogCallWaiterFragment newInstance() {
        return new DialogCallWaiterFragment();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_call_waiter_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        try {
            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            VnyiUtils.LogException(TAG, "==> requestRequestGetList:" + mRequestGetLists.size());
            mGetListAdapter = new GetListAdapter(getContext(), mRequestGetLists, requestGetList -> {

            });
            mGetListAdapter.setGetList(mRequestGetLists);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvGetList.setAdapter(mGetListAdapter);
            rvGetList.setLayoutManager(layoutManager);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.btnSendRequest)
    void onClickSendRequest() {
        try {
            if (mGetListAdapter.getItemCount() == 0) return;
            List<RequestGetList> requestGetLists = StreamSupport.stream(mGetListAdapter.getListRequest()).filter(RequestGetList::isChecked).collect(Collectors.toList());

            if (requestGetLists == null || requestGetLists.size() == 0) return;

            String requestDetail = StreamSupport.stream(requestGetLists).map(RequestGetList::getRequestNote).collect(Collectors.joining(", "));

            Log.e(TAG, "==> requestDetail:: " + requestDetail);
            int ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);
            if (TextUtils.isEmpty(requestDetail)) return;
            requestTicketSendItemWaiter(mConfigValueModel, ticketId, requestDetail);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.btnCancelRquest)
    void onClickCancelRequest() {
        
    }

    public DialogCallWaiterFragment setListRequest(List<RequestGetList> listRequest) {
        this.mRequestGetLists = listRequest;
        return this;
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

}
