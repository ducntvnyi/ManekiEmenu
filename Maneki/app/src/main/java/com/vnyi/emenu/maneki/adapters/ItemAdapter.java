package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.AnimationView;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.ItemCategoryDetail;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class ItemAdapter extends BaseRecycleAdapter<ItemModel, ItemAdapter.ViewHolder> {


    private static final String TAG = ItemAdapter.class.getSimpleName();
    private Context mContext;
    private List<ItemCategoryDetail> mItemModelList;

    private Consumer<AnimationView> mConsumer;

    public ItemAdapter(Context context, List<ItemCategoryDetail> categoryDetails, Consumer<AnimationView> consumer) {
        this.mContext = context;
        this.mItemModelList = categoryDetails;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        ItemCategoryDetail itemModel = mItemModelList.get(position);
        if (itemModel == null) return;

        holder.binData(mContext, itemModel);

        holder.view.setOnClickListener(view -> {
            AnimationView animationView = new AnimationView(itemModel, holder.ivCartItemTemp, holder.view);
            VnyiUtils.LogException(TAG, "==> itemModel:: " + itemModel.toString());
            mConsumer.accept(animationView);
        });
    }

    @Override
    public int getItemCount() {
        return mItemModelList == null ? 0 : mItemModelList.size();
    }

    public void setItemModelList(List<ItemCategoryDetail> branchList) {
        this.mItemModelList = branchList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ivCartItemTemp)
        ImageView ivCartItemTemp;
        @BindView(R.id.ivCartItem)
        ImageView ivCartItem;
        @BindView(R.id.tvItemName)
        TextViewFont tvItemName;
        @BindView(R.id.tvPrice)
        TextViewFont tvPrice;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, ItemCategoryDetail itemModel) {
            tvItemName.setText(itemModel.getItemNameLang());
            tvPrice.setText("" + itemModel.getItemPrice() + "");
            Log.e(TAG, "==> getItemUrlImage::" + VnyiApiServices.URL_CONFIG + itemModel.getItemUrlImage());
            Glide.with(context)
                    .load(VnyiApiServices.URL_CONFIG + itemModel.getItemUrlImage())
                    .fitCenter()
                    .centerCrop()
                    .into(ivCartItem);

        }
    }
}
