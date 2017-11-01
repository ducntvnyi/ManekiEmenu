package com.qslib.recycleview.swipe.interfaces;

import com.qslib.recycleview.swipe.SwipeLayout;
import com.qslib.recycleview.swipe.util.Attributes;

import java.util.List;

public interface SwipeItemMangerInterface {
    void openItem(int position);

    void closeItem(int position);

    void closeAllExcept(SwipeLayout swipeLayout);

    void closeAllItems();

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    void removeShownLayouts(SwipeLayout swipeLayout);

    boolean isOpen(int position);

    Attributes.Mode getMode();

    void setMode(Attributes.Mode mode);
}
