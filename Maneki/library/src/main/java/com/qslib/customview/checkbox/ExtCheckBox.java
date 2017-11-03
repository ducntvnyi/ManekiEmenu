package com.qslib.customview.checkbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.qslib.library.R;
import com.qslib.util.StringUtils;
import com.qslib.util.TypefacesUtils;


/**
 * Created by user on 4/22/2015.
 */
public class ExtCheckBox extends CheckBox {
    public ExtCheckBox(Context context) {
        super(context);
    }

    public ExtCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public ExtCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyAttributes(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void applyAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtCheckBox);
            String fontPath = typedArray.getString(R.styleable.ExtCheckBox_checkBoxFontAssetName);
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
