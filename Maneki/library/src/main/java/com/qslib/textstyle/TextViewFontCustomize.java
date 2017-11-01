package com.qslib.textstyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewFontCustomize extends TextView {
    // DEMO
    final String fontPath = "fonts/myriad_pro_light.ttf";

    public TextViewFontCustomize(Context context) {
        super(context);
        initFont();
    }

    public TextViewFontCustomize(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public TextViewFontCustomize(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        initFont();
    }

    private void initFont() {
        try {
            Typeface tf = Typefaces.get(getContext(), fontPath);
            this.setTypeface(tf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
