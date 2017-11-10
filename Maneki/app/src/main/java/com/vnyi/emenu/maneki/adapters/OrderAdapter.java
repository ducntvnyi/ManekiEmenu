package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.OrderItemModel;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/7/17.
 */

public class OrderAdapter extends BaseRecycleAdapter<OrderItemModel, OrderAdapter.ViewHolder> {


    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context mContext;
    private List<OrderItemModel> mModelList;

    private Consumer<OrderItemModel> mConsumer;
    private Consumer<OrderItemModel> mConsumerAddItem;
    private Consumer<OrderItemModel> mConsumerReduce;
    private boolean isPayment;

    public OrderAdapter(Context context, boolean isPayment, List<OrderItemModel> itemModels, Consumer<OrderItemModel> consumer, Consumer<OrderItemModel> consumerAddItem, Consumer<OrderItemModel> consumerReduce) {
        this.mContext = context;
        this.isPayment = isPayment;
        this.mModelList = itemModels;
        this.mConsumer = consumer;
        this.mConsumerAddItem = consumerAddItem;
        this.mConsumerReduce = consumerReduce;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            if (getItemCount() < 1) return;
            OrderItemModel itemModel = mModelList.get(position);
            if (itemModel == null) return;

            holder.binData(mContext, itemModel);

            holder.view.setOnClickListener(view -> {
                VnyiUtils.LogException(TAG, "==> itemModel:: " + itemModel.toString());
                mConsumer.accept(itemModel);
            });
            holder.tvAddQuantity.setOnClickListener(view -> {
                itemModel.setQuantity(itemModel.getQuantity() + 1);
                holder.tvQuantity.setText("" + itemModel.getQuantity() + "");
                mConsumerAddItem.accept(itemModel);
            });
            holder.tvReduceQuantity.setOnClickListener(view -> {
                itemModel.setQuantity(itemModel.getQuantity() - 1);
                holder.tvQuantity.setText("" + itemModel.getQuantity() + "");
                mConsumerReduce.accept(itemModel);
            });
        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public void setOrderItemModelList(List<OrderItemModel> orderItemModels) {
        this.mModelList = orderItemModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivItem)
        ImageView ivItem;
        @BindView(R.id.tvItemName)
        TextViewFont tvItemName;
        @BindView(R.id.tvQuantity)
        TextViewFont tvQuantity;
        @BindView(R.id.tvAddQuantity)
        TextViewFont tvAddQuantity;
        @BindView(R.id.tvReduceQuantity)
        TextViewFont tvReduceQuantity;
        @BindView(R.id.tvTotalMoney)
        TextViewFont tvTotalMoney;
        @BindView(R.id.llChangeQuantity)
        LinearLayout llChangeQuantity;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
            llChangeQuantity.setVisibility(isPayment ? View.GONE : View.VISIBLE);
        }

        private void binData(Context context, OrderItemModel itemModel) {
            try {
                tvItemName.setText(itemModel.getItemName());
                tvQuantity.setText("" + itemModel.getQuantity() + "");
                tvTotalMoney.setText("" + itemModel.getTotalMoney() + " VND");
            } catch (Exception e) {
                VnyiUtils.LogException(TAG, e);
            }

        }
    }
}
