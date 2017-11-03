package com.qslib.customview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.qslib.library.R;
import com.qslib.util.StringUtils;
import com.qslib.util.TypefacesUtils;

/**
 * Created by user on 4/22/2015.
 */
public class ExtTextView extends AppCompatTextView {
    public ExtTextView(Context context) {
        super(context);
    }

    public ExtTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public ExtTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyAttributes(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void applyAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtTextView);
            String fontPath = typedArray.getString(R.styleable.ExtTextView_textViewFontAssetName);
            if (!StringUtils.isEmpty(fontPath)) {
                this.setTypeface(TypefacesUtils.get(getContext(), fontPath));
                this.setPaintFlags(this.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
