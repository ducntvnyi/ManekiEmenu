package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private String tableName;
    private ConfigValueModel mConfigValueModel;
    private int ticketId;
    private List<TicketPayment> ticketPayments;
    private PaymentItemAdapter paymentItemAdapter;


    private TicketPaymentMoney mTicketItemOrderMoney;

    public static Fragment newInstance() {
        Fragment fragment = new PaymentFragment();
        return fragment;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.payment_fragment;
    }

    @Override
    public void initViews() {
        // init menu adapter
        tableName = VnyiPreference.getInstance(getContext()).getString(Constant.KEY_TABLE_NAME);
        tvTableName.setText(tableName);

        ticketPayments = new ArrayList<>();
        paymentItemAdapter = new PaymentItemAdapter(mContext, true, ticketPayments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvItemOrder.setAdapter(paymentItemAdapter);
        rvItemOrder.setLayoutManager(layoutManager);

    }

    @Override
    public void initData() {

        mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
        ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);

        requestLoadInfoTicketPayment(mConfigValueModel, ticketId, this::updateUI);
    }

    private void updateUI(TicketPaymentModel ticketItemOrderModel) {

        mTicketItemOrderMoney = ticketItemOrderModel.getTicketPaymentMoney();
        ticketPayments = ticketItemOrderModel.getTicketPayments();
        paymentItemAdapter.setTicketPaymentList(ticketPayments);

        tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
        tvSaleOffPrice.setText("" + mTicketItemOrderMoney.getDiscountItem() + " VND");
        tvVAT.setText("" + mTicketItemOrderMoney.getVatAmount() + " VND");
        tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");
    }

    @OnClick(R.id.btnPayment)
    void onBtnPaymentClick() {
        requestPayment();
    }

    private void requestPayment() {
        requestTicketProcessingPayment(mConfigValueModel, ticketId, payment -> {
            if (payment) {
//                mActivity.changeTab(Constant.INDEX_MENU);
                ToastUtils.showToast(mContext, "Payment successfully!!");
            } else {
                ToastUtils.showToast(mContext, "Payment failed!!");
            }
        });
    }
}
