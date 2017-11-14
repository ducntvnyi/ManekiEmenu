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
import com.vnyi.emenu.maneki.models.response.TicketPayment;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hungnd on 11/7/17.
 */

public class PaymentItemAdapter extends BaseRecycleAdapter<TicketPayment, PaymentItemAdapter.ViewHolder> {


    private static final String TAG = PaymentItemAdapter.class.getSimpleName();
    private Context mContext;
    private List<TicketPayment> mModelList;


    public PaymentItemAdapter(Context context, boolean isPayment, List<TicketPayment> itemModels) {
        this.mContext = context;

        this.mModelList = itemModels;

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
            TicketPayment itemModel = mModelList.get(position);
            if (itemModel == null) return;

            holder.binData(mContext, itemModel);

        } catch (Exception e) {
            VnyiUtils.LogException(TAG, e);
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public void setTicketPaymentList(List<TicketPayment> TicketPayments) {
        this.mModelList = TicketPayments;
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
            llChangeQuantity.setVisibility(View.GONE);
        }

        private void binData(Context context, TicketPayment itemModel) {
            try {
                tvItemName.setText(itemModel.getItemName());
                tvQuantity.setText("" + itemModel.getItemQuantity() + "");
                tvTotalMoney.setText("" + (int) itemModel.getItemAmount() + " VND");
                Log.e(TAG, "==> getItemImage::" + VnyiApiServices.URL_CONFIG + itemModel.getItemImageUrl());
                Glide.with(context)
                        .load(VnyiApiServices.URL_CONFIG + itemModel.getItemImageUrl())
                        .fitCenter()
                        .centerCrop()
                        .into(ivItem);
            } catch (Exception e) {
                VnyiUtils.LogException(TAG, e);
            }

        }
    }
}
