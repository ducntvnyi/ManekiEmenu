package com.vnyi.emenu.maneki.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.adapters.OrderAdapter;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.OrderItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Hungnd on 11/6/17.
 */

public class PaymentFragment extends BaseFragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();

    @BindView(R.id.llHeaderTable)
    LinearLayout llHeaderTable;
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
    @BindView(R.id.tvTotalPaymen)
    TextViewFont tvTotalPaymen;
    @BindView(R.id.llAction)
    LinearLayout llAction;
    @BindView(R.id.tvCart)
    TextView tvCart;


    private List<OrderItemModel> mOrderItemModels;
    private OrderAdapter mOrderAdapter;


    public static Fragment newInstance() {
        Fragment fragment = new PaymentFragment();
        return fragment;
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.order_fragment;
    }

    @Override
    public void initViews() {

        // init menu adapter
        mOrderItemModels = new ArrayList<>();

        mOrderAdapter = new OrderAdapter(mContext, true, mOrderItemModels, orderItemModel -> {

        }, itemAdd -> {

        }, itemReduce -> {

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvItemOrder.setAdapter(mOrderAdapter);
        rvItemOrder.setLayoutManager(layoutManager);

    }

    @Override
    public void initData() {
        // new ArrayList<>
        mOrderItemModels.add(new OrderItemModel(1, "", "Sushi Ca ngu", 10, 100000f));
        mOrderItemModels.add(new OrderItemModel(2, "", "Sushi Ca ngu", 9, 200000f));
        mOrderItemModels.add(new OrderItemModel(3, "", "Sushi Ca ngu", 3, 400000f));
        mOrderItemModels.add(new OrderItemModel(4, "", "Sushi Ca ngu", 1, 500000f));
        mOrderItemModels.add(new OrderItemModel(5, "", "Sushi Ca ngu", 2, 600000f));
        mOrderItemModels.add(new OrderItemModel(6, "", "Sushi Ca ngu", 3, 700000f));
        mOrderItemModels.add(new OrderItemModel(7, "", "Sushi Ca ngu", 10, 800000f));
        mOrderItemModels.add(new OrderItemModel(8, "", "Sushi Ca ngu", 6, 900000f));
        mOrderItemModels.add(new OrderItemModel(9, "", "Sushi Ca ngu", 9, 770000f));
        mOrderItemModels.add(new OrderItemModel(10, "", "Sushi Ca ngu", 8, 140000f));
        mOrderItemModels.add(new OrderItemModel(11, "", "Sushi Ca ngu", 11, 190000f));
        mOrderItemModels.add(new OrderItemModel(12, "", "Sushi Ca ngu", 1, 220000f));
        mOrderAdapter.setOrderItemModelList(mOrderItemModels);
    }


}
