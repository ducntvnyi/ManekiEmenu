package com.vnyi.emenu.maneki.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private int itemCounterCurrent = 0;
    private boolean isUpdateItem;
    private ConfigValueModel mConfigValueModel;
    private TicketUpdateInfo mTicketUpdateInfo;
    private List<TicketItemOrder1> mItemOrderList = new ArrayList<>();
    private TicketItemOrderMoney mTicketItemOrderMoney;
    private int ticketId;

    private String tableName;

    public static Fragment newInstance() {
        return new OrderFragment();
    }

    private void cancelItem(TicketItemOrder1 itemReduce) {

        requestTicketCancelItem(mConfigValueModel, ticketId, aBoolean -> {
            mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() - itemReduce.getItemAmount());
            mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() - itemReduce.getItemAmount());
            tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
            tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");

            mOrderItemModels.remove(itemReduce);
            mOrderAdapter.notifyDataSetChanged();
        });


    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.order_fragment;
    }

    @Override
    public void initViews() {
        debounce();
        // init menu adapter
        tableName = VnyiPreference.getInstance(getContext()).getString(Constant.KEY_TABLE_NAME);
        tvTableName.setText(tableName);
        mOrderItemModels = new ArrayList<>();

        mOrderAdapter = new OrderAdapter(mContext, false, mOrderItemModels, orderItemModel -> {
            //TODO onClick
        }, itemAdd -> {
            ++itemCounter;
            tvCart.setText("" + itemCounter + "");
            mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() + itemAdd.getItemAmount());
            mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() + itemAdd.getItemAmount());
            tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
            tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");

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
                }).show(getFragmentManager(), "");

            } else {
                --itemCounter;
                tvCart.setText("" + itemCounter + "");
                mTicketItemOrderMoney.setItemAmount(mTicketItemOrderMoney.getItemAmount() - itemReduce.getItemAmount());
                mTicketItemOrderMoney.setTotalAmount(mTicketItemOrderMoney.getTotalAmount() - itemReduce.getItemAmount());
                tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
                tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");
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

    }

    private void debounce() {
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
    }

    @Override
    public void initData() {

        ticketId = VnyiPreference.getInstance(getContext()).getInt(Constant.KEY_TICKET_ID);

        mConfigValueModel = VnyiPreference.getInstance(getContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);
        int getType = 2;

        requestGetTicketItemOrder(mConfigValueModel, ticketId, getType, this::updateUI);

    }

    private void updateUI(TicketItemOrderModel ticketItemOrderModel) {
        mOrderItemModels = ticketItemOrderModel.getTicketItemOrders();
        mOrderAdapter.setTicketItemOrderList(mOrderItemModels);
        mTicketItemOrderMoney = ticketItemOrderModel.getTicketItemOrderMoney();

        tvTotalMoney.setText("" + mTicketItemOrderMoney.getItemAmount() + " VND");
        tvSaleOffPrice.setText("" + mTicketItemOrderMoney.getDiscountAmount() + " VND");
        tvVAT.setText("" + mTicketItemOrderMoney.getvATAmount() + " VND");
        tvTotalPayment.setText("" + mTicketItemOrderMoney.getTotalAmount() + " VND");
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
        private int orderId;

        public UpdateItemOrderTask(List<TicketItemOrder1> itemOrderList, ConfigValueModel configValueModel, int ticketId) {
            this.ticketItemOrders = itemOrderList;
            this.configValueModel = configValueModel;
            this.ticketId = ticketId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "==> UpdateItemOrderTask onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e(TAG, "==> UpdateItemOrderTask doInBackground");
            updateOrder(ticketItemOrders, this.configValueModel, this.ticketId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastUtils.showToast(mContext, "Order successfully!!");
            Log.e(TAG, "==> UpdateItemOrderTask onPostExecute");
        }
    }

    private void updateOrder(List<TicketItemOrder1> itemOrderList, ConfigValueModel configValueModel, int ticketId) {
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
    }


    @OnClick(R.id.btnOrder)
    void onOrderClick() {
        try {
            DialogConfirmOrderFragment.newInstance()
                    .setConsumer(isValidCode -> {
                        requestPostTicketSendItemOrder(mConfigValueModel, ticketId, sendOrder -> {
                            // Dat hang thanh k va k con mon nao
                            if (sendOrder) {
                                ToastUtils.showToast(getContext(), "Đặt món thành công!");
                                VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET, null);
                                mActivity.changeTab(Constant.INDEX_MENU);
                            } else {
                                ToastUtils.showToast(getContext(), "Đặt món chưa thành công!");
                            }
                        });
                    })
                    .show(getFragmentManager(), "");
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
        mActivity.showProgressDialog();
        requestPostTicketCancelAllItemOrdering(mConfigValueModel, ticketId, isCallOrder -> {
//            VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET, null);
            mActivity.changeTab(Constant.INDEX_MENU);
            if (isCallOrder) {
//                VnyiPreference.getInstance(getContext()).putObject(Constant.KEY_TICKET, null);
                clearData();
                mActivity.hideProgressDialog();
                mActivity.changeTab(Constant.INDEX_MENU);
            } else {
                ToastUtils.showToast(getContext(), "Huy mon khong thanh cong!");
            }
        });
    }

    private void clearData() {

        mItemOrderList.clear();
        mOrderItemModels.clear();
        mOrderAdapter.setTicketItemOrderList(mOrderItemModels);
        tvTotalMoney.setText("0 VND");
        tvSaleOffPrice.setText("0 VND");
        tvVAT.setText("0 VND");
        tvTotalPayment.setText("0 VND");
    }
}
