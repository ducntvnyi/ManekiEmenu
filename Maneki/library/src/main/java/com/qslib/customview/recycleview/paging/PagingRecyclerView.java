package com.qslib.customview.recycleview.paging;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.qslib.library.R;

/**
 * Created by Dang on 6/3/2016.
 */
public class PagingRecyclerView extends RecyclerView {
    // default visible threshold
    private static final int VISIBLE_THRESHOLD = 2;

    private boolean isLoading, isLoadMore;
    private PagingRecyclerListener pagingRecyclerListener;
    private LinearLayoutManager mLayoutManager;

    private PagingRecyclerViewAdapter mAdapter;
    private int mVisibleThreshold;

    /**
     * @param context
     */
    public PagingRecyclerView(Context context) {
        super(context);
        init(null);
    }

    /**
     * @param context
     * @param attrs
     */
    public PagingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PagingRecyclerView, 0, 0);
        init(attr);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PagingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PagingRecyclerView, 0, 0);
        init(attr);
    }

    /**
     * init attribute
     *
     * @param attr
     */
    private void init(TypedArray attr) {
        try {
            this.addOnScrollListener(mScrollListener);
            this.isLoading = false;
            this.isLoadMore = true;
            if (attr != null) {
                this.mVisibleThreshold = attr.getInt(R.styleable.PagingRecyclerView_visible_threshold, VISIBLE_THRESHOLD);
            } else {
                this.mVisibleThreshold = VISIBLE_THRESHOLD;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof PagingRecyclerViewAdapter) {
            this.mAdapter = (PagingRecyclerViewAdapter) adapter;
        } else {
            throw new UnsupportedOperationException("This RecyclerView's adapter should extend from PagingRecyclerViewAdapter");
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        try {
            if (layout instanceof LinearLayoutManager) {
                this.mLayoutManager = (LinearLayoutManager) layout;
            } else {
                throw new UnsupportedOperationException("This recycler view can only be used with LinearLayoutManager");
            }
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public PagingRecyclerView setLoadMoreListener(PagingRecyclerListener pagingRecyclerListener) {
        this.pagingRecyclerListener = pagingRecyclerListener;
        return this;
    }

    /**
     * scroll recycler view
     */
    private final OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            try {
                int visibleItemCount = getChildCount();
                int totalItemCount = mAdapter.getItemCount();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (isLoadMore && !isLoading &&
                        (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
                    // End has been reached
                    isLoading = true;
                    if (pagingRecyclerListener != null) pagingRecyclerListener.onLoadMore();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * This informs the RecyclerView that data has been loaded.
     * <p>
     * This also calls the attached adapter's {@link Adapter#notifyDataSetChanged()} method,
     * so the implementing class only needs to call this method
     */
    @SuppressWarnings("unused")
    public PagingRecyclerView setDataLoaded() {
        try {
            this.isLoading = false;
            this.mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Set as false when you don't want the recycler view to load more data. This will also remove the isLoading view
     *
     * @param loadMore
     */
    @SuppressWarnings("unused")
    public PagingRecyclerView setLoadMore(boolean loadMore) {
        try {
            this.isLoadMore = loadMore;
            this.mAdapter.setLoadMore(isLoadMore);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}