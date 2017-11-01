package com.qslib.listview;

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

import com.qslib.library.R;
import com.qslib.listview.listener.ClickListener;
import com.qslib.listview.listener.LoadMoreListener;
import com.qslib.listview.listener.LongClickListener;
import com.qslib.listview.listener.RefreshListener;

import java.util.List;

/**
 * Created by Dang on 5/27/2016.
 */
@SuppressWarnings("ALL")
public class PagingListView<T> extends FrameLayout {
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private ListView listView = null;
    private View loadingView = null;

    // status
    private boolean loading = false;
    private boolean hasMore = false;

    // adapter
    private PagingListViewAdapter pagingListViewAdapter = null;

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
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (refreshListener != null) refreshListener.onRefresh();
            });

            listView = (ListView) view.findViewById(R.id.listView);
            listView.setFooterDividersEnabled(false);

            //attribute config
            if (attrs != null) {
                TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PagingListView, 0, 0);
                try {
                    // swipe refresh indicator color
                    int swipeRefreshIndicatorColor = typedArray.getColor(R.styleable.PagingListView_swipeRefreshIndicatorColor, context.getResources().getColor(android.R.color.black));
                    swipeRefreshLayout.setColorSchemeColors(swipeRefreshIndicatorColor);

                    // scroll bar visibility
                    boolean scrollbarVisible = typedArray.getBoolean(R.styleable.PagingListView_scrollbarVisible, true);
                    listView.setVerticalScrollBarEnabled(scrollbarVisible);

                    // divider visibility
                    boolean dividerVisible = typedArray.getBoolean(R.styleable.PagingListView_dividerVisible, true);
                    if (!dividerVisible) listView.setDividerHeight(0);

                    // divider color
                    int color = typedArray.getColor(R.styleable.PagingListView_dividerColor, 0);
                    if (color != 0) listView.setDivider(new ColorDrawable(color));

                    // divider height
                    int dividerHeight = typedArray.getDimensionPixelSize(R.styleable.PagingListView_dividerHeight, 0);
                    if (dividerHeight > 0) listView.setDividerHeight(dividerHeight);
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
     * @param pagingListViewAdapter
     */
    public PagingListView init(Context context, PagingListViewAdapter<T> pagingListViewAdapter) {
        return init(context, pagingListViewAdapter, null);
    }

    /**
     * @param context
     * @param pagingListViewAdapter
     * @param loadingView
     */
    @SuppressLint("InflateParams")
    public PagingListView init(Context context, PagingListViewAdapter<T> pagingListViewAdapter, final View loadingView) {
        try {
            this.pagingListViewAdapter = pagingListViewAdapter;
            this.listView.setAdapter(pagingListViewAdapter);

            if (loadingView != null) {
                this.loadingView = loadingView;
            } else {
                this.loadingView = LayoutInflater.from(context).inflate(R.layout.paging_item_loading, null);
            }

            // scroll listview
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    try {
                        if (!hasMore) return;

                        int lastVisibleItem = visibleItemCount + firstVisibleItem;
                        if (lastVisibleItem >= totalItemCount && !loading) {
                            if (loadMoreListener != null) loadMoreListener.onLoadMore();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // click
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (clickListener != null)
                        clickListener.onClick(position, pagingListViewAdapter == null ? null : pagingListViewAdapter.getItem(position));
                }
            });

            // long click
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (longClickListener != null)
                        longClickListener.onLongClick(position, pagingListViewAdapter == null ? null : pagingListViewAdapter.getItem(position));
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @param newItem
     */
    public PagingListView addNewItem(T newItem) {
        try {
            pagingListViewAdapter.addNewItem(newItem);
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
            pagingListViewAdapter.addNewItems(newItems);
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
            pagingListViewAdapter.removeItem(item);
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
            pagingListViewAdapter.removeItems(items);
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
            hasMore = false;
            pagingListViewAdapter.clearList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * start loading
     */
    public PagingListView startLoading() {
        try {
            loading = true;
            if (!swipeRefreshLayout.isRefreshing()) listView.addFooterView(loadingView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * stop loading
     */
    public PagingListView stopLoading() {
        try {
            if (!swipeRefreshLayout.isRefreshing()) listView.removeFooterView(loadingView);
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
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
}