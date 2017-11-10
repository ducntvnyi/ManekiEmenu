package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.AnimationView;
import com.vnyi.emenu.maneki.models.ItemModel;
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
    private List<ItemModel> mItemModelList;

    private Consumer<AnimationView> mConsumer;

    public ItemAdapter(Context context, List<ItemModel> branches, Consumer<AnimationView> consumer) {
        this.mContext = context;
        this.mItemModelList = branches;
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
        ItemModel itemModel = mItemModelList.get(position);
        if (itemModel == null) return;

        holder.binData(mContext, itemModel);

        holder.view.setOnClickListener(view -> {
            AnimationView animationView = new AnimationView(itemModel, holder.ivCartItem, holder.view);
            VnyiUtils.LogException(TAG, "==> itemModel:: " + itemModel.toString());
            mConsumer.accept(animationView);
        });
    }

    @Override
    public int getItemCount() {
        return mItemModelList == null ? 0 : mItemModelList.size();
    }

    public void setItemModelList(List<ItemModel> branchList) {
        this.mItemModelList = branchList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


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

        private void binData(Context context, ItemModel itemModel) {
            tvItemName.setText(itemModel.getItemName());
            tvPrice.setText(itemModel.getPrice());

        }
    }
}
