package com.qslib.customview.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.qslib.customview.listview.listener.ClickListener;
import com.qslib.customview.listview.listener.LoadMoreListener;
import com.qslib.customview.listview.listener.LongClickListener;
import com.qslib.customview.listview.listener.RefreshListener;
import com.qslib.customview.textview.ExtTextView;
import com.qslib.library.R;
import com.qslib.util.StringUtils;

import java.util.List;

/**
 * Created by Dang on 5/27/2016.
 */
@SuppressWarnings("ALL")
public class PagingListView<T> extends FrameLayout {
    private SwipeRefreshLayout srlSwipeRefreshLayout;
    private ListView lvListView;
    private View vLoadingView;
    private ExtTextView tvNotFoundData;

    // status
    private boolean loading = false;
    private boolean hasMore = true;

    // adapter
    private ExtBaseAdapter extBaseAdapter = null;

    // listener
    private ClickListener<T> clickListener = null;
    private LongClickListener<T> longClickListener = null;
    private LoadMoreListener loadMoreListener = null;
    private RefreshListener refreshListener = null;

    public PagingListView(Context context) {
        super(context);
        this.initView(context, null);
    }

    public PagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs);
    }

    public PagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        try {
            View view = inflate(context, R.layout.paging_listview, this);

            // refresh layout
            this.srlSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlSwipeRefreshLayout);
            this.srlSwipeRefreshLayout.setOnRefreshListener(() -> {
                if (this.refreshListener != null) this.refreshListener.onRefresh();
            });

            this.lvListView = (ListView) view.findViewById(R.id.lvListView);
            this.lvListView.setFooterDividersEnabled(false);
            this.lvListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

            this.tvNotFoundData = (ExtTextView) view.findViewById(R.id.tvNotFoundData);

            //attribute config
            if (attrs != null) {
                TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PagingListView, 0, 0);
                try {
                    // swipte refresh enable
                    boolean swipeRefreshEnable = typedArray.getBoolean(R.styleable.PagingListView_swipeRefreshEnable, false);
                    if (!swipeRefreshEnable) {
                        this.srlSwipeRefreshLayout.setEnabled(false);
                        this.srlSwipeRefreshLayout.setRefreshing(false);
                    }

                    // swipe refresh indicator color
                    int swipeRefreshIndicatorColor = typedArray.getColor(R.styleable.PagingListView_swipeRefreshIndicatorColor, context.getResources().getColor(android.R.color.black));
                    this.srlSwipeRefreshLayout.setColorSchemeColors(swipeRefreshIndicatorColor);

                    // scroll bar visibility
                    boolean scrollbarVisible = typedArray.getBoolean(R.styleable.PagingListView_scrollbarVisible, true);
                    this.lvListView.setVerticalScrollBarEnabled(scrollbarVisible);

                    // divider visibility
                    boolean dividerVisible = typedArray.getBoolean(R.styleable.PagingListView_dividerVisible, true);
                    if (!dividerVisible) this.lvListView.setDividerHeight(0);

                    // divider color
                    int color = typedArray.getColor(R.styleable.PagingListView_dividerColor, 0);
                    if (color != 0) this.lvListView.setDivider(new ColorDrawable(color));

                    // divider height
                    int dividerHeight = typedArray.getDimensionPixelSize(R.styleable.PagingListView_dividerHeight, 0);
                    if (dividerHeight > 0) this.lvListView.setDividerHeight(dividerHeight);

                    // message no data
                    String message = typedArray.getString(R.styleable.PagingListView_noDataMessage);
                    if (!StringUtils.isEmpty(message)) this.tvNotFoundData.setText(message);
                } finally {
                    typedArray.recycle();
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param extBaseAdapter
     */
    public PagingListView init(Context context, ExtBaseAdapter<T> extBaseAdapter) {
        return init(context, extBaseAdapter, null);
    }

    /**
     * @param context
     * @param extBaseAdapter
     * @param loadingView
     */
    @SuppressLint("InflateParams")
    public PagingListView init(Context context, ExtBaseAdapter<T> extBaseAdapter, final View loadingView) {
        try {
            this.extBaseAdapter = extBaseAdapter;
            this.lvListView.setAdapter(extBaseAdapter);

            if (loadingView != null) {
                this.vLoadingView = loadingView;
            } else {
                this.vLoadingView = LayoutInflater.from(context).inflate(R.layout.paging_item_loading, null);
            }

            // scroll listview
            this.lvListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    try {
                        if (!hasMore || loadMoreListener == null) return;
                        int lastVisibleItem = visibleItemCount + firstVisibleItem;
                        if (lastVisibleItem >= totalItemCount && !loading) {
                            loadMoreListener.onLoadMore();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // click
            this.lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (clickListener != null)
                        clickListener.onClick(position, extBaseAdapter == null ? null : extBaseAdapter.getItem(position));
                }
            });

            // long click
            this.lvListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (longClickListener != null)
                        longClickListener.onLongClick(position, extBaseAdapter == null ? null : extBaseAdapter.getItem(position));
                    return false;
                }
            });

            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * notification data
     *
     * @param items
     * @param <T>
     */
    public void notifyDataSetChangedExtBaseAdapter(List<T> items) {
        try {
            if (this.extBaseAdapter != null)
                this.extBaseAdapter.notifyDataSetChangedExtBaseAdapter(items);
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * display message no data
     */
    public void displayMessageNoData() {
        try {
            if (this.extBaseAdapter == null || this.extBaseAdapter.getCount() <= 0) {
                this.tvNotFoundData.setVisibility(VISIBLE);
            } else {
                this.tvNotFoundData.setVisibility(GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param newItem
     */
    public PagingListView addNewItem(T newItem) {
        try {
            this.extBaseAdapter.addNewItem(newItem);
            // display no data mesage
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @param newItems
     */
    public PagingListView addNewItems(List<T> newItems) {
        try {
            this.extBaseAdapter.addNewItems(newItems);
            // display no data mesage
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * remove item
     *
     * @param item
     */
    public PagingListView removeItem(T item) {
        try {
            this.extBaseAdapter.removeItem(item);
            // display no data mesage
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * remove items
     *
     * @param items
     */
    public PagingListView removeItems(List<T> items) {
        try {
            this.extBaseAdapter.removeItems(items);
            // display no data mesage
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * clear data
     */
    public PagingListView clearList() {
        try {
            this.hasMore = true;
            this.extBaseAdapter.clearList();
            // display no data mesage
            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * start loading
     */
    public PagingListView startLoading() {
        this.startLoading(false);
        return this;
    }

    /**
     * start loading
     */
    public PagingListView startLoading(boolean isLoading) {
        try {
            this.loading = true;

            if (this.srlSwipeRefreshLayout == null || this.lvListView == null || this.vLoadingView == null)
                return this;

            if (!this.srlSwipeRefreshLayout.isRefreshing() && isLoading)
                this.lvListView.addFooterView(this.vLoadingView);

            this.tvNotFoundData.setVisibility(GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * stop loading
     */
    public PagingListView stopLoading() {
        stopLoading(false);
        return this;
    }

    /**
     * stop loading
     */
    public PagingListView stopLoading(boolean isLoading) {
        try {
            this.loading = false;

            if (this.srlSwipeRefreshLayout == null || this.lvListView == null || this.vLoadingView == null)
                return this;

            if (!this.srlSwipeRefreshLayout.isRefreshing() && isLoading)
                this.lvListView.removeFooterView(this.vLoadingView);
            this.srlSwipeRefreshLayout.setRefreshing(false);

            this.displayMessageNoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set has more
     *
     * @param hasMore
     */
    public PagingListView hasMore(boolean hasMore) {
        this.hasMore = hasMore;
        return this;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    /**
     * @param longClickListener
     * @return
     */
    public PagingListView setLongClickListener(LongClickListener<T> longClickListener) {
        this.longClickListener = longClickListener;
        return this;
    }

    /**
     * @param clickListener
     * @return
     */
    public PagingListView setClickListener(ClickListener<T> clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    /**
     * @param loadMoreListener
     * @return
     */
    public PagingListView setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        return this;
    }

    /**
     * @param refreshListener
     * @return
     */
    public PagingListView setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    /**
     * set refresh listview
     *
     * @param isRefresh
     * @return
     */
    public PagingListView setSwipeRefreshing(boolean isRefresh) {
        try {
            this.srlSwipeRefreshLayout.setEnabled(isRefresh);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public PagingListView scrollToPosition(int position) {
        try {
            lvListView.setSelection(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * set text message
     *
     * @param message
     * @return
     */
    public PagingListView setMessage(String message) {
        try {
            tvNotFoundData.setText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}