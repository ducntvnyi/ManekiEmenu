package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.Branch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class BranchAdapter extends BaseRecycleAdapter<ItemModel, BranchAdapter.ViewHolder> {


    private static final String TAG = BranchAdapter.class.getSimpleName();
    private Context mContext;
    private List<Branch> branchList;
    private Consumer<Branch> mConsumer;

    public BranchAdapter(Context context, List<Branch> branches, Consumer<Branch> consumer) {
        this.mContext = context;
        this.branchList = branches;
        this.mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.brach_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemCount() < 1) return;
        Branch branch = branchList.get(position);
        if (branch == null) return;

        holder.binData(mContext, branch);

        holder.view.setOnClickListener(view -> {
            mConsumer.accept(branch);
        });
    }

    @Override
    public int getItemCount() {
        return branchList == null ? 0 : branchList.size();
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvBranch)
        TextViewFont tvBranch;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        private void binData(Context context, Branch branch) {
            tvBranch.setText(branch.getBranchName());


        }
    }
}
