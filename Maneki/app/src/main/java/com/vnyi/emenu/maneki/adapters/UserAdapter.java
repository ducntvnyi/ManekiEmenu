package com.vnyi.emenu.maneki.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.models.ItemModel;
import com.vnyi.emenu.maneki.models.response.UserOrder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.function.Consumer;

/**
 * Created by Hungnd on 11/8/17.
 */

public class UserAdapter extends BaseRecycleAdapter<ItemModel, UserAdapter.ViewHolder> {


    private static final String TAG = UserAdapter.class.getSimpleName();
    private Context mContext;
    private List<UserOrder> userOrders;
    private Consumer<UserOrder> mConsumer;

    public UserAdapter(Context context, List<UserOrder> userOrders, Consumer<UserOrder> consumer) {
        this.mContext = context;
        this.userOrders = userOrders;
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
        UserOrder UserOrder = userOrders.get(position);
        if (UserOrder == null) return;

        holder.binData(mContext, UserOrder);

        holder.view.setOnClickListener(view -> {
            mConsumer.accept(UserOrder);
        });
    }

    @Override
    public int getItemCount() {
        return userOrders == null ? 0 : userOrders.size();
    }

    public void setTableList(List<UserOrder> UserOrders) {
        this.userOrders = UserOrders;
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

        private void binData(Context context, UserOrder userOrder) {
            tvBranch.setText(userOrder.getObjName());
        }
    }
}
