package com.qslib.customizeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Dang on 9/18/2015.
 */
public class CustomizeExpListView extends ExpandableListView {

    /**
     * @param context
     */
    public CustomizeExpListView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomizeExpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomizeExpListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int heightSpec;
            if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
                heightSpec = MeasureSpec.makeMeasureSpec(
                        Integer.MAX_VALUE >> 1, MeasureSpec.AT_MOST);
            } else {
                // Any other height should be respected as is.
                heightSpec = heightMeasureSpec;
            }

            refreshDrawableState();

            super.onMeasure(widthMeasureSpec, heightSpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
