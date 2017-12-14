package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.PaymentItemAdapter;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.TicketPaymentModel;
import com.vnyi.emenu.maneki.models.response.TicketPayment;
import com.vnyi.emenu.maneki.models.response.TicketPaymentMoney;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Hungnd on 11/6/17.
 */

public class PaymentFragment extends BaseFragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();

    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.llHeaderTable)
    LinearLayout llHeaderTable;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;
    @BindView(R.id.rvItemOrder)
    RecyclerView rvItemOrder;
    @BindView(R.id.PriceTotal)
    LinearLayout priceTotal;
    @BindView(R.id.tvTotalMoney)
    TextViewFont tvTotalMoney;
    @BindView(R.id.tvSaleOffPrice)
    TextViewFont tvSaleOffPrice;
    @BindView(R.id.tvVAT)
    TextViewFont tvVAT;
    @BindView(R.id.tvTotalPayment)
    TextViewFont tvTotalPayment;

    private ConfigValueModel mConfigValueModel;
    private int ticketId;
    private List<TicketPayment> ticketPayments;
    private PaymentItemAdapter paymentItemAdapter;


    private TicketPaymentMoney mTicketItemOrderMoney;

    public static Fragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.payment_fragment;
    }

    @Override
    public void initViews() {
        try {
            ticketPayments = new ArrayList<>();
            paymentItemAdapter = new PaymentItemAdapter(mContext, true, ticketPayments);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvItemOrder.setAdapter(paymentItemAdapter);
            rvItemOrder.setLayoutManager(layoutManager);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " initViews onFail", TAG, e.getMessage());
        }

    }

    @Override
    public void initData() {
        try {
            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);

            loadTableName();

            requestLoadInfoTicketPayment(mConfigValueModel, ticketId, this::updateUI);
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " initData", TAG, e.getMessage());
        }
    }

    private void loadTableName() {
        try {
            String tableName = VnyiPreference.getInstance(getContext()).getString(Constant.KEY_TABLE_NAME);
            if (TextUtils.isEmpty(tableName)) {
                int tableId = Integer.parseInt(mConfigValueModel.getTableName().getConfigValue());
                tableName = getTableName(tableId);
            }
            tvTableName.setText(tableName);
        } catch (NumberFormatException e) {
            VnyiUtils.LogException(mContext, "loadTableName", TAG, e.getMessage());
        }
    }

    private void updateUI(TicketPaymentModel ticketItemOrderModel) {
        try {
            mTicketItemOrderMoney = ticketItemOrderModel.getTicketPaymentMoney();
            ticketPayments = ticketItemOrderModel.getTicketPayments();
            paymentItemAdapter.setTicketPaymentList(ticketPayments);

            tvTotalMoney.setText(mTicketItemOrderMoney.getItemMoney());
            tvSaleOffPrice.setText(mTicketItemOrderMoney.getDiscount());
            tvVAT.setText(mTicketItemOrderMoney.getVAT());
            tvTotalPayment.setText(mTicketItemOrderMoney.getTotalMoney());
        } catch (Exception e) {
            VnyiUtils.LogException(getContext(), " updateUI", TAG, e.getMessage());
        }
    }

    @OnClick(R.id.btnPayment)
    void onBtnPaymentClick() {
        requestPayment();
    }

    private void requestPayment() {
        requestTicketProcessingPayment(mConfigValueModel, ticketId, payment -> {
            if (payment) {
                ToastUtils.showToast(mContext, "Payment successfully!!");
            } else {
                ToastUtils.showToast(mContext, "Payment failed!!");
            }
        });
    }
}
