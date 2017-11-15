package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.TicketItemOrder1;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/7/17.
 */

public class OrderAdapter extends BaseRecycleAdapter<TicketItemOrder1, OrderAdapter.ViewHolder> {


    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context mContext;
    private List<TicketItemOrder1> mModelList;

    private Consumer<TicketItemOrder1> mConsumer;
    private Consumer<TicketItemOrder1> mConsumerAddItem;
    private Consumer<TicketItemOrder1> mConsumerReduce;
    private boolean isPayment;

    public OrderAdapter(Context context, boolean isPayment, List<TicketItemOrder1> itemModels, Consumer<TicketItemOrder1> consumer, Consumer<TicketItemOrder1> consumerAddItem, Consumer<TicketItemOrder1> consumerReduce) {
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
            TicketItemOrder1 itemModel = mModelList.get(position);
            if (itemModel == null) return;

            holder.binData(mContext, itemModel);

            holder.view.setOnClickListener(view -> {
                VnyiUtils.LogException(TAG, "==> itemModel:: " + itemModel.toString());
                mConsumer.accept(itemModel);
            });
            holder.tvAddQuantity.setOnClickListener(view -> {
                mModelList.get(position).setItemQuantity(itemModel.getItemQuantity() + 1);
                mModelList.get(position).setItemAmount(itemModel.getItemAmount() + itemModel.getItemPrice());
                holder.tvQuantity.setText("" + itemModel.getItemQuantity() + "");
                holder.tvTotalMoney.setText("" + itemModel.getItemAmount() + "");
                mConsumerAddItem.accept(itemModel);
            });
            holder.tvReduceQuantity.setOnClickListener(view -> {
                if (mModelList.get(position).getItemQuantity() == 0) {
                    mModelList.get(position).setItemQuantity(0);
                    mModelList.get(position).setItemAmount(0d);
                    holder.tvQuantity.setText("0");
                    holder.tvTotalMoney.setText("0 VND");
                } else {
                    mModelList.get(position).setItemQuantity(itemModel.getItemQuantity() - 1);
                    mModelList.get(position).setItemAmount(itemModel.getItemAmount() - itemModel.getItemPrice());
                    holder.tvQuantity.setText("" + itemModel.getItemQuantity() + "");
                    holder.tvTotalMoney.setText("" + itemModel.getItemAmount() + "");
                }

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

    public void setTicketItemOrderList(List<TicketItemOrder1> TicketItemOrder1s) {
        this.mModelList = TicketItemOrder1s;
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

        private void binData(Context context, TicketItemOrder1 itemModel) {
            try {
                tvItemName.setText(itemModel.getItemName());
                tvQuantity.setText("" + itemModel.getItemQuantity() + "");
                tvTotalMoney.setText("" + (int) itemModel.getItemAmount() + " VND");
                Log.e(TAG, "==> getItemImage::" + VnyiApiServices.URL_CONFIG + itemModel.getItemImage());
                Glide.with(context)
                        .load(VnyiApiServices.URL_CONFIG + itemModel.getItemImage())
                        .fitCenter()
                        .centerCrop()
                        .error(R.mipmap.ic_app_default)
                        .into(ivItem);

            } catch (Exception e) {
                VnyiUtils.LogException(TAG, e);
            }

        }
    }
}
