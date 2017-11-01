package com.qslib.customizeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Dang on 9/16/2015.
 */
public class CustomizeListView extends ListView {
    /**
     * @param context
     */
    public CustomizeListView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomizeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomizeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int heightSpec;
            if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
                heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 1,
                        MeasureSpec.AT_MOST);
            } else {
                // Any other height should be respected as is.
                heightSpec = heightMeasureSpec;
            }

            super.onMeasure(widthMeasureSpec, heightSpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}