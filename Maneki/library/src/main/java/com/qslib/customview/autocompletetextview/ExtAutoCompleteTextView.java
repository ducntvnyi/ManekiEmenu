package com.qslib.customview.autocompletetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.qslib.library.R;
import com.qslib.util.StringUtils;
import com.qslib.util.TypefacesUtils;

/**
 * Created by user on 5/18/2015.
 */
public class ExtAutoCompleteTextView extends AutoCompleteTextView {
    public ExtAutoCompleteTextView(Context context) {
        super(context);
    }

    public ExtAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public ExtAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttributes(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void applyAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtAutoCompleteTextView);
            String fontPath = typedArray.getString(R.styleable.ExtAutoCompleteTextView_autoCompleteFontAssetName);
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
