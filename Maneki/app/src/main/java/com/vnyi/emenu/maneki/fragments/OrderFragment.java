package com.vnyi.emenu.maneki.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.qslib.util.ToastUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.OrderAdapter;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.TicketItemOrderModel;
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.models.response.Table;
import com.vnyi.emenu.maneki.models.response.TicketItemOrder1;
import com.vnyi.emenu.maneki.models.response.TicketItemOrderMoney;
import com.vnyi.emenu.maneki.models.response.TicketUpdateInfo;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/6/17.
 */

public class OrderFragment extends BaseFragment {

    private static final String TAG = OrderFragment.class.getSimpleName();

    @BindView(R.id.llHeaderTable)
    LinearLayout llHeaderTable;
    @BindView(R.id.rvItemOrder)
    RecyclerView rvItemOrder;
    @BindView(R.id.PriceTotal)
    LinearLayout priceTotal;
    @BindView(R.id.tvTableName)
    TextViewFont tvTableName;
    @BindView(R.id.tvTotalMoney)
    TextViewFont tvTotalMoney;
    @BindView(R.id.tvSaleOffPrice)
    TextViewFont tvSaleOffPrice;
    @BindView(R.id.tvVAT)
    TextViewFont tvVAT;
    @BindView(R.id.tvTotalPayment)
    TextViewFont tvTotalPayment;
    @BindView(R.id.llAction)
    LinearLayout llAction;
    @BindView(R.id.tvCart)
    TextView tvCart;


    private List<TicketItemOrder1> mOrderItemModels;
    private OrderAdapter mOrderAdapter;

    private int itemCounter = 0;
    private ConfigValueModel mConfigValueModel;
    private TicketUpdateInfo mTicketUpdateInfo;
    private List<TicketItemOrder1> mItemOrderList = new ArrayList<>();
    private TicketItemOrderMoney mTicketItemOrderMoney;
    private int ticketId;
    private String mTableName;

    public static Fragment newInstance() {
        return new OrderFragment();
    }

    private void cancelItem(TicketItemOrder1 itemReduce) {

        requestTicketCancelItem(mConfigValueModel, ticketId, aBoolean -> {
            try {
                mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() - itemReduce.getItemAmount());
                mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() - itemReduce.getItemAmount());
                tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
                tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");

                mOrderItemModels.remove(itemReduce);
                mOrderAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                VnyiUtils.LogException(mContext, "requestTicketCancelItem", TAG, e.getMessage());
            }
        });


    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.order_fragment;
    }

    @Override
    public void initViews() {
        try {
            debounce();
            mOrderItemModels = new ArrayList<>();
            mOrderAdapter = new OrderAdapter(mContext, false, mOrderItemModels, orderItemModel -> {
                //TODO onClick
            }, itemAdd -> {
                ++itemCounter;
                tvCart.setText("" + itemCounter + "");

                mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() + itemAdd.getItemAmount());
                mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() + itemAdd.getItemAmount());
                tvTotalMoney.setText(mTicketItemOrderMoney.getItemMoney());
                tvTotalPayment.setText(mTicketItemOrderMoney.getTotalMoney());

                if (mItemOrderList.size() > 0) {
                    boolean isPresent = StreamSupport.stream(mItemOrderList).filter(item -> item.getItemId() == itemAdd.getItemId()).findFirst().isPresent();
                    if (isPresent) {
                        StreamSupport.stream(mItemOrderList).filter(item -> item.getItemId() == itemAdd.getItemId()).findFirst().get().setItemQuantity(itemAdd.getItemQuantity());
                    } else {
                        mItemOrderList.add(itemAdd);
                    }
                } else {
                    mItemOrderList.add(itemAdd);
                }

            }, itemReduce -> {

                if (itemReduce.getItemQuantity() == 0) {
                    DialogRemoveItemFragment.newInstance().setConsumer(removeItem -> {
                        if (removeItem) {
                            cancelItem(itemReduce);
                        } else {
                            StreamSupport.stream(mOrderItemModels).filter(item -> item.getItemId() == itemReduce.getItemId()).findFirst().get().setItemQuantity(1);
                            StreamSupport.stream(mOrderItemModels).filter(item -> item.getItemId() == itemReduce.getItemId()).findFirst().get().setItemAmount(itemReduce.getItemAmount());
                            mOrderAdapter.notifyDataSetChanged();
                        }
                    }).setTableName(mTableName).show(getFragmentManager(), "");

                } else {
                    --itemCounter;
                    tvCart.setText("" + itemCounter + "");

                    mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() - itemReduce.getItemAmount());
                    mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() - itemReduce.getItemAmount());
                    tvTotalMoney.setText(mTicketItemOrderMoney.getItemMoney());
                    tvTotalPayment.setText(mTicketItemOrderMoney.getTotalMoney());

                    if (mItemOrderList.size() > 0) {
                        boolean isPresent = StreamSupport.stream(mItemOrderList).filter(item -> item.getItemId() == itemReduce.getItemId()).findFirst().isPresent();
                        if (isPresent) {
                            StreamSupport.stream(mItemOrderList).filter(item -> item.getItemId() == itemReduce.getItemId()).findFirst().get().setItemQuantity(itemReduce.getItemQuantity());
                        } else {
                            mItemOrderList.add(itemReduce);
                        }
                    } else {
                        mItemOrderList.add(itemReduce);
                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvItemOrder.setAdapter(mOrderAdapter);
            rvItemOrder.setLayoutManager(layoutManager);

        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "initViews", TAG, e.getMessage());
        }

    }


    private void debounce() {
        try {
            RxTextView.textChanges(tvCart)
                    .debounce(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CharSequence>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CharSequence value) {
                            updateItem(value);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "debounce", TAG, e.getMessage());
        }
    }

    @Override
    public void initData() {
        try {
            mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
            ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);
            int getType = 2;
            loadTableName();
            requestGetTicketItemOrder(mConfigValueModel, ticketId, getType, this::updateUI);
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "initData", TAG, e.getMessage());
        }

    }

    private void loadTableName() {
        try {
            mTableName = VnyiPreference.getInstance(getContext()).getString(Constant.KEY_TABLE_NAME);
            if (TextUtils.isEmpty(mTableName)) {
                int tableId = Integer.parseInt(mConfigValueModel.getTableName().getConfigValue());
                mTableName = getTableName(tableId);
            }
            Log.e(TAG,"==> tableName Order: " + tableName);
            tvTableName.setText(mTableName);
        } catch (NumberFormatException e) {
            VnyiUtils.LogException(mContext, "loadTableName", TAG, e.getMessage());
        }
    }

    private void updateUI(TicketItemOrderModel ticketItemOrderModel) {
        try {
            mOrderItemModels = ticketItemOrderModel.getTicketItemOrders();
            mOrderAdapter.setTicketItemOrderList(mOrderItemModels);
            mTicketItemOrderMoney = ticketItemOrderModel.getTicketItemOrderMoney();

            tvTotalMoney.setText(mTicketItemOrderMoney.getItemMoney());
            tvSaleOffPrice.setText(mTicketItemOrderMoney.getDiscount());
            tvVAT.setText(mTicketItemOrderMoney.getVat());
            tvTotalPayment.setText(mTicketItemOrderMoney.getTotalMoney());
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "updateUI", TAG, e.getMessage());
        }
    }

    private void updateItem(CharSequence charSequence) {
        if (itemCounter == 0) return;
        Log.e(TAG, "==> updateItem:: " + charSequence);
        new UpdateItemOrderTask(mItemOrderList, mConfigValueModel, ticketId).execute();
    }

    private class UpdateItemOrderTask extends AsyncTask<Void, Void, Void> {

        private List<TicketItemOrder1> ticketItemOrders;
        private ConfigValueModel configValueModel;
        private int ticketId;

        private UpdateItemOrderTask(List<TicketItemOrder1> itemOrderList, ConfigValueModel configValueModel, int ticketId) {
            this.ticketItemOrders = itemOrderList;
            this.configValueModel = configValueModel;
            this.ticketId = ticketId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            updateOrder(ticketItemOrders, this.configValueModel, this.ticketId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastUtils.showToast(mContext, "Order successfully!!");
        }
    }

    private void updateOrder(List<TicketItemOrder1> itemOrderList, ConfigValueModel configValueModel, int ticketId) {
        try {
            for (TicketItemOrder1 order1 : itemOrderList) {
                ItemCategoryDetail itemCategoryDetail = new ItemCategoryDetail();

                itemCategoryDetail.setItemId(order1.getItemId());
                itemCategoryDetail.setUomId(order1.getUomId());
                itemCategoryDetail.setItemPrice(order1.getItemPrice());
                itemCategoryDetail.setItemDiscountPer((int) order1.getItemDiscountPer());
                itemCategoryDetail.setOrderDetailId(order1.getOrderDetailId());

                requestPostTicketUpdateItem(configValueModel, ticketId, (order1.getItemQuantity()), itemCategoryDetail, ticketUpdateInfo -> {
                    StreamSupport.stream(itemOrderList).forEach(item -> item.setOrderDetailId(item.getItemId() == ticketUpdateInfo.getItemId() ?
                            ticketUpdateInfo.getOrderDetailId() + "" : item.getOrderDetailId()));
                });
            }
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "updateOrder", "UpdateItemOrderTask", e.getMessage());
        }
    }


    @OnClick(R.id.btnOrder)
    void onOrderClick() {
        try {
            DialogConfirmOrderFragment.newInstance().setConsumer(isValidCode -> {
                requestPostTicketSendItemOrder(mConfigValueModel, ticketId, sendOrder -> {
                    try {
                        if (sendOrder) {
                            ToastUtils.showToast(getContext(), getString(R.string.order_item_successfully));
                            VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET, null);
                            mActivity.changeTab(Constant.INDEX_MENU);
                        } else {
                            ToastUtils.showToast(getContext(), getString(R.string.order_item_failed));
                        }
                    } catch (Exception e) {
                        VnyiUtils.LogException(mContext, "DialogConfirmOrderFragment", TAG, e.getMessage());
                    }
                });
            }).show(getFragmentManager(), "");

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }


    @OnClick(R.id.btnAddItem)
    void onAddItemClick() {
        mActivity.changeTab(Constant.INDEX_MENU);
    }


    @OnClick(R.id.btnCancelOrder)
    void onCancelOrderClick() {
        try {
            mActivity.showProgressDialog();
            requestPostTicketCancelAllItemOrdering(mConfigValueModel, ticketId, isCallOrder -> {
                mActivity.changeTab(Constant.INDEX_MENU);
                if (isCallOrder) {
                    clearData();
                    mActivity.hideProgressDialog();
                    mActivity.changeTab(Constant.INDEX_MENU);
                } else {
                    ToastUtils.showToast(getContext(), getString(R.string.cancel_order_failed));
                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "onCancelOrderClick", TAG, e.getMessage());
        }
    }

    private void clearData() {
        try {
            mItemOrderList.clear();
            mOrderItemModels.clear();
            mOrderAdapter.setTicketItemOrderList(mOrderItemModels);
            tvTotalMoney.setText("0 VND");
            tvSaleOffPrice.setText("0 VND");
            tvVAT.setText("0 VND");
            tvTotalPayment.setText("0 VND");
        } catch (Exception e) {
            VnyiUtils.LogException(mContext, "clearData", TAG, e.getMessage());
        }
    }
}
