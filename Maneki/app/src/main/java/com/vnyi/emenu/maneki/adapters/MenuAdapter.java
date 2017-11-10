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
    private List<Branch> mBranchList;

    private Consumer<Branch> mConsumer;

    public MenuAdapter(Context context, List<Branch> branches, Consumer<Branch> consumer) {
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
        Branch branch = mBranchList.get(position);
        if (branch == null) return;
        branch.setPosition(position);
        holder.binData(mContext, branch);

        holder.view.setOnClickListener(view -> {
            VnyiUtils.LogException(TAG, "==> branch:: " + branch.toString());
            mConsumer.accept(branch);
        });
    }

    @Override
    public int getItemCount() {
        return mBranchList == null ? 0 : mBranchList.size();
    }

    public void setBranchList(List<Branch> branchList) {
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

        private void binData(Context context, Branch branch) {
            tvMenu.setText(branch.getBranchName());
            if (branch.isSelected()) {
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
