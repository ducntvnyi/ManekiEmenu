package com.qslib.recycleview.paging;

import android.content.Context;
import android.view.LayoutInflater;

import com.qslib.library.R;

/**
 * Created by Dang on 6/14/2016.
 */
public class PagingRecyclerUtils {
    /**
     * get loading view holder
     *
     * @param context
     * @return
     */
    public static LoadingViewHolder getLoadingViewHolder(Context context) {
        return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.paging_item_loading, null));
    }
}
