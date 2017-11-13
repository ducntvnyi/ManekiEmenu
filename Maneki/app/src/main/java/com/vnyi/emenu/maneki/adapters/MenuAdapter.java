package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.ItemCategoryNoListNote;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/7/17.
 */

public class MenuAdapter extends BaseRecycleAdapter<Branch, MenuAdapter.ViewHolder> {


    private static final String TAG = MenuAdapter.class.getSimpleName();
    private Context mContext;
    private List<ItemCategoryNoListNote> mBranchList;

    private Consumer<ItemCategoryNoListNote> mConsumer;

    public MenuAdapter(Context context, List<ItemCategoryNoListNote> branches, Consumer<ItemCategoryNoListNote> consumer) {
        this.mContext = context;
        this.mBranchList = branches;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        ItemCategoryNoListNote categoryNoListNote = mBranchList.get(position);
        if (categoryNoListNote == null) return;
        categoryNoListNote.setPosition(position);
        holder.binData(mContext, categoryNoListNote);

        holder.view.setOnClickListener(view -> {
            VnyiUtils.LogException(TAG, "==> categoryNoListNote:: " + categoryNoListNote.toString());
            mConsumer.accept(categoryNoListNote);
        });
    }

    @Override
    public int getItemCount() {
        return mBranchList == null ? 0 : mBranchList.size();
    }

    public void setMenuList(List<ItemCategoryNoListNote> branchList) {
        this.mBranchList = branchList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivItem)
        ImageView ivItem;
        @BindView(R.id.tvMenu)
        TextViewFont tvMenu;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, ItemCategoryNoListNote categoryNoListNote) {
            tvMenu.setText(categoryNoListNote.getGroupName());
            if (categoryNoListNote.isSelected()) {
                view.setBackground(context.getDrawable(R.mipmap.bg_btn_white));
                tvMenu.setTextColor(ContextCompat.getColor(context, R.color.color_black));
//                ivItem.setVisibility(View.VISIBLE);
//                tvMenu.setVisibility(View.GONE);
            } else {
                tvMenu.setTextColor(ContextCompat.getColor(context, R.color.white));
                view.setBackground(null);
            }
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), ViewUtils.drawViewToBitmap(view));
//            ivItem.setBackground(bitmapDrawable);
        }
    }
}
