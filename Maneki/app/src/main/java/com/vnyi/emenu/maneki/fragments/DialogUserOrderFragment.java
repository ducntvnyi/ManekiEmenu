package com.vnyi.emenu.maneki.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qslib.fragment.BaseMainDialogFragment;
import com.qslib.jackson.JacksonUtils;
import com.qslib.network.NetworkUtils;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.UserAdapter;
import com.vnyi.emenu.maneki.customviews.DividerItemDecoration;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.UserOrder;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17. 
 */

public class DialogUserOrderFragment extends BaseMainDialogFragment {


    private static final String TAG = DialogUserOrderFragment.class.getSimpleName();

    @BindView(R.id.tvTitle)
    TextViewFont tvTitle;
    @BindView(R.id.rvUserOrderList)
    RecyclerView rvUserOrderList;

    private List<UserOrder> mUserOrders = new ArrayList<>();
    private Consumer<UserOrder> mConsumer;
    private UserAdapter mUserOrderAdapter;

    private int mBranchId;
    private String linkSerVer;

    public static DialogUserOrderFragment newInstance() {
        return new DialogUserOrderFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_order_fragment, container, false);
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
        loadData();
    }

    private void initViews() {
        try {
            mUserOrderAdapter = new UserAdapter(getContext(), mUserOrders, branch -> {
                mConsumer.accept(branch);
                dismiss();
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvUserOrderList.setAdapter(mUserOrderAdapter);
            rvUserOrderList.setLayoutManager(layoutManager);
            rvUserOrderList.addItemDecoration(new DividerItemDecoration(getContext()));
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }


    private void loadData() {
        Log.e(TAG, "==> loadData:: " + mUserOrders.size());
        if (mUserOrders.size() == 0) {
            loadTables();
        }
    }

    public DialogUserOrderFragment setBranchId(int branchId) {
        this.mBranchId = branchId;
        return this;
    }

    public DialogUserOrderFragment setListUser(List<UserOrder> listUser) {
        this.mUserOrders = listUser;
        return this;
    }

    public DialogUserOrderFragment setLinkServer(String url) {
        this.linkSerVer = url;
        return this;
    }

    public DialogUserOrderFragment setConsumer(Consumer<UserOrder> consumer) {
        this.mConsumer = consumer;
        return this;
    }

    protected void loadTables() {

        try {
            if (!NetworkUtils.isNetworkAvailable(getContext())) return;

            VnyiServices.requestConfigValueUserOrder(linkSerVer, this.mBranchId, 1, new SoapListenerVyni() {

                @Override
                public void onStarted() {
                    VnyiUtils.LogException(TAG, "==> requestConfigValueUserOrder onStarted ");
                    showDialog();
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> requestConfigValueUserOrder onSuccess ");
                    if (soapResponse == null) return;

                    if (soapResponse.getStatus().toLowerCase().equals("true")) {
                        if (soapResponse.getResult() != null) {
                            VnyiUtils.LogException(TAG, "==> requestConfigValueUserOrder onSuccess:: " + soapResponse.toString());
                            try {
                                JSONObject configValueObject = new JSONObject(soapResponse.getResult());

                                mUserOrders = JacksonUtils.convertJsonToObject(configValueObject.getString(VnyiApiServices.TABLE), new TypeReference<List<UserOrder>>() {
                                });
                                mUserOrderAdapter.setTableList(mUserOrders);
                                // save to local
                                VnyiUtils.LogException(TAG, "==> mUserOrders" + mUserOrders.toString());

                            } catch (JSONException e) {
                                VnyiUtils.LogException(TAG, "==> jsonObject passed error:  " + e.getMessage());
                            }

                        }
                    }

                }

                @Override
                public void onFail(Exception ex) {
                    dismissDialog();
                    VnyiUtils.LogException(getContext(), " requestConfigValueUserOrder onFail", TAG, ex.getMessage());
                }

                @Override
                public void onFinished() {
                    dismissDialog();
                    VnyiUtils.LogException(TAG, "==> loadBloadTablesranch onFinished ");
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), "initViews", TAG, e.getMessage());
        }
    }
}
