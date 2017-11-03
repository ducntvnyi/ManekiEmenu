package com.vnyi.emenu.maneki.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;


import android.support.v7.widget.AppCompatTextView;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.utils.FontCacheUtil;

/**
 * Created by Hungnd on 11/1/17.
 */

public class TextViewFont extends AppCompatTextView {

    public TextViewFont(Context context) {
        super(context);
        init(null);
    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontView);
            final String fontName = a.getString(R.styleable.FontView_font);
            if (TextUtils.isEmpty(fontName))
                setTypeface(FontCacheUtil.get(FontCacheUtil.DEFAULT_FONT_COUR, getContext()));
            else
                setTypeface(FontCacheUtil.get(fontName, getContext()));
        } else {
            setTypeface(FontCacheUtil.get(FontCacheUtil.DEFAULT_FONT_COUR, getContext()));
        }
    }
}
