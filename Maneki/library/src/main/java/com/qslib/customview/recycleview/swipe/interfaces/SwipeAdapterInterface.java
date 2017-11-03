package com.qslib.customview.recycleview.swipe.interfaces;

public interface SwipeAdapterInterface {
    int getSwipeLayoutResourceId(int position);

    void notifyDataSetChanged();
}
