package com.qslib.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtils {
    /**
     * display full height for ListView
     *
     * @param listView
     */
    public static void displayFullHeightListView(ListView listView) {
        try {
            ListAdapter myListAdapter = listView.getAdapter();
            if (myListAdapter == null) return;

            // set listAdapter in loop for getting final size
            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++) {
                View listItem = myListAdapter.getView(size, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            // setting list view item in adapter
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight() * (myListAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
