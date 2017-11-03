package com.qslib.customview.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by tuyennm on 8/17/2016.
 */
public class SquareLayout extends FrameLayout {

    private static final String TAG = SquareLayout.class.getSimpleName();

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = width > height ? height : width;
//        int maxSize = (int) PixelUtils.convertDpToPixel(getContext(), MAX_SIZE);
//        if (size > maxSize)
//            size = maxSize;

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
        setLayoutParams(layoutParams);

        setMeasuredDimension(size, size);
    }
}
