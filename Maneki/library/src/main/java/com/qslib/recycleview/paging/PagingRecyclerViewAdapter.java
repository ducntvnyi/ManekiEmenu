package com.qslib.recycleview.paging;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Dang on 6/3/2016.
 */
public abstract class PagingRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    protected static final int VIEW_TYPE_LOADING = 0;
    protected static final int VIEW_TYPE_DATA = 1;

    private boolean isLoadMore = true;
    private static final int NUMBER_INCREASE = 1;

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return getLoadingViewHolder(parent);
        } else {
            return onCreateView(parent, viewType);
        }
    }

    @Override
    public final int getItemCount() {
        try {
            int actualCount = getCount();
            if (actualCount == 0 || !isLoadMore) {
                return actualCount;
            } else {
                return actualCount + NUMBER_INCREASE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public final int getItemViewType(int position) {
        try {
            if (isLoadingView(position)) {
                return VIEW_TYPE_LOADING;
            } else {
                int viewType = getViewType(position);
                if (viewType == VIEW_TYPE_LOADING) {
                    throw new IndexOutOfBoundsException("0 index is reserved for the loading view");
                } else {
                    return viewType;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * @param position
     * @return
     */
    private boolean isLoadingView(int position) {
        return position == getCount() && isLoadMore;
    }

    /**
     * set loading more
     *
     * @param isLoadMore
     */
    public PagingRecyclerViewAdapter setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        return this;
    }

    /**
     * Returns the loading view to be shown at the bottom of the list.
     *
     * @return loading view
     */
    public abstract RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent);

    /**
     * The count of the number of items in the list. This does not include the loading item
     *
     * @return number of items in list
     */
    public abstract int getCount();

    /**
     * use PagingRecyclerViewAdapter.VIEW_TYPE_DATA
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>0 index is reserved for the loading view. So this function cannot return 0.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    public abstract int getViewType(int position);

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent
     * an item. This is the same as the onCreateViewHolder method in RecyclerView.Adapter,
     * except that it internally detects and handles the creation on the loading footer
     *
     * @param parent
     * @param viewType
     * @return
     * @return
     */
    public abstract T onCreateView(ViewGroup parent, int viewType);
}
